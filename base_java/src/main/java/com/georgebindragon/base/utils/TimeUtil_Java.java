package com.georgebindragon.base.utils;

/**
 * 创建人：George
 * 类名称：TimeUtil_Java
 * 类概述：工具类：获取各种时间
 * 详细描述：获取自系统开机后的时长
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TimeUtil_Java
{
	private static final String TAG = "TimeUtil_Java-->";

	public static long getMilliSeconds()
	{
		return System.currentTimeMillis();
	}

}
