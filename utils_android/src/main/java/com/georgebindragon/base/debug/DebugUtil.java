package com.georgebindragon.base.debug;


import android.content.pm.ApplicationInfo;

import com.georgebindragon.base.BaseUtils;
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
		return BuildConfig.DEBUG;//做为库, 一直是complete版本, 所以一直返回false
	}

	public static boolean isDebug()
	{
		return BaseUtils.getContext().getApplicationInfo() != null
				&& (BaseUtils.getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
	}

	public static void makeACrash()
	{
		throw new RuntimeException("Boom!");//谨慎使用, 只能在debug版本里出现
	}

	public static void makeADebugCrash()
	{
		if (isDebug()) makeACrash();// 只能在debug版本里生效
	}
}
