package com.hardware.enums;

/**
 * 
 * @author zq
 * 
 */
public enum OperationAction {

	DB_CZ("电表充值"), YY_TBSN("云柚同步SN"), DB_KZ("电表控制"),
  CS_MIMA("次数密码"), PT_MIMA("普通密码"), MS_KZ("门锁控制"),
  MS_SZ_MIMA("门锁设置密码"),
	DT_MIMA("动态密码"), MR_MIMA("默认密码"), ZYW_TS("子业务推送用户"),
	HT_BD_YJ_YH("后台绑定硬件给用户"), ZYW_JB_YJ("子业务解绑硬件"), 
	DEL_YJ("删除硬件"), ZYW_BD_YJ_YH("子业务绑定硬件给用户"),;

	// 枚举说明
	private String desc;

	/**
	 * 私有的构造方法
	 */
	private OperationAction(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
}