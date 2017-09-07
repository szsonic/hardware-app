package com.hardware.model.lianyu;
import java.util.List;

/*
 * 联寓平台 - 获取租客租住房的门锁状态信息响应结果
 */
public class LianYuResponseOfGetRoomlock extends LianYuResponse{
	private List<RoomLock> locks;

	public List<RoomLock> getLocks() {
		return locks;
	}

	public void setLocks(List<RoomLock> locks) {
		this.locks = locks;
	} 
	
}
