package com.georgebindragon.base.utils;

import android.os.SystemClock;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：TimeUtil
 * 类概述：工具类：获取各种时间
 * 创建时间：2017-06-26 15:38
 * 详细描述：获取自系统开机后的时长
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TimeUtil extends TimeUtil_Java
{
	private static final String TAG = "TimeUtil-->";

	/**
	 * Returns milliseconds since boot, including time spent in sleep.
	 *
	 * @return elapsed milliseconds since boot.
	 */
	public static long getMilliSecondsSinceBoot()
	{
		return SystemClock.elapsedRealtime();
	}

	public static long getTimeDiffByMilliSecondsSinceBoot(long oldTime)
	{
		long now = getMilliSecondsSinceBoot();
		LogProxy.i(TAG, "getTimeDiffByMilliSecondsSinceBoot-->oldTime=" + oldTime, "now=" + now);
		return now - oldTime;
	}
}
