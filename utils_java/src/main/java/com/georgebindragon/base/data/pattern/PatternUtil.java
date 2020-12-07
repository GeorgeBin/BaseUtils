package com.georgebindragon.base.data.pattern;


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
public class PatternUtil
{
	private static final String TAG = "PatternUtil-->";

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
