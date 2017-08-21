package com.hardware.business.utils;

import java.util.regex.Pattern;

public class CommonUtils {

	 public static boolean isNumeric(String str){  
		    Pattern pattern = Pattern.compile("[0-9]*");  
		    return pattern.matcher(str).matches();     
		}
}
