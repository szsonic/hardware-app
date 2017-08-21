package com.hardware.business.service;

import com.hardware.business.model.houseCenter.IntelligentHouseResult;

public interface HouseCenterService {
	public IntelligentHouseResult getHouseInfo(String houseOrRoomId, String type);
	
	
}
