package com.hardware.business.utils;

import java.math.BigDecimal;

public class DataUtil {
	public static BigDecimal getValue(String s)
	{
		if(s.equals(""))
		{
			return new BigDecimal(s);
		}else
		{
			BigDecimal val = new BigDecimal(s);
			return val.setScale(2, val.ROUND_DOWN);
		}
	}
}
