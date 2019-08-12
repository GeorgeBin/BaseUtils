package com.georgebindragon.base.crash;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 创建人：George
 * 类名称：CrashUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CrashUtil
{
	private final static String TAG = "CrashUtil-->";

	@SuppressWarnings("unchecked")
	private static Class<? extends Activity> getLauncherActivity(Context context)
	{
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		if (intent != null && intent.getComponent() != null)
		{
			try
			{
				return (Class<? extends Activity>) Class.forName(intent.getComponent().getClassName());
			} catch (ClassNotFoundException e)
			{
				Log.e(TAG, "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!", e);
			}
		}
		return null;
	}

	public static void restartApp(Context context)
	{
		Class<? extends Activity> launcherActivity = getLauncherActivity(context);
		if (null != launcherActivity) restartApp(context, launcherActivity);
	}

	public static void restartApp(Context context, Class<? extends Activity> launcher)
	{
		restartApp(context, launcher, 800);
	}

	public static void restartApp(Context context, Class<? extends Activity> launcher, long mills)
	{
		Intent        intent        = new Intent(context, launcher);
		PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager  mgr           = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (mgr != null)
		{
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + mills, restartIntent); //mills 后重启应用
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		}
	}
}
