package com.georgebindragon.base.utils;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：StringUtil
 * 类概述：字符串相关工具类
 * 详细描述：
 *
 * 1、获取可打印字符串
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class StringUtil
{
	private static final String TAG = "StringUtil-->";

	public static final String NULL = "NULL";

	public static String getSuccessString(boolean success)
	{
		return success ? "成功" : "失败";
	}

	public static String getPrintString(Object object)
	{
		return getPrintString(object, NULL);//返回可安全打印的字符串
	}

	public static String getPrintString(Object object, String defaultValue)
	{
		try
		{
			if (EmptyUtil.isEmpty(defaultValue)) defaultValue = NULL;
			return EmptyUtil.isEmpty(object) ? defaultValue : object.toString();//返回可安全打印的字符串
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getPrintString", e);
			e.printStackTrace();
		}
		return "Exception";
	}

}
