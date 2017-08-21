package com.hardware.business.service.impl;

import com.hardware.business.conf.Config;
import com.hardware.business.model.houseCenter.IntelligentHouseResult;
import com.hardware.business.service.HouseCenterService;
import net.sf.json.JSONObject;
import org.iframework.commons.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("houseCenterService")
public class HouseCenterServiceImpl   implements HouseCenterService{
	public IntelligentHouseResult getHouseInfo(String houseOrRoomId,String type){
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("houseId", 333);
		urlVariables.put("searchType", 333);
		String HOUSE_CENTER_PATH = Config.HOUSE_CENTER_PATH;
		String HOUSE_CENTER_GET_HOUSE_INFO = Config.HOUSE_CENTER_GET_HOUSE_INFO;
		String Url = HOUSE_CENTER_PATH
				+ HOUSE_CENTER_GET_HOUSE_INFO;
		String result = HttpClientUtils.post(Url, urlVariables);
		
		JSONObject obj =JSONObject.fromObject(result);
		IntelligentHouseResult intelligentHouseResult = (IntelligentHouseResult)JSONObject.toBean(obj,IntelligentHouseResult.class);
		return intelligentHouseResult;
	}
}
