package com.hardware.business.service;

import com.hardware.business.enums.FrozenStatus;
import com.hardware.business.model.DoorLock;
import com.hardware.business.model.Member;
import com.hardware.business.model.MemberLockPass;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;
import java.util.Map;

/**
 * 用户门锁密码业务接口<br>
 * 
 * @author zq
 *
 */

public interface MemberLockPassService extends IBaseServiceSupport<MemberLockPass, String> {
	
	
	/**
	 * 根据用户的openId和设备号查询用户门锁的密码是否存在
	 * 
	 * @param openId
	 *          用户的openId
	 * @param devId
	 *          门锁的设备号
	 * @return
	 */
	public List<MemberLockPass> findByPasswordIdDoorLockId(Integer passIndex, DoorLock doorLock);




	/**
	 * 根据用户的openId和设备号查询用户门锁的密码是否存在
	 *
	 * @param openId
	 *          用户的openId
	 * @param devId
	 *          门锁的设备号
	 * @return
	 */
	public List<MemberLockPass> findByOpenIdDevId(String openId, String devId);


	/**
	 * 验证有效密码
	 * @param model 密码查询实体
	 * @return
	 */
	 Map<String,Object> validateEffectivePwdExist(Member member, DoorLock doorLock, String passType, FrozenStatus frozenStatus) throws Exception;

	/**
	 * 验证密码是否存在，包括失效密码
	 * @param model
	 * @return
	 */
	 public List<MemberLockPass> findPwdMaybeEffectiveByDoorLock(DoorLock doorLock, String passType);
	 /**
	  * 查询密码id根据设备和设备密码
	  * @param devId
	  * @param passWord
	  * @return
	  */
	 public List<MemberLockPass> findMemberLockPassByDoorLockDevIdAndPass(DoorLock doorLock, String passWord, String type);

	 /**
	  * 根据用户的openId和设备号查询用户门锁的密码是否存在
	 * 根据用户的openId和设备号查询用户门锁的密码是否存在
	 * 
	 * @param openId
	 *          用户的openId
	 * @param devId
	 *          门锁的设备号
	 * @return
	 */
	public List<MemberLockPass> findByDevIdAndPasswordId(String passwordId, String devId);
}
