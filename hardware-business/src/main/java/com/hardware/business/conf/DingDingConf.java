package com.hardware.business.conf;

import com.hardware.business.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 丁盯接口配置信息
 * 
 * @author zq
 *
 */
public class DingDingConf {


	public static String TEST_BASE_URL ="http://lockapi.dding.net/openapi/v1/";
	public static String TEST_CLIENT_ID ="5c74a2dbf3b86e88ca46af65";
	public static String TEST_CLIENT_SECRET="6c9de2739b105ffdff5356d63faf719f";


	public static String BASE_URL ="http://lockapi.dding.net/openapi/v1/";
	public static String CLIENT_ID ="85c16f75a8a508df19d86eab";
	public static String CLIENT_SECRET="d0510aa9e8ca6f679bf6fc5bafdc24a9";


	static {
		Properties properties = PropertiesUtils.getProperties();
		String account = properties.getProperty("account");
		if (StringUtils.equals("test", account)) {
			BASE_URL = TEST_BASE_URL;
			CLIENT_ID = TEST_CLIENT_ID;
			CLIENT_SECRET = TEST_CLIENT_SECRET;
		}
	}

}
