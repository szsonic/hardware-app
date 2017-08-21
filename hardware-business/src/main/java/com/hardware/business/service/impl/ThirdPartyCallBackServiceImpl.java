package com.hardware.business.service.impl;

import com.hardware.business.dao.*;
import com.hardware.business.enums.HardwareStatus;
import com.hardware.business.enums.HardwareType;
import com.hardware.business.enums.HouseSource;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.model.*;
import com.hardware.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service(value = "thirdPartyCallBackService")
public class ThirdPartyCallBackServiceImpl implements ThirdPartyCallBackService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private AmmeterService ammeterService;

    @Autowired
    private DoorLockService doorLockService;


    @Autowired
    private HardwareService hardwareService;
    @Autowired
    private SupplierProductService supplierProductService;



    @Resource(name = "ammeterDao")
    private AmmeterDao ammeterDao;

    @Resource(name = "doorLockDao")
    private DoorLockDao doorLockDao;

    @Resource(name = "houseDao")
    private HouseDao houseDao;



    @Resource(name = "hardwareDao")
    private HardwareDao hardwareDao;


    @Resource(name = "memberDao")
    private MemberDao memberDao;

    @Override
    public void syncAllData(String houseOrRoomOpenId, String doorType,String hardwareType, String devId, String supplier) throws Exception{
        //新增房间
        House house=houseService.getSourceHouseCode(houseOrRoomOpenId);
        if(house==null){
            // 调接口插入房间相关数据
            houseService.saveHouseCenterInfo(houseOrRoomOpenId, doorType, HouseSource.FD_INSTALL_HARDWARE,null,null,null);
            house=houseService.getSourceHouseCode(houseOrRoomOpenId);
            if(house==null){
                throw new DataErrorException("获取房源信息失败，同步失败");
            }
        }
        //新增硬件
        SupplierProduct supplierProduct=supplierProductService.findBySupplierCode(supplier);
        if(supplierProduct!=null){
            Hardware hardware=new Hardware();
            hardware.setHouse(house);
            hardware.setHardwareStatus(HardwareStatus.ZC);
            if(hardwareType.equals(HardwareType.DIANBIAO.name())){
                Ammeter ammeter = ammeterService.getAmmeterByDevId(devId);
                if(ammeter!=null){
                    throw new DataErrorException("该电表已经存在");
                }
                Ammeter searchAmmeter=new Ammeter();
                searchAmmeter.setHouse(house);
                List<Ammeter> ammeterList = ammeterService.findByModel(searchAmmeter,null,null);
                if(ammeterList.size()>0){
                    throw new DataErrorException("该房间下已经有其他电表存在");
                }
                Ammeter tempAmmeter=new Ammeter();
                hardware.setAmmeter(tempAmmeter);
                hardware.setType(HardwareType.DIANBIAO);
                tempAmmeter.setDevId(devId);
                tempAmmeter.setHardware(hardware);
                tempAmmeter.setHouse(house);
                tempAmmeter.setSupplierProduct(supplierProduct);
                tempAmmeter.setInstallTime(new Date());
                hardwareService.save(hardware);
                ammeterService.save(tempAmmeter);
            }else{
                DoorLock doorLock=doorLockService.getDoorLockByDevId(devId);
                if(doorLock!=null){
                    throw new DataErrorException("该门锁已经存在");
                }
                DoorLock searchDoorLock=new DoorLock();
                searchDoorLock.setHouse(house);
                List<DoorLock> doorLockList = doorLockService.findByModel(searchDoorLock,null,null);
                if(doorLockList.size()>0){
                    throw new DataErrorException("该房间下已经有其他门锁存在");
                }
                DoorLock tempDoorLock=new DoorLock();
                hardware.setDoorLock(tempDoorLock);
                hardware.setType(HardwareType.MENSUO);
                tempDoorLock.setDevId(devId);
                tempDoorLock.setHardware(hardware);
                tempDoorLock.setHouse(house);
                tempDoorLock.setSupplierProduct(supplierProduct);
                tempDoorLock.setInstallTime(new Date());
                hardwareService.save(hardware);
                doorLockService.save(tempDoorLock);
            }
        }else{
            throw new DataErrorException("未找到对应的产品");
        }

    }


    @Override
    public void deleteHouseMember(String houseOpenId, String memberId) throws Exception {
        House house=houseService.getSourceHouseCode(houseOpenId);
        if(house==null){
            throw new DataErrorException("房源不存在，删除租户失败");
        }
        Member oldMember=memberService.getByOpenId(memberId);
        if(oldMember==null){
            throw new DataErrorException("待删除租户不存在，删除租户失败");
        }
        Integer oldCount = houseService.getHouseMember(house.getId(),oldMember.getId());
        if(oldCount==0){
            throw new DataErrorException("待更新租户与该房屋的关系不存在，删除租户失败");
        }
        houseDao.deleteHouseMember(house.getId(),oldMember.getId());

    }


    @Override
    public void addHouseMember(String houseOpenId, String memberId) throws Exception {
        House house=houseService.getSourceHouseCode(houseOpenId);
        if(house==null){
            throw new DataErrorException("房源不存在，新增失败");
        }
        Member member=memberService.getByOpenId(memberId);
        if(member==null){
            Member temp=new Member();
            temp.setAccountOpenId(memberId);
            memberDao.save(temp);
            member=temp;
        }else {
            Integer newCount = houseService.getHouseMember(house.getId(), member.getId());
            if (newCount > 0) {
                throw new DataErrorException("新租户与该房屋的关系已存在，新增失败");
            }
        }
        List<House> houses=member.getHouses();
        Set<House> houseSet=new HashSet<>();
        if(houses!=null){
            houseSet=new HashSet<>(houses);
        }
        houseSet.add(house);
        member.setHouses(new ArrayList<>(houseSet));
        memberService.update(member);
    }

    @Override
    public void updateHardware(String houseOpenId,String hardwareType,String devId,String supplier) throws Exception {
        House house=houseService.getSourceHouseCode(houseOpenId);
        HardwareType type=null;
        if(house==null){
            throw new DataErrorException("房源不存在，更新失败");
        }
        if(HardwareType.DIANBIAO.name().equals(hardwareType)){
            type= HardwareType.DIANBIAO;
            Ammeter ammeter=new Ammeter();
            ammeter.setHouse(house);
            List<Ammeter> ammeterList=ammeterService.findByModel(ammeter,null,null);
            if(ammeterList.size()==0){
                throw new DataErrorException("该房源不存在电表，更新失败");
            }else if(ammeterList.size()>1){
                throw new DataErrorException("该房源存在多个电表，更新失败");
            }
        }else{
            type= HardwareType.MENSUO;
            DoorLock doorLock=new DoorLock();
            doorLock.setHouse(house);
            List<DoorLock> doorLockList=doorLockService.findByModel(doorLock,null,null);
            if(doorLockList.size()==0){
                throw new DataErrorException("该房源不存在门锁，更新失败");
            }else if(doorLockList.size()>1){
                throw new DataErrorException("该房源存在多个门锁，更新失败");
            }
        }

        SupplierProduct supplierProduct=supplierProductService.findByProductCode(supplier);
        if(supplierProduct==null){
            throw new DataErrorException("未找到对应的产品编码，更新失败");
        }

        hardwareDao.deleteByHouseId(house.getId(),type.name());
        //2、删除对应的硬件表
        if("MENSUO".equals(type.name())){
            //1、删除硬件表
            //删除门锁表记录
            doorLockDao.deleteByHouseId(house.getId());
        }else{
            ammeterDao.deleteByHouseId(house.getId());
        }
        //3、插入新的设备
        Hardware hardware=new Hardware();
        if("MENSUO".equals(type.name())){
            DoorLock doorLock=new DoorLock();
            doorLock.setSupplierProduct(supplierProduct);
            doorLock.setDevId(devId);
            doorLock.setInstallTime(new Date());
            doorLock.setHouse(house);
            doorLockDao.save(doorLock);
            hardware.setDoorLock(doorLock);
        }else{
            Ammeter ammeter=new Ammeter();
            ammeter.setSupplierProduct(supplierProduct);
            ammeter.setDevId(devId);
            ammeter.setInstallTime(new Date());
            ammeter.setHouse(house);
            ammeterDao.save(ammeter);
            hardware.setAmmeter(ammeter);
        }
        hardware.setHouse(house);
        hardware.setType(type);
        hardware.setHardwareStatus(HardwareStatus.ZC);
        hardwareDao.save(hardware);
    }
}
