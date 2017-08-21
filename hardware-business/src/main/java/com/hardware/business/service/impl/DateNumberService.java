package com.hardware.business.service.impl;

import org.apache.commons.lang.StringUtils;
import org.iframework.commons.utils.date.DatetimeUtils;
import org.iframework.commons.utils.log.Log;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class DateNumberService {
	@Resource(name = "memcachedManager")
	private MemcachedManager memcachedManager;

	public static Integer number;
	public static String date;
	static {
		number = 1;
		date = DatetimeUtils.format(new Date(), "yyyyMMdd");
	}

	public synchronized String getDateNumber() {
		Log.i("-----------------CREATE DATE NUMBER BEGIN-----------------");
		String DATE = this.memcachedManager.loadObject("DATE");
		Integer NUMBER = this.memcachedManager.loadObject("NUMBER");
		if (StringUtils.isEmpty(DATE) || NUMBER != null && NUMBER > 0) {
			number = NUMBER;
			date = DATE;
		}
		String now = DatetimeUtils.format(new Date(), "yyyyMMdd");
		if (StringUtils.equals(date, now)) {
			++number;
			this.memcachedManager.cacheObject("NUMBER", number, MemcachedManager.CACHE_EXP_FOREVER);
		} else {
			date = now;
			number = 1;
			this.memcachedManager.cacheObject("DATE", date, MemcachedManager.CACHE_EXP_FOREVER);
			this.memcachedManager.cacheObject("NUMBER", number, MemcachedManager.CACHE_EXP_FOREVER);
		}
		int numberSize = number.toString().length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5 - numberSize; i++) {
			sb.append("0");
		}
		sb.append(number.toString());
		Log.i("-----------------CREATE DATE NUMBER END-----------------");
		return now + sb.toString();
	}
}