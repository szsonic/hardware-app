package com.hardware.enums;

/**
 * 门锁的操作状态<br>
 * 
 * @author zhongqi
 * 
 */
public enum DoorLockAction {

	OPENING("正在开锁"), OPENSUCCESS("开锁成功"), OPENFAIL("开锁失败"), CLOSEING("正在关锁"), CLOSESUCCESS("关锁成功"), CLOSEFAIL("关锁失败");

	// 枚举说明
	private String desc;

	/**
	 * 私有的构造方法
	 */
	private DoorLockAction(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
}
