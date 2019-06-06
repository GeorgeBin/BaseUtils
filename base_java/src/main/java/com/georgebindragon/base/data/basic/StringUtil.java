package com.georgebindragon.base.data.basic;


import com.georgebindragon.base.utils.EmptyUtil;

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
}
