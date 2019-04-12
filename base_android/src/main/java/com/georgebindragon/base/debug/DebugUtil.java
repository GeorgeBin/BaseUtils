package com.georgebindragon.base.debug;


import com.georgebindragon.base.android.BuildConfig;

/**
 * 创建人：George
 * 类名称：DebugUtil
 * 类概述：debug 用途的工具类
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class DebugUtil
{
	public static boolean isBuildDebug()
	{
		return BuildConfig.DEBUG;
	}

	public static void makeACrash()
	{
		if(isBuildDebug()) throw new RuntimeException("Boom!");//谨慎使用, 只能在debug版本里出现
	}
}
