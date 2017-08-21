package com.hardware.business.conf;

import com.hardware.business.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 蜂电电表接口配置信息
 * 
 * @author zhongqi
 *
 */
public class FengdianConf {

	/**
	 * 蜂电API host地址
	 */
	public static final String HOST = "http://im.zg118.com:8001";
	/**
	 * 蜂电saas平台的管理帐号
	 */
	public static String USERID = "18616791717";
	/**
	 * 蜂电saas平台的管理帐号的密码
	 */
	public static String PASS = "1989Xsk1031";
	/**
	 * 默认版本号
	 */
	public static final String VERSION = "0116010101";

	/**
	 * 蜂电saas平台的管理帐号
	 */
	public static final String TEST_USERID = "13564270329";
	/**
	 * 蜂电saas平台的管理帐号的密码
	 */
	public static final String TEST_PASS = "zq0209";

	static {
		// 临时解决，已经全部在数据库里面了
		Properties properties = PropertiesUtils.getProperties();
		String account = properties.getProperty("account");
		if (StringUtils.equals("test", account)) {
			USERID = TEST_USERID;
			PASS = TEST_PASS;
		}
	}

}
