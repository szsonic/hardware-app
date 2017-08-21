package com.hardware.business.conf;

import com.hardware.business.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 超仪接口配置信息
 */
public class CJoyConf {

	public static String URL = "http://122.225.71.66:8787/yingjia/joy/";//"http://122.225.71.66:8787/ts/joy/"
	public static String CLINET_ID = "joy000001";
	//硬件信息回调接口
	public static String CALLBACK_URL = "http://hd.innjia.com/hardware-service/api/ammeter/syncDevId.json";
	
	public static String TEST_URL = "http://122.225.71.66:211/yingjia/joy/";
	public static String TEST_CLINET_ID = "joy000001";
	public static String TEST_CALLBACK_URL = "http://121.40.178.164:9090/hardware-service/api/ammeter/syncDevId.json";
	//public static String TEST_CALLBACK_URL = "http://121.40.201.35:9090/hardware-service/api/ammeter/syncDevId.json";


	static {
		Properties properties = PropertiesUtils.getProperties();
		String account = properties.getProperty("account");
		if (StringUtils.equals("test", account)) {
			URL = TEST_URL;
			CLINET_ID = TEST_CLINET_ID;
			CALLBACK_URL = TEST_CALLBACK_URL;
		}
	}

}
