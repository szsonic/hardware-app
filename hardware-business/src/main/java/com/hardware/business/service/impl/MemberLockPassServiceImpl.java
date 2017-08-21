package com.hardware.business.service.impl;

import com.hardware.business.dao.MemberLockPassDao;
import com.hardware.business.enums.FrozenStatus;
import com.hardware.business.enums.PwdStatus;
import com.hardware.business.exception.ValidateException;
import com.hardware.business.model.DoorLock;
import com.hardware.business.model.Member;
import com.hardware.business.model.MemberLockPass;
import com.hardware.business.service.DoorLockService;
import com.hardware.business.service.MemberLockPassService;
import org.iframework.support.domain.enums.RecordStatus;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户门锁密码业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("memberLockPassService")
public class MemberLockPassServiceImpl extends BaseServiceSupport<MemberLockPass, String>
		implements MemberLockPassService {

	@Resource(name = "memberLockPassDao")
	private MemberLockPassDao memberLockPassDao;

	@Resource(name = "doorLockService")
    DoorLockService doorLockService;

	@Resource(name = "memberLockPassDao")
	@Override
	public void setBaseHibernateDaoSupport(
			@Qualifier("memberLockPassDaoImpl") BaseHibernateDaoSupport<MemberLockPass, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public List<MemberLockPass> findByOpenIdDevId(String openId, String devId) {
		return memberLockPassDao.findByOpenIdDevId(openId, devId);
	}

	@Override
	public Map<String, Object> validateEffectivePwdExist(Member member, DoorLock doorLock, String passType
			, FrozenStatus frozenStatus)
			throws Exception {
		MemberLockPass model = new MemberLockPass();
		model.setDoorLock(doorLock);
		model.setMember(member);
		model.setPassType(passType);
		model.setFrozenStatus(frozenStatus);
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberLockPass> memberLockPassList = memberLockPassDao.validateEffectivePwdExist(model);
		MemberLockPass memberLockPass = null;
		String status = null;
		if (memberLockPassList != null && memberLockPassList.size() == 1) {
			// 可以查询到的数据 必定只有等待回调，或者回调成功 ，不存在回调失败
			memberLockPass = memberLockPassList.get(0);
			if (PwdStatus.WAIT_CALLBACK == memberLockPass.getPwdStatus()
					&& memberLockPass.getMaxCallBackTime().after(new Date())) {
				// 未知逻辑
				status = "2";
				map.put("memberLockPass", memberLockPass);
				map.put("status", status);
				return map;
			}
			if (PwdStatus.WAIT_CALLBACK == memberLockPass.getPwdStatus()
					&& memberLockPass.getMaxCallBackTime().before(new Date())) {
				// 回调超时逻辑
				memberLockPass.setPwdStatus(PwdStatus.CALLBACK_FAIL);
				memberLockPass.setStatus(RecordStatus.DELETE);
				memberLockPassDao.update(memberLockPass);
				status = "0";
				map.put("memberLockPass", memberLockPass);
				map.put("status", status);
				return map;
			}
			if (memberLockPass.getEffectiveEnd().before(new Date())) {
				// 密码过期逻辑
				memberLockPass.setStatus(RecordStatus.DELETE);
				memberLockPassDao.update(memberLockPass);
				status = "3";
				map.put("memberLockPass", memberLockPass);
				map.put("status", status);
				return map;
			} else {
				// 此处必定回调成功，而且密码还未过期
				status = "1";
				map.put("memberLockPass", memberLockPass);
				map.put("status", status);
				return map;
			}

		} else if (memberLockPassList != null && memberLockPassList.size() > 1) {
			throw new ValidateException("密码数量异常");
		} else {
			status = "0";
			map.put("memberLockPass", null);
			map.put("status", status);
			return map;
		}

	}

	@Override
	public List<MemberLockPass> findPwdMaybeEffectiveByDoorLock(DoorLock doorLock, String passType) {
		MemberLockPass model = new MemberLockPass();
		model.setDoorLock(doorLock);
		model.setPassType(passType);
		List<MemberLockPass> memberLockPassList = memberLockPassDao.validateEffectivePwdExist(model);
		if (memberLockPassList != null && memberLockPassList.size() > 0) {
			Iterator<MemberLockPass> i = memberLockPassList.iterator();
			while (i.hasNext()) {
				MemberLockPass memberLockPass = i.next();
				if (PwdStatus.WAIT_CALLBACK == memberLockPass.getPwdStatus()
						&& memberLockPass.getMaxCallBackTime().after(new Date())) {
					// 未知逻辑,保留该对象
					;
				}
				if (PwdStatus.WAIT_CALLBACK == memberLockPass.getPwdStatus()
						&& memberLockPass.getMaxCallBackTime().before(new Date())) {
					// 回调超时逻辑
					memberLockPass.setPwdStatus(PwdStatus.CALLBACK_FAIL);
					memberLockPass.setStatus(RecordStatus.DELETE);
					memberLockPassDao.update(memberLockPass);
					i.remove();
				}
				if (memberLockPass.getEffectiveEnd().before(new Date())) {
					// 密码过期逻辑
					memberLockPass.setStatus(RecordStatus.DELETE);
					memberLockPassDao.update(memberLockPass);
					i.remove();
				} else {
					// 此处必定回调成功，而且密码还未过期
					;
				}

			}
			return memberLockPassList;
		} else {
			return memberLockPassList;
		}
	}

	@Override
	public List<MemberLockPass> findByPasswordIdDoorLockId(Integer passIndex, DoorLock doorLock) {
		MemberLockPass memberLockPassForSearch = new MemberLockPass();
		memberLockPassForSearch.setDoorLock(doorLock);
		memberLockPassForSearch.setPassIndex(passIndex);
		memberLockPassForSearch.setStatus(RecordStatus.AVAILABLE);
		return memberLockPassDao.findByModel(memberLockPassForSearch, null, null);
//		return null;
	}

	@Override
	public List<MemberLockPass> findMemberLockPassByDoorLockDevIdAndPass(DoorLock doorLock, String passIndex,String type) {
		// TODO Auto-generated method stub
		// if(doorLock!=null)
		// {
		// return memberLockPassDao.queryHQL("from MemberLockPass m where
		// m.doorLock.id='"+doorLock.getId()+"' "
		// + "and m.passIndex='"+passIndex+"' and m.status='AVAILABLE'");
		// }
		// TODO Auto-generated method stub
		//防止sql注入
		if (doorLock != null&&"6".equals(type)) {
			MemberLockPass memberLockPass = new MemberLockPass();
			memberLockPass.setDoorLock(doorLock);
			memberLockPass.setPassIndex(Integer.parseInt(passIndex));
			memberLockPass.setStatus(RecordStatus.AVAILABLE);
			List<MemberLockPass> list = (List<MemberLockPass>) memberLockPassDao
					.getObjectByHQL("from MemberLockPass m where m.doorLock=:doorLock and m.passIndex=:passIndex"
							+ " and m.status=:status", memberLockPass, 0, 0);
			return list;
		}
		return null;
	}
	
	@Override
	public List<MemberLockPass> findByDevIdAndPasswordId(String passwordId, String devId) {
		return memberLockPassDao.findByDevIdAndPasswordId(passwordId, devId);
	}
}
