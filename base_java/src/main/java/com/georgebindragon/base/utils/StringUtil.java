package com.georgebindragon.base.utils;

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
	public static final String NULL = "NULL";

	public static String getPrintString(Object object)
	{
		return getPrintString(object, NULL);//返回可安全打印的字符串
	}

	public static String getPrintString(Object object, String defaultValue)
	{
		if (EmptyUtil.isEmpty(defaultValue)) defaultValue = NULL;
		return EmptyUtil.isEmpty(object) ? defaultValue : object.toString();//返回可安全打印的字符串
	}

}
