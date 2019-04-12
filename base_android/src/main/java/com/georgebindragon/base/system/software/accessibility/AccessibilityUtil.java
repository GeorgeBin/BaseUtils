package com.georgebindragon.base.system.software.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;

/**
 * 创建人：George
 * 类名称：AccessibilityUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AccessibilityUtil
{
	private static final String TAG = "AccessibilityUtil-->";

	//判断服务是否打开
	public static boolean isPackageAccessibilitySettingsOn(Context context, String packageName, Class<? extends AccessibilityService> clasz)
	{
		final String                   service              = packageName + File.separator + clasz.getCanonicalName();
		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
		if (isSystemAccessibilityEnabled(context))
		{
			LogProxy.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
			String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (settingValue != null)
			{
				mStringColonSplitter.setString(settingValue);
				while (mStringColonSplitter.hasNext())
				{
					String accessibilityService = mStringColonSplitter.next();
					LogProxy.v(TAG, "accessibilityService=" + accessibilityService + " " + service);
					if (accessibilityService.equalsIgnoreCase(service))
					{
						LogProxy.v(TAG, "We've found the correct setting - accessibility is switched on!");
						return true;
					}
				}
			}
		} else
		{
			LogProxy.v(TAG, "***ACCESSIBILITY IS DISABLED***");
		}
		return false;
	}

	//判断服务是否打开
	public static boolean isSystemAccessibilityEnabled(Context mContext)
	{
		int accessibilityEnabled = 0;
		try
		{
			accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
					Settings.Secure.ACCESSIBILITY_ENABLED);
		} catch (Settings.SettingNotFoundException e)
		{
			LogProxy.e(TAG, "isSystemAccessibilityEnabled", e);
		}
		LogProxy.d(TAG, "isSystemAccessibilityEnabled", "accessibilityEnabled = " + accessibilityEnabled);
		return accessibilityEnabled == 1;
	}
}
