package com.hardware.business.service;



/**
 * <br>
 *
 * @author sunzhongshuai
 */
public interface ThirdPartyCallBackService {
    void syncAllData(String houseOrRoomOpenId, String doorType, String hardwareType, String devId, String supplier) throws Exception;
    void deleteHouseMember(String houseOpenId, String memberId) throws Exception;
    void addHouseMember(String houseOpenId, String memberId) throws Exception;
    void updateHardware(String houseOpenId, String hardwareType, String devId, String supplier) throws Exception;
}
