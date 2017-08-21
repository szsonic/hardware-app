package com.hardware.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hardware.business.enums.RentType;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import com.hardware.business.model.dingding.AddHomeRequest;
import com.hardware.business.model.dingding.AddHomeResponse;
import com.hardware.business.model.dingding.AddRoomResponse;
import com.hardware.business.model.yunyou.City;
import com.hardware.business.model.yunyou.District;
import com.hardware.business.model.yunyou.Province;
import com.hardware.business.service.*;
import com.hardware.service.sdk.chaoyi.CJoyServiceUtil;
import com.hardware.service.sdk.dingding.utils.DingDingHouseUtil;
import com.hardware.service.sdk.guojia.utils.GuoJiaDoorLockUtil;
import com.hardware.service.sdk.yunyou.utils.YunYouUtil;
import com.hardware.service.sdk.yunyu.YunyuServiceUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步房源服务<br>
 *
 * @author sunzhongshuai
 */
@Service(value = "houseSyncService")
public class HouseSyncServiceImpl extends BaseServiceSupport<House, String> implements HouseSyncService,InitializingBean {

    /**
     * 这三个map是云柚平台对应省市区的中文名和id的map
     * 由于“城市”和“区”名称有重复的情况，所以其中的key在前面加上对应的省和城市id
     * provinceMap中的key就是省的中文名，value是对应的省id，比如<"广东省",19>
     * cityMap中的key是省id+城市中文名，value是对应的城市id，比如<"19广州市",190>
     * districtMap中的key是城市id+区中文名，value是对应的区id，比如<"190天河区",1673>
     */
    private Map<String,Integer> provinceMap=new HashMap<>();
    private Map<String,Integer> cityMap=new HashMap<>();
    private Map<String,Integer> districtMap=new HashMap<>();

    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseSyncRecordService houseSyncRecordService;
    @Autowired
    private HouseUnitSyncRecordService houseUnitSyncRecordService;
    @SuppressWarnings("unused")
	@Autowired
    private DateNumberService dateNumberService;
    @Autowired
    private HouseDistrictSyncRecordService houseDistrictSyncRecordService;
    @Autowired
	private MemcachedManager memcachedManager;

    @Override
    public Map<String, Object> syncDingDingHouse(House house, SupplierProduct supplierProduct) throws ThirdPartyRequestException, DataErrorException {
        //去小区表里找，该房源是整租（对应集中式）还是合租（对应分散式）
        if(RentType.HEZU.equals(house.getRentType())){
            syncFenSanHouse4DingDing(house,supplierProduct);
        }else if(RentType.ZHENGZU.equals(house.getRentType())){
            syncJiZhongHouse4DingDing(house,supplierProduct);
        }
        return null;
    }

    @Override
    public Map<String, Object> syncYunYouHouse(House house, SupplierProduct supplierProduct) throws DataErrorException {
        Log.i("===访问云柚门锁同步房源获取设备编号==");
        List<JSONObject> list = new ArrayList<>();
        if(house==null){
            Log.i("==没有房源信息，同步结束==");
            return null;
        }
        HouseDistrict houseDistrict;
        boolean isHome=false;
        House home=null;
        String onLineSynCode;//同步code
        //默认是小门
        if(StringUtils.isNotBlank(house.getParentId())){
            home=houseService.get(house.getParentId());
            //获取大门记录
            houseDistrict=home.getHouseUnit().getHouseBlock().getHouseDistrict();
            onLineSynCode=home.getOnLineSynCode();
        }else{
            isHome=true;
            //如果parentId为空，则当前房屋是大门
            houseDistrict=house.getHouseUnit().getHouseBlock().getHouseDistrict();
            //否则该房源是大门
            if(StringUtils.isBlank(house.getOnLineSynCode())){
                throw new DataErrorException("没有SynCode，同步失败");
            }else{
                onLineSynCode=house.getOnLineSynCode();//否则直接取这个code
            }
        }
        HouseDistrictSyncRecord houseDistrictSyncRecord=new HouseDistrictSyncRecord();
        houseDistrictSyncRecord.setHouseDistrict(houseDistrict);
        SupplierProduct tempSupplierProduct=new SupplierProduct();
        tempSupplierProduct.setSupplier(supplierProduct.getSupplier());
        houseDistrictSyncRecord.setSupplierProduct(tempSupplierProduct);
        List<HouseDistrictSyncRecord> houseDistrictSyncRecordList = houseDistrictSyncRecordService.findByModel(houseDistrictSyncRecord,null,null);
        if(houseDistrictSyncRecordList.isEmpty()){
            HouseDistrictSyncRecord tempHouseDistrictSyncRecord=new HouseDistrictSyncRecord();
            tempHouseDistrictSyncRecord.setHouseDistrict(house.getHouseUnit().getHouseBlock().getHouseDistrict());
            tempHouseDistrictSyncRecord.setSupplierProduct(supplierProduct);
            houseDistrictSyncRecordService.save(tempHouseDistrictSyncRecord);
        }



        if(!isHome){
            //如果不是大门，要先判断该房间对应的大门是否已经同步过
            Log.i("==该房间是小门==");
            HouseSyncRecord houseSyncRecord=new HouseSyncRecord();
            houseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecord.setHouse(home);
            List<HouseSyncRecord> houseSyncRecordList= houseSyncRecordService.findByModel(houseSyncRecord,null,null);
            if(houseSyncRecordList.size()==0){
                Log.i("==该房间对应的大门没有同步过，先同步大门==");
                //代表大门需要先同步一下
                JSONObject homeJson = new JSONObject();
                homeJson.put("code", onLineSynCode);
                homeJson.put("door", ObjectUtils.toString(home.getDoor(), ""));
                homeJson.put("name", home.getRoomNo());
                homeJson.put("doorType", "1");//1:大门，2：小门
                homeJson.put("buildingId", home.getHouseUnit().getHouseBlock().getHouseDistrict().getOnLineSynCode());
                homeJson.put("buildingName", home.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName());
                homeJson.put("buildingNo", ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getName(), ""));
                homeJson.put("unit", ObjectUtils.toString(home.getHouseUnit().getName(), ""));
                String provinceName=home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getProvinceName();
                String cityName=home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName();
                String distName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName();
                //省
                Integer provinceId = provinceMap.get(provinceName);
                homeJson.put("provinceId", provinceId+"");

                //市
                Integer cityId=cityMap.get(provinceId+cityName);
                homeJson.put("cityId", cityId+"");

                //区
                Integer distId=districtMap.get(cityId+distName);
                homeJson.put("disId",distId+"");
                List<JSONObject> homeList = new ArrayList<>();
                homeList.add(homeJson);
                Map<String, Object> resultData = YunYouUtil.houseSyns(homeList);
                Log.i("===访问云柚门锁同步房源获取设备编号结果==" + JSON.toJSONString(resultData));
                if((Boolean) resultData.get("success")){
                    HouseSyncRecord tempHouseSyncRecord=new HouseSyncRecord();
                    tempHouseSyncRecord.setHouse(home);
                    tempHouseSyncRecord.setSupplierProduct(supplierProduct);
                    houseSyncRecordService.save(tempHouseSyncRecord);
                }else{
                    throw new DataErrorException("同步云柚房源大门失败");
                }
            }
        }


        HouseSyncRecord houseSyncRecord=new HouseSyncRecord();
        houseSyncRecord.setHouse(house);
        houseSyncRecord.setSupplierProduct(tempSupplierProduct);
        List<HouseSyncRecord> houseSyncRecordList=houseSyncRecordService.findByModel(houseSyncRecord,null,null);
        if(!houseSyncRecordList.isEmpty()){
            Log.i("==该房源已在云柚同步过==");
            return null;
        }


        JSONObject houseJson = new JSONObject();
        String provinceName;
        String cityName;
        String distName;
        if(isHome){
            //要同步的房间是大门，则直接取数据即可
            houseJson.put("buildingId", house.getHouseUnit().getHouseBlock().getHouseDistrict().getOnLineSynCode());
            houseJson.put("buildingName", house.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName());
            houseJson.put("buildingNo", ObjectUtils.toString(house.getHouseUnit().getHouseBlock().getName(), ""));
            houseJson.put("unit", ObjectUtils.toString(house.getHouseUnit().getName(), ""));
            provinceName=house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getProvinceName();
            cityName=house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName();
            distName = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName();
        }else{
            //否则这些数据取大门的数据
            houseJson.put("buildingId", home.getHouseUnit().getHouseBlock().getHouseDistrict().getOnLineSynCode());
            houseJson.put("buildingName", home.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName());
            houseJson.put("buildingNo", ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getName(), ""));
            houseJson.put("unit", ObjectUtils.toString(home.getHouseUnit().getName(), ""));
            provinceName=home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getProvinceName();
            cityName=home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName();
            distName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName();
        }



        houseJson.put("code", onLineSynCode);
        houseJson.put("door", ObjectUtils.toString(house.getDoor(), ""));
        houseJson.put("name", house.getRoomNo());
        houseJson.put("doorType", StringUtils.isBlank(house.getDoor()) ? "1" : "2");

        //省
        Integer provinceId = provinceMap.get(provinceName);
        houseJson.put("provinceId", provinceId+"");

        //市
        Integer cityId=cityMap.get(provinceId+cityName);
        houseJson.put("cityId", cityId+"");

        //区
        Integer distId=districtMap.get(cityId+distName);
        houseJson.put("disId",distId+"");




        list.add(houseJson);
        Map<String, Object> resultData = YunYouUtil.houseSyns(list);
        Log.i("===访问云柚门锁同步房源获取设备编号结果==" + JSON.toJSONString(resultData));
        if((Boolean) resultData.get("success")){
            HouseSyncRecord tempHouseSyncRecord=new HouseSyncRecord();
            tempHouseSyncRecord.setHouse(house);
            tempHouseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecordService.save(tempHouseSyncRecord);
        }
        return resultData;
    }


    /**
     * 丁盯分散式房源同步
     * 流程：
     * 1、先获取该房源的parentId对应的house信息（大门）判断该大门是否已经同步过
     * 2、如果没同步该大门，则同步并且插入对应的大门同步记录{@link HouseSyncRecord}
     * 3、判断该房源信息是否同步过，如果没同步过则同步并且插入对应的房源同步记录{@link HouseSyncRecord}
     * @param house 房源信息
     * @param supplierProduct 丁盯产品信息
     */
    private void syncFenSanHouse4DingDing(House house, SupplierProduct supplierProduct) throws DataErrorException, ThirdPartyRequestException {
        //获取其parentId然后判断是否经同步，没同步就同步一次。
        Log.i("==开始同步分散式房源==");
        String parentId=house.getParentId();
        Boolean isHome=StringUtils.isBlank(parentId);
        //如果parentId是空，那么代表此房屋为大门
        House home;
        if(isHome){
            home=house;//否则代表传进来的就是大门
        }else{
            home=houseService.get(parentId);//获取该房间所在大门
        }

        if(StringUtils.isBlank(home.getOnLineSynCode())){
           throw new DataErrorException("该大门没有SynCode，同步失败,sourceHouseCode:"+home.getSourceHouseCode());
        }
        if(StringUtils.isBlank(house.getOnLineSynCode())){
            throw new DataErrorException("该房间没有SynCode，同步失败,sourceHouseCode:"+house.getSourceHouseCode());
        }


        //同步大门
        HouseSyncRecord homeSyncRecord=new HouseSyncRecord();
        SupplierProduct tempSupplierProduct=new SupplierProduct();
        tempSupplierProduct.setSupplier(supplierProduct.getSupplier());
        homeSyncRecord.setHouse(home);
        homeSyncRecord.setSupplierProduct(tempSupplierProduct);
        List<HouseSyncRecord> homeSyncRecords= houseSyncRecordService.findByModel(homeSyncRecord,null,null);
        if(homeSyncRecords.isEmpty()){
            //代表该大门没被同步过，先同步大门，把SynCode作为homeId传过去。
            AddHomeRequest addHomeRequest=new AddHomeRequest();
            addHomeRequest.setHomeId(home.getOnLineSynCode());
            addHomeRequest.setHomeType(AddHomeRequest.HouseType.FENSAN.getValue());
            addHomeRequest.setLocation(home.getStreet());//房屋位置暂时写街道名，待确认。
            addHomeRequest.setHomeName(home.getHouseUnit().getHouseBlock().getName()+"幢"+home.getHouseUnit().getName()+"单元"+home.getRoomNo()+"室");
            addHomeRequest.setBlock(home.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName());
            addHomeRequest.setCountry("中国");//国家这里写默认全写中国
            addHomeRequest.setCity(home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName());//城市名
            addHomeRequest.setZone(home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName());//区
            AddHomeResponse addHomeResponse= DingDingHouseUtil.addHome(addHomeRequest);
            //同步home
            Log.i("同步大门结果:"+ JSONObject.toJSONString(addHomeResponse));
            if(addHomeResponse.getErrNo()==0){
                //如果同步第三方成功，先新增同步记录。同步成功home后才能同步room
                HouseSyncRecord addHomeSyncRecord=new HouseSyncRecord();
                addHomeSyncRecord.setHouse(home);
                addHomeSyncRecord.setSupplierProduct(supplierProduct);
                houseSyncRecordService.save(addHomeSyncRecord);
                //插入home同步记录
            }else {
                throw new ThirdPartyRequestException("调用丁盯同步接口失败");
            }
        }else{
            Log.i("==该房间大门已经同步过==");
        }
        if(isHome){
            return;//如果传进来的就是大门，下面的同步小门逻辑就不用走了
        }
        //同步house
        HouseSyncRecord houseSyncRecord=new HouseSyncRecord();
        houseSyncRecord.setHouse(house);
        houseSyncRecord.setSupplierProduct(tempSupplierProduct);
        List<HouseSyncRecord> houseSyncRecords= houseSyncRecordService.findByModel(houseSyncRecord,null,null);
        if(!houseSyncRecords.isEmpty()){
            Log.i("==该房间已经同步过==");
            return;
        }
        AddRoomResponse addRoomResponse = DingDingHouseUtil.addRoom(home.getOnLineSynCode(),house.getOnLineSynCode(),house.getDoor(),"");
        Log.i("同步房间结果:"+JSONObject.toJSONString(addRoomResponse));
        if(addRoomResponse.getErrNo()==0){
            HouseSyncRecord addHouseSyncRecord=new HouseSyncRecord();
            addHouseSyncRecord.setHouse(house);
            addHouseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecordService.save(addHouseSyncRecord);
            //插入house同步记录
        }
        Log.i("==同步分散式房源结束==");
    }


    /**
     * 丁盯集中式房源同步
     * 流程：
     * 1、先获取该房源的单元信息，把单元作为home信息，并且判断该单元是否已经同步过
     * 2、如果没同步该单元，则同步并且插入对应的单元同步记录{@link HouseUnitSyncRecord}
     * 3、判断该房源信息是否同步过，如果没同步过则同步并且插入对应的房源同步记录{@link HouseSyncRecord}
     * @param house 房源信息
     * @param supplierProduct 丁盯产品信息
     */
    private void syncJiZhongHouse4DingDing(House house, SupplierProduct supplierProduct) throws DataErrorException, ThirdPartyRequestException {
        //先获取单元信息
        Log.i("==开始同步集中式房源==");
        HouseUnit home=house.getHouseUnit();
        //虽然目前只有丁盯会用到单元同步表，但还是要根据供应商产品查一下，以防以后有其他供应商会用到
        HouseUnitSyncRecord houseUnitSyncRecord=new HouseUnitSyncRecord();
        houseUnitSyncRecord.setHouseUnit(home);
        SupplierProduct temp=new SupplierProduct();
        temp.setSupplier(supplierProduct.getSupplier());
        houseUnitSyncRecord.setSupplierProduct(temp);
        List<HouseUnitSyncRecord> homeSyncRecords= houseUnitSyncRecordService.findByModel(houseUnitSyncRecord,null,null);

        //同步单元
        if(homeSyncRecords.isEmpty()) {
            if(StringUtils.isBlank(home.getOnLineSynCode())){
                throw new DataErrorException("该单元没有SynCode，同步失败，sourceHouseCode:"+house.getSourceHouseCode());
            }
            //代表该公寓没被同步过，先同步公寓，把SynCode作为homeId传过去。
            AddHomeRequest addHomeRequest=new AddHomeRequest();
            addHomeRequest.setHomeId(home.getOnLineSynCode());
            addHomeRequest.setHomeType(AddHomeRequest.HouseType.JIZHONG.getValue());
            addHomeRequest.setHomeName(house.getHouseUnit().getHouseBlock().getName()+"幢"+house.getHouseUnit().getName()+"单元");
            addHomeRequest.setBlock(house.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName());
            addHomeRequest.setCountry("中国");//国家这里写默认全写中国
            addHomeRequest.setCity(house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName());//城市名
            addHomeRequest.setZone(house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName());//区
            addHomeRequest.setLocation(house.getStreet());//房屋位置暂时写街道名，待确认。
            AddHomeResponse addHomeResponse= DingDingHouseUtil.addHome(addHomeRequest);
            //同步home
            if(addHomeResponse.getErrNo()==0){
                //如果同步第三方成功，先新增同步记录。同步成功home后才能同步room
                HouseUnitSyncRecord homeSyncRecord=new HouseUnitSyncRecord();
                homeSyncRecord.setHouseUnit(home);
                homeSyncRecord.setSupplierProduct(supplierProduct);
                houseUnitSyncRecordService.save(homeSyncRecord);
                //插入home同步记录
            }else{
                throw new ThirdPartyRequestException("调用丁盯接口失败:"+JSONObject.toJSONString(addHomeResponse));
            }
        }else{
            Log.i("==该home已经被同步过==");
        }

        //同步房屋信息
        HouseSyncRecord houseSyncRecord=new HouseSyncRecord();
        houseSyncRecord.setHouse(house);
        houseSyncRecord.setSupplierProduct(temp);

        List<HouseSyncRecord> houseSyncRecords= houseSyncRecordService.findByModel(houseSyncRecord,null,null);
        if(!houseSyncRecords.isEmpty()){
            Log.i("==该房间已经同步过==");
            return;
        }
        if(StringUtils.isBlank(house.getOnLineSynCode())){
            throw new DataErrorException("该房间没有SynCode，同步失败,sourceHouseCode:"+house.getSourceHouseCode());
        }

        AddRoomResponse addRoomResponse = DingDingHouseUtil.addRoom(home.getOnLineSynCode(),house.getOnLineSynCode(),house.getRoomNo(),"");
        //同步house
        if(addRoomResponse.getErrNo()==0){
            HouseSyncRecord addhouseSyncRecord=new HouseSyncRecord();
            addhouseSyncRecord.setHouse(house);
            addhouseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecordService.save(addhouseSyncRecord);
            //插入house同步记录
        }
    }

    /**
     * 初始化时加载云柚提供的json文件，把省市区信息加载到对应的map中。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream in=null;
        InputStreamReader read=null;
        BufferedReader bufferedReader=null;
        try {
            StringBuilder provinceJson=new StringBuilder();
            StringBuilder cityJson=new StringBuilder();
            StringBuilder districtJson=new StringBuilder();
            in= this.getClass().getResourceAsStream("/province.json");
            read = new InputStreamReader(in,"utf-8");
            bufferedReader = new BufferedReader(read);
            String lineTxt;
            while((lineTxt = bufferedReader.readLine()) != null){
                provinceJson.append(lineTxt);
            }
            in= this.getClass().getResourceAsStream("/district.json");
            read = new InputStreamReader(in,"utf-8");
            bufferedReader = new BufferedReader(read);
            while((lineTxt = bufferedReader.readLine()) != null){
                districtJson.append(lineTxt);
            }
            in= this.getClass().getResourceAsStream("/city.json");
            read = new InputStreamReader(in,"utf-8");
            bufferedReader = new BufferedReader(read);
            while((lineTxt = bufferedReader.readLine()) != null){
                cityJson.append(lineTxt);
            }
            List<City> cityList=JSONObject.parseArray(cityJson.toString(),City.class);
            List<District> districtList=JSONObject.parseArray(districtJson.toString(),District.class);
            List<Province> provinceList=JSONObject.parseArray(provinceJson.toString(),Province.class);
            for (Province province : provinceList) {
                provinceMap.put(province.getName(),province.getPro_id());
            }
            for (City city : cityList) {
                cityMap.put(city.getPro_id()+city.getName(),city.getCity_id());
            }
            for (District district : districtList) {
                districtMap.put(district.getCity_id()+district.getName(),district.getDis_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if(in!=null){
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(read!=null){
                    read.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    /*
     * 向第三方智能硬件提供商同步房源信息(通用)
     * 现在支持的第三方
     *   1) 超仪   2) 云寓
     * 每个房源用自己的 onLineSynCode
     */
    @Override
    public Map<String, Object> syncHouse(House house,
    		SupplierProduct supplierProduct) throws DataErrorException {
        Log.i("===开始同步房源信息==");
        if(ValidatorUtils.isEmpty(house)){
            Log.i("==没有房源信息，同步结束==");
            throw new DataErrorException("没有房源信息");
        }
        
        List<JSONObject> list = new ArrayList<>();
        
        boolean isHome = false;
        House home = null;
        //默认是小门
        if(StringUtils.isNotBlank(house.getParentId())){
            home = houseService.get(house.getParentId());
        }else{
            isHome = true;//如果parentId为空，则当前房屋是大门
            if(StringUtils.isBlank(house.getOnLineSynCode())){
                throw new DataErrorException("没有SynCode，同步失败");
            }
        }
        
        if(!isHome){
            //如果不是大门，要先判断该房间对应的大门是否已经同步过
            Log.i("==该房间是小门==");
            HouseSyncRecord houseSyncRecord = new HouseSyncRecord();
            houseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecord.setHouse(home);
            List<HouseSyncRecord> houseSyncRecordList = houseSyncRecordService.findByModel(houseSyncRecord, null,null);
            if(houseSyncRecordList.size() == 0){
                Log.i("==该房间对应的大门没有同步过，先同步大门==");
                //代表大门需要先同步一下
                JSONObject homeJson = new JSONObject();
                homeJson.put("onlineSyncCode", home.getOnLineSynCode());
                homeJson.put("doorType", "1");//1:大门，2：小门
                homeJson.put("roomName", ObjectUtils.toString(home.getDoor(), ""));
                homeJson.put("houseNumber", ObjectUtils.toString(home.getRoomNo(), ""));
                homeJson.put("unitNumber", ObjectUtils.toString(home.getHouseUnit().getName(), ""));
                homeJson.put("buildingNumber", ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getName(), ""));
                homeJson.put("villageName", 
                		ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName(),""));
                HouseStreet street = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet();
                homeJson.put("streetName", ObjectUtils.toString(street.getStreet(), ""));
                homeJson.put("streetAddress", ObjectUtils.toString(street.getAddress(), ""));
                homeJson.put("cityAreaName", ObjectUtils.toString(street.getDistName(), ""));
                homeJson.put("cityName", ObjectUtils.toString(street.getCityName(), ""));
                homeJson.put("provinceName", ObjectUtils.toString(street.getProvinceName(), ""));
                
                List<JSONObject> homeList = new ArrayList<>();
                homeList.add(homeJson);
                
                //Map<String, Object> resultData = CJoyServiceUtil.houseSyns(homeList, memcachedManager);
                Map<String, Object> resultData = houseSync(supplierProduct, homeList, memcachedManager);
                
                Log.i("===同步房源(大门)结果==" + JSON.toJSONString(resultData));
                if((Boolean) resultData.get("success")){
                    HouseSyncRecord tempHouseSyncRecord = new HouseSyncRecord();
                    tempHouseSyncRecord.setHouse(home);
                    tempHouseSyncRecord.setSupplierProduct(supplierProduct);
                    houseSyncRecordService.save(tempHouseSyncRecord);
                }else{
                    throw new DataErrorException("同步房源(大门)失败");
                }
            }
        }

        HouseSyncRecord houseSyncRecord = new HouseSyncRecord();
        houseSyncRecord.setHouse(house);
        houseSyncRecord.setSupplierProduct(supplierProduct);
        List<HouseSyncRecord> houseSyncRecordList = houseSyncRecordService.findByModel(houseSyncRecord, null, null);
        if(!houseSyncRecordList.isEmpty()){
            Log.i("==该房源已同步过==");
            return null;
        }

        JSONObject houseJson = new JSONObject();
        String provinceName;
        String cityName;
        String distName;
        String streetName;
        String streetAdd;
        if(isHome){
            //要同步的房间是大门，则直接取数据即可
            houseJson.put("unitNumber", ObjectUtils.toString(house.getHouseUnit().getName(), ""));
            houseJson.put("buildingNumber", ObjectUtils.toString(house.getHouseUnit().getHouseBlock().getName(), ""));
            houseJson.put("villageName", 
            		ObjectUtils.toString(house.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName(), ""));
            provinceName = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getProvinceName();
            cityName = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName();
            distName = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName();
            streetName = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getStreet();
            streetAdd = house.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getAddress();
        }else{
            //否则这些数据取大门的数据
        	houseJson.put("unitNumber", ObjectUtils.toString(home.getHouseUnit().getName(), ""));
            houseJson.put("buildingNumber", ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getName(), ""));
            houseJson.put("villageName", 
            		ObjectUtils.toString(home.getHouseUnit().getHouseBlock().getHouseDistrict().getDistrictName(), ""));
            provinceName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getProvinceName();
            cityName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getCityName();
            distName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getDistName();
            streetName = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getStreet();
            streetAdd = home.getHouseUnit().getHouseBlock().getHouseDistrict().getHouseStreet().getAddress();
        }
        //房间信息
        houseJson.put("onlineSyncCode", ObjectUtils.toString(house.getOnLineSynCode(),""));
        houseJson.put("doorType", StringUtils.isBlank(house.getDoor()) ? "1" : "2");
        houseJson.put("roomName", ObjectUtils.toString(house.getDoor(), ""));
        houseJson.put("houseNumber", ObjectUtils.toString(house.getRoomNo(),""));
        //房间地理位置信息
        houseJson.put("streetName", ObjectUtils.toString(streetName, ""));
        houseJson.put("streetAddress", ObjectUtils.toString(streetAdd, ""));
        houseJson.put("cityAreaName", ObjectUtils.toString(distName, ""));
        houseJson.put("cityName", ObjectUtils.toString(cityName, ""));
        houseJson.put("provinceName", ObjectUtils.toString(provinceName, ""));

        list.add(houseJson);
        //Map<String, Object> resultData = CJoyServiceUtil.houseSyns(list, memcachedManager);
        Map<String, Object> resultData = houseSync(supplierProduct, list, memcachedManager);
        Log.i("===同步房源结果==" + JSON.toJSONString(resultData));
        if((Boolean) resultData.get("success")){
            HouseSyncRecord tempHouseSyncRecord = new HouseSyncRecord();
            tempHouseSyncRecord.setHouse(house);
            tempHouseSyncRecord.setSupplierProduct(supplierProduct);
            houseSyncRecordService.save(tempHouseSyncRecord);
        }else{
        	throw new DataErrorException("同步房源失败");
        }
        
        return resultData;
    }
    
    //调用第三方同步房源接口
    private Map<String, Object> houseSync(SupplierProduct supplierProduct,
    		List<JSONObject> list, MemcachedManager memcachedManager){
    	String code = supplierProduct.getSupplier().getCode();
    	if(ValidatorUtils.isEquals("CHAOYI", code)){
    		return CJoyServiceUtil.houseSyns(list, memcachedManager);
    	}else if(ValidatorUtils.isEquals("YUNYU", code)){
    		return YunyuServiceUtil.houseSyns(list);
    	}
    	
    	return null;
    }

    @Override
    public Map<String, Object> syncGuoJiaHouse(House house) throws DataErrorException, ThirdPartyRequestException {
        Log.i("===果加同步房源（新增订单）==");
        if(ValidatorUtils.isEmpty(house)){
            Log.i("==没有房源信息，同步结束==");
            throw new DataErrorException("没有房源信息");
        }
        String  parentCode = null;
        if(StringUtils.isNotBlank(house.getParentId())){
            //代表这个小门
            parentCode = houseService.get(house.getParentId()).getOnLineSynCode();
        }else{
            parentCode=house.getOnLineSynCode();
        }
        GuoJiaDoorLockUtil.addOrder(house,parentCode);
        return null;
    }
}
