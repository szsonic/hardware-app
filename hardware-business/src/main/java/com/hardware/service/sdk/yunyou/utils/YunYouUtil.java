package com.hardware.service.sdk.yunyou.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.conf.YunYouConf;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.exception.ThirdPartyRequestException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.http.HttpClientUtils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云铀门锁的工具类
 * 
 * @author zhongqi
 *
 */
public class YunYouUtil {

	/**
	 * 同步房源信息
	 * 
	 * @param list
	 *          房源信息的json列表
	 * @return
	 */
	public static Map<String, Object> houseSyns(List<JSONObject> list) {
		String url = "http://alms.yeeuu.com/apartments/synchronize_apartments";
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String nonstr = RandomStringUtils.random(8, "1234567890");
		String token = getSha1(timestamp + YunYouConf.SECRET + nonstr);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerId", YunYouConf.PARTNERID);
		params.put("timestamp", timestamp);
		params.put("nonstr", nonstr);
		params.put("token", token);
		params.put("apartmentList", JSON.toJSONString(list));
		System.out.println(JSON.toJSONString(params));
		String result = HttpRequestUtils.postSSL(url, params);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(result)) {
			JSONObject resultObj = JSON.parseObject(result, JSONObject.class);
			resultData.put("msg", resultObj.getString("desc"));
			resultData.put("success", resultObj.get("success"));
		}
		return resultData;
	}

	private static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		    'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Map<String, Object> getState(String sn) {
		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks/").append(sn).append("/getState");
		url.append("?key=").append(YunYouConf.KEY);
		Map<String, Object> requestParams = new HashMap<>();
		String data = HttpClientUtils.get(url.toString(), requestParams);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			resultData.put("msg", result.get("desc"));
			resultData.put("state", result.get("state"));
			resultData.put("bolt", result.get("bolt"));
			resultData.put("votage", result.get("votage"));
			resultData.put("lock", result.get("lock"));
			resultData.put("host", result.get("host"));
			resultData.put("powerLeftValue", result.get("electric"));
			if (result.getBooleanValue("success")) {
				resultData.put("wifiOnlineStatus", "on");
			} else {
				resultData.put("wifiOnlineStatus", "off");
			}
		} else {
			throw new HardwareSdkException("获取云柚门锁状态失败！");
		}
		return resultData;
	}

	public static Map<String, Object>  sitePass(String sn, String startTime, String endTime, String type,
												Integer passIndex, String password, String action, String times) throws DataErrorException, ThirdPartyRequestException {
		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks/").append(sn).append("/ext_password");
		url.append("?key=").append(YunYouConf.KEY);
		url.append("&sn=").append(sn);
		url.append("&password=").append(password);
		url.append("&type=").append(type);
		if(StringUtils.isNotBlank(startTime)){
			url.append("&startime=").append(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			url.append("&endtime=").append(endTime);
		}
		url.append("&index=").append(passIndex);
		url.append("&action=").append(action);
		if (StringUtils.isNotBlank(times))
			url.append("&times=").append(times);
		System.out.println("请求URL：" + url);
		String data = HttpClientUtils.get(url.toString());
		System.out.println("请求URL结果：" + data);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			if (result.getBooleanValue("success")) {
				resultData.put("passIndex", passIndex);
			} else {
				throw new DataErrorException("请求云柚接口新增/修改密码接口失败"+result.toJSONString());
			}
		} else {
			throw new ThirdPartyRequestException("请求云柚门锁设置密码接口异常！");
		}
		return resultData;
	}

	public static Map<String, Object> clearAllPassword(String sn) {
		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks/").append(sn).append("/clear_all_password");
		url.append("?key=").append(YunYouConf.KEY);
		url.append("&sn=").append(sn);
		url.append("&mode=0");
		String data = HttpClientUtils.get(url.toString());
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			if (result.getBooleanValue("success")) {
				resultData.put("success", "true");
			} else {
				resultData.put("success", "false");
			}
			return resultData;
		} else {
			throw new HardwareSdkException("云柚门锁清除密码失败！");
		}
	}


	public static boolean  modifyPasswordProperty(String sn,Integer index,Integer mode) throws DataErrorException,ThirdPartyRequestException {
		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks/").append(sn).append("/modify_password_property");
		url.append("?key=").append(YunYouConf.KEY);
		url.append("&sn=").append(sn);
		url.append("&action=").append(mode);
		url.append("&index=").append(index);
		String data = HttpClientUtils.get(url.toString());
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			if(!result.getBooleanValue("success")){
				throw new DataErrorException("冻结/解冻密码失败");
			}else{
				return true;
			}
		} else {
			throw new ThirdPartyRequestException("请求云柚接口冻结/解冻密码失败！");
		}
	}

	public static  boolean delPassword(String sn,Integer index)throws DataErrorException,ThirdPartyRequestException {
		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks/").append(sn).append("/operation_password");
		url.append("?key=").append(YunYouConf.KEY);
		url.append("&sn=").append(sn);
		url.append("&index=").append(index);
		String data = HttpClientUtils.get(url.toString());
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			if(!result.getBooleanValue("success")){
				throw new DataErrorException("云柚删除密码失败");
			}else{
				return true;
			}
		} else {
			throw new ThirdPartyRequestException("请求云柚接口删除密码失败！");
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		// 用户的合同时间
		List<String> jiezhiLists = FileUtils.readLines(new File("hetong.txt"));
		JSONObject o = new JSONObject();
		for (String string : jiezhiLists) {
			String[] split = StringUtils.split(string, ",");
			o.put(split[0], split[1]);
		}
		// 排除已经设置密码的用户
		List<String> yszLists = FileUtils.readLines(new File("ysz.txt"));
		JSONObject ysz_o = new JSONObject();
		for (String string : yszLists) {
			ysz_o.put(string, string);
		}
		// 需要延长密码时间的用户
		List<String> xlists = FileUtils.readLines(new File("x.txt"));
		for (String string : xlists) {
			String[] split = StringUtils.split(string, ",");
			String mobile = split[0];
			String pass = split[1];
			String devId = split[2];
			if (StringUtils.isBlank(ysz_o.getString(mobile))) {
				String shijian = o.getString(mobile);
				if (StringUtils.isNotBlank(shijian)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date edate = dateFormat.parse(shijian);
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
					String eTime = dateFormat2.format(edate);
					long currentTimeMillis = System.currentTimeMillis() - 60000 * 5;
					Date sdate = new Date(currentTimeMillis);
					String sTime = dateFormat2.format(sdate);
					// System.out.println(mobile + "-" + pass + "-" + devId + "-" +
					// shijian + "-" + sTime + "-" + eTime);
					String s = HttpClientUtils.get("http://api.yeeuu.com/v1/locks/" + devId + "/ext_password?key=cJdz6EsZ&password="
					    + pass + "&type=1&startime=" + sTime + "&endtime=" + eTime +
					    "&index=50&action=0");
					System.out.println(s);
				}
			}
		}

		//
		// long currentTimeMillis = System.currentTimeMillis() - 60000 * 5;
		// Date sdate = new Date(currentTimeMillis);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		// String sTime = dateFormat.format(sdate);
		// Date edate = new Date(currentTimeMillis + 1000 * 60 * 60);
		// String eTime = dateFormat.format(edate);
		// String random = RandomStringUtils.random(6, "0987654321");
		// String d =
		// HttpClientUtils.get("http://api.yeeuu.com/v1/locks/00124b000ec53cdf/ext_password?key=cJdz6EsZ&password="
		// + random + "&type=1&startime=" + sTime + "&endtime=" + eTime +
		// "&index=50&action=0");
		// System.out.println(d);
		// System.out.println(random);
	}
}
