package com.georgebindragon.base.utils;

import android.os.SystemClock;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：TimeUtil
 * 类概述：工具类：获取各种时间
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
