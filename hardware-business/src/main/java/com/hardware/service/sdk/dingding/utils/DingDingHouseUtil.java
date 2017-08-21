package com.hardware.service.sdk.dingding.utils;

import com.hardware.business.model.dingding.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class DingDingHouseUtil extends DingDingUtil{

    public static FindHomeStatesResponse findHomeStates(List<String> homeIds) {
        String url= BASE_URL +"find_home_states";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id",homeIds);
        return  restTemplate.postForObject(url,params,FindHomeStatesResponse.class);

    }


    public static SearchHomeInfoResponse searchHomeInfo(SearchHomeInfoRequest searchHomeInfoRequest) {
        StringBuilder url=new StringBuilder(BASE_URL);
        Map<String,Object> params=new HashMap<>();
        url.append("search_home_info?access_token={access_token}");
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(searchHomeInfoRequest.getHomeName())){
            params.put("home_name",searchHomeInfoRequest.getHomeName());
            url.append("&home_name={home_name}");
        }
        if(StringUtils.isNotBlank(searchHomeInfoRequest.getBlock())){
            params.put("block",searchHomeInfoRequest.getBlock());
            url.append("&block={block}");
        }
        if(StringUtils.isNotBlank(searchHomeInfoRequest.getLocation())){
            params.put("location",searchHomeInfoRequest.getLocation());
            url.append("&location={location}");
        }
        if(searchHomeInfoRequest.getOffset()!=null){
            params.put("offset",searchHomeInfoRequest.getOffset());
            url.append("&offset={offset}");
        }
        if(searchHomeInfoRequest.getCount()!=null){
            params.put("count",searchHomeInfoRequest.getCount());
            url.append("&count={count}");
        }
        return restTemplate.getForObject(url.toString(),SearchHomeInfoResponse.class,params);
    }


    public static FindHomeDeviceResponse findHomeDevice(String homeId) {
        String url= BASE_URL +"find_home_device";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id",homeId);
        return restTemplate.postForObject(url,params,FindHomeDeviceResponse.class);
    }


    public static FindHomeDevicesResponse findHomeDevices(List<String> homeId) {
        String url= BASE_URL +"find_home_devices";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id",homeId);
        return restTemplate.postForObject(url,params,FindHomeDevicesResponse.class);
    }


    public static AddRoomResponse addRoom(String homeId, String roomId, String roomName, String roomDescription) {
        String url= BASE_URL +"add_room";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id",homeId);
        params.put("room_id",roomId);
        params.put("room_name",roomName);
        params.put("room_description",roomDescription);
        return restTemplate.postForObject(url,params,AddRoomResponse.class);
    }



    public static AddHomeResponse addHome(AddHomeRequest addHomeRequest) {
        String url= BASE_URL +"add_home";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", addHomeRequest.getHomeId());
        params.put("home_type", addHomeRequest.getHomeType());
        params.put("home_name", addHomeRequest.getHomeName());
        params.put("country", addHomeRequest.getCountry());
        params.put("city", addHomeRequest.getCity());
        params.put("zone", addHomeRequest.getZone());
        params.put("location", addHomeRequest.getLocation());
        params.put("block", addHomeRequest.getBlock());
        params.put("description", addHomeRequest.getDescription());
        return restTemplate.postForObject(url,params,AddHomeResponse.class);
    }



}
