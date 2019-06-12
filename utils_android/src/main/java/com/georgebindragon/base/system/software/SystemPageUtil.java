package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：SystemPageUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SystemPageUtil
{
	private static final String TAG = "SystemPageUtil-->";

	/**
	 * 打开 Gps 设置界面
	 */
	public static boolean jumpToGpsSettingPage(Context context)
	{
		return jumpToXXXSettingPage(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	}

	/**
	 * 打开 Accessibility 设置界面
	 */
	public static boolean jumpToAccessibilitySettingPage(Context context)
	{
		return jumpToXXXSettingPage(context, Settings.ACTION_ACCESSIBILITY_SETTINGS);
	}

	/**
	 * 打开 系统 设置界面
	 */
	public static boolean jumpToXXXSettingPage(Context context, String action)
	{
		Intent intent = new Intent(action);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		boolean isSuccess = jumpPage(context, intent);
		LogProxy.d(TAG, "jumpToXXXSettingPage", "Action=" + action + " 跳转成功与否=" + Boolean.toString(isSuccess));
		return isSuccess;
	}

	public static boolean jumpPage(Context context, Intent intent)
	{
		if (EmptyUtil.notEmpty(context, intent))
		{
			try
			{
				context.startActivity(intent);
				return true;
			} catch (Exception e)
			{
				LogProxy.e(TAG, "jumpPage", e);
				return false;
			}
		}
		return false;
	}
}
