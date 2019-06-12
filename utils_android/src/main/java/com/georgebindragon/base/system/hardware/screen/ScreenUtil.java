package com.georgebindragon.base.system.hardware.screen;

import android.content.Context;
import android.content.pm.PackageManager;

import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：ScreenUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ScreenUtil
{
	public static boolean hasTouchScreen(Context context)
	{
		if (EmptyUtil.notEmpty(context))
		{
			return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
					|| context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
		}
		return false;
	}
}
