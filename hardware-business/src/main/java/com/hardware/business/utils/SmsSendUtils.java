package com.hardware.business.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hardware.business.client.HttpRequestUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SmsSendUtils {

	public static boolean sendSms(String mobile, String smsMsg) {
		Properties config = PropertiesUtils.getProperties();
		String smsPath = config.getProperty("SMS_PATH");
		StringBuilder url = new StringBuilder(smsPath);
		Map<String, Object> params = new HashMap<>();
		params.put("smsChannelName", "lhwt");
		params.put("projectName", "zhsh");
		params.put("smsSendTo", mobile);
		params.put("smsSendMsg", smsMsg);
		params.put("batchSend", false);
		String post = HttpRequestUtils.post(url.toString(), params);
		JSONObject smsData = JSON.parseObject(post, JSONObject.class);
		if (ObjectUtils.toString(smsData.getString("innjiaMessageJsonResponseStatus"), "").equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		StringBuilder url = new StringBuilder("http://114.55.234.160:8080/message-service/service/smsSend.json");
		Map<String, Object> params = new HashMap<>();
		params.put("smsChannelName", "lhwt");
		params.put("projectName", "zhsh");
		params.put("smsSendTo", "13262833967");
		params.put("smsSendMsg", "亲爱的用户，您的门锁随机密码123456已经设置成功，请尽快登陆盈家APP修改设置密码。如有任何疑问，请咨询您的在线管家，谢谢。");
		params.put("batchSend", false);
		String post = HttpRequestUtils.post(url.toString(), params);
		JSONObject smsData = JSON.parseObject(post, JSONObject.class);
		System.out.println(smsData);
	}
}
