package com.georgebindragon.base.system.hardware.wifi;

import android.content.Context;
import android.provider.Settings;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：WiFiUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class WiFiUtil
{
	private static final String TAG = "WiFiUtil-->";

	public static int getWiFiSleepPolicy(Context context)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		if (EmptyUtil.notEmpty(context))
		{
			return Settings.System.getInt(context.getApplicationContext().getContentResolver(),
					Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
		}
		return -1;
	}

	public static boolean setWiFiSleepPolicy(Context context, int wiFiSleepPolicy)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		if (EmptyUtil.notEmpty(context))
		{
			if (wiFiSleepPolicy != Settings.Global.WIFI_SLEEP_POLICY_DEFAULT
					&& wiFiSleepPolicy != Settings.Global.WIFI_SLEEP_POLICY_NEVER
					&& wiFiSleepPolicy != Settings.Global.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED)//值有一定的取值范围
			{
				wiFiSleepPolicy = Settings.System.WIFI_SLEEP_POLICY_NEVER;
			}

			return Settings.System.putInt(context.getApplicationContext().getContentResolver(),
					Settings.System.WIFI_SLEEP_POLICY, wiFiSleepPolicy);

		}
		return false;
	}
}