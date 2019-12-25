package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SystemPageUtil
{
	private static final String TAG = "SystemPageUtil-->";

	public static boolean jumpToSettingPage_App(String packageName)
	{
		try
		{
			Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri    uri    = Uri.fromParts("package", packageName, null);
			intent.setData(uri);

			boolean find = ActivityUtil.hasActivityIntent(BaseUtils.getContext(), intent);
			if (!find) intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);

			return ActivityUtil.jumpActivity(BaseUtils.getContext(), intent);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "jumpToSettingPage_App", e);
		}
		return false;
	}

	/**
	 * 打开 Gps 设置界面
	 */
	public static boolean jumpToSettingPage_Location(Context context)
	{
		return jumpToSettingPage(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	}

	/**
	 * 打开 Accessibility 设置界面
	 */
	public static boolean jumpToSettingPage_Accessibility(Context context)
	{
		return jumpToSettingPage(context, Settings.ACTION_ACCESSIBILITY_SETTINGS);
	}

	/**
	 * 打开 系统 设置界面
	 */
	public static boolean jumpToSettingPage(Context context, String action)
	{
		boolean success = ActivityUtil.jumpActivity(context, new Intent(action));
		LogProxy.d(TAG, "jumpToSettingPage-->action=" + StringUtil.getPrintString(action), "跳转：" + StringUtil.getSuccessString(success));
		return success;
	}
}
