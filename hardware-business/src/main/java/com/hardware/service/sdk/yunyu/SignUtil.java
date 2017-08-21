package com.hardware.service.sdk.yunyu;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class SignUtil {
	
	public static final String CHARSET_UTF8 = "UTF-8";
	
	public static final String SIGN_METHOD_MD5 = "md5";
	
	public static final String SIGN_METHOD_HMAC = "hmac";

	/**
	 * 对请求参数进行签名
	 * @param params 请求参数（除sign）
	 * @param secret 应用密钥
	 * @param signMethod 签名算法
	 * @return 签名
	 * @throws IOException
	 */
	public static String signTjyRequest(Map<String, String> params, String secret, String signMethod)
			throws IOException {
		// 第一步：检查参数是否已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		if (SIGN_METHOD_MD5.equals(signMethod)) {
			query.append(secret);
		}
		for (String key : keys) {
			String value = params.get(key);
			if (areNotEmpty(key, value)) {
				query.append(key).append(value);
			}
		}

		// 第三步：使用MD5/HMAC加密
		byte[] bytes;
		if (SIGN_METHOD_HMAC.equals(signMethod)) {
			bytes = encryptHMAC(query.toString(), secret);
		} else {
			query.append(secret);
			bytes = encryptMD5(query.toString());
		}

		// 第四步：把二进制转化为大写的十六进制
		return byte2hex(bytes);
	}

	private static byte[] encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.toString());
		}
		return bytes;
	}

	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg);
		}
		return bytes;
	}
	
	private static String getStringFromException(Throwable e) {
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		e.printStackTrace(ps);
		try {
			result = bos.toString(CHARSET_UTF8);
		} catch (IOException ioe) {
		}
		return result;
	}

	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}
	
	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	private static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	private static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
	
	static class RequestParams extends HashMap<String, String>{
		private static final long serialVersionUID = 1L;
		public RequestParams(String appId, String signMethod){
		
			this.put("appId", appId);
			this.put("signMethod", signMethod);
			this.put("timestamp", String.valueOf(System.currentTimeMillis()));
		}
		
		public RequestParams param(String key, String value){
			this.put(key, value);
			return this;
		}
	}

}
