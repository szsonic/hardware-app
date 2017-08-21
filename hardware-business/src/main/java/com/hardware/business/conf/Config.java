package com.hardware.business.conf;

import java.util.ResourceBundle;

/**
 * 系统静态配置信息
 */
public class Config {
	
	/**
	 * 房源中心
	 */
	public static String HOUSE_CENTER_PATH;
	
	
	/**
	 * 房源中心獲取房源
	 */
	public static String HOUSE_CENTER_GET_HOUSE_INFO;
	
	/**
	 * 房源中心獲取房源
	 */
	public static String HOUSE_CENTER_GET_ROOM_INFO;

	static {
		ResourceBundle conf = ResourceBundle.getBundle("config");
		HOUSE_CENTER_GET_ROOM_INFO = conf.getString("HOUSE_CENTER_GET_ROOM_INFO");
		HOUSE_CENTER_GET_HOUSE_INFO = conf.getString("HOUSE_CENTER_GET_HOUSE_INFO");
		HOUSE_CENTER_PATH = conf.getString("HOUSE_CENTER_PATH");
	
	}

	public static String getValue(String key) {
		return ResourceBundle.getBundle("config").getString(key);
	}
}
