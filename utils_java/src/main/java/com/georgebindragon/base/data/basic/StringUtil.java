package com.georgebindragon.base.data.basic;


import com.georgebindragon.base.utils.EmptyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建人：George
 * 类名称：StringUtil
 * 类概述：String类型相关工具
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StringUtil
{
	private static final String TAG = "StringUtil-->";

	public static boolean checkLength(String str, int minLength)
	{
		return checkLength(str, minLength, Integer.MAX_VALUE);
	}

	public static boolean checkLength(String str, int minLength, int maxLength)//包含minLength和maxLength
	{
		boolean result = false;
		if (checkNotEmpty(str))
		{
			int length = str.length();
			if (length >= minLength && length <= maxLength)
			{
				result = true;
			}
		}
		return result;
	}

	public static boolean checkNotEmpty(String str)
	{
		return EmptyUtil.notEmpty(str);
	}

	public static final String IPV4 = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	/**
	 * @param ipAddress ip address
	 * @return is this ip address IPv4
	 */
	public static boolean isIPv4(String ipAddress)
	{
		if (EmptyUtil.isEmpty(ipAddress)) return false;
		if (ipAddress.length() < 7 || ipAddress.length() > 15) return false;

		Pattern pattern = Pattern.compile(IPV4);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
}
