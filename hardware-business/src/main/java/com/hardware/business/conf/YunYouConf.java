package com.hardware.business.conf;

import com.hardware.business.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 云柚门锁接口配置信息
 * 
 * @author zq
 *
 */
public class YunYouConf {

	public static final String TEST_HOST = "http://api.yeeuu.com";
	public static final String TEST_PARTNERID = "58732d489e236217270000ee";
	public static final String TEST_SECRET = "cJdz6EsZ";
	public static final String TEST_KEY = "cJdz6EsZ";

	public static String HOST = "http://api.yeeuu.com";
	public static String PARTNERID = "5824298a9e23624b40000017";
	public static String SECRET = "pt5GckRy";
	public static String KEY = "pt5GckRy";

	static {
		// 临时解决，其实已经全部在数据库里面了
		Properties properties = PropertiesUtils.getProperties();
		String account = properties.getProperty("account");
		if (StringUtils.equals("test", account)) {
			HOST = TEST_HOST;
			PARTNERID = TEST_PARTNERID;
			SECRET = TEST_SECRET;
			KEY = TEST_KEY;
		}
	}

}
