package com.hardware.business.utils;

import com.hardware.business.exception.HardwareSdkException;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public class PropertiesUtils {
	private static Properties config = new Properties();
	static {
		// 临时解决，已经全部在数据库里面了
		try {
			config = PropertiesLoaderUtils.loadAllProperties("config.properties");
			if (config == null) {
				throw new HardwareSdkException("config.properties未找到！");
			}
		} catch (Exception e) {
			throw new HardwareSdkException(e.getMessage());
		}
	}

	private PropertiesUtils() {
	}

	public static Properties getProperties() {
		return config;
	}
}
