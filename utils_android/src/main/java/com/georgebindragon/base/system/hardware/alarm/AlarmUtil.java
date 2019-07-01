package com.georgebindragon.base.system.hardware.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：AlarmUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AlarmUtil
{
	private static final String TAG = "AlarmUtil-->";

	final static String EXTRA_ID  = "ID";
	final static String EXTRA_PID = "PID";
	final static String alarmAction = "ALARM_ACTION(" + String.valueOf(Process.myPid()) + ")";

	public static PendingIntent setAlarm(final long alarmID, final long afterMilliSeconds)
	{
		return setAlarm(alarmID, afterMilliSeconds, null);
	}

	public static PendingIntent setAlarm(final long alarmID, final long afterMilliSeconds, Context context)
	{
		if (context == null) context = BaseUtils.getContext();
		final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (am == null)
		{
			LogProxy.e(TAG, "am == null");
			return null;
		}

		Intent intent = new Intent();
		intent.setAction("ALARM_ACTION(" + String.valueOf(Process.myPid()) + ")");
		intent.putExtra(EXTRA_ID, alarmID);
		intent.putExtra(EXTRA_PID, Process.myPid());

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) alarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			am.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, afterMilliSeconds, pendingIntent);
			am.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, afterMilliSeconds, pendingIntent);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, afterMilliSeconds, pendingIntent);
		}

		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, afterMilliSeconds, pendingIntent);

		return pendingIntent;
	}


	public static boolean cancelAlarm(Context context, PendingIntent pendingIntent)
	{
		if (context == null) context = BaseUtils.getContext();
		final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (am == null)
		{
			LogProxy.e(TAG, "am == null");
			return false;
		}
		if (pendingIntent == null)
		{
			LogProxy.e(TAG, "pendingIntent == null");
			return false;
		}

		am.cancel(pendingIntent);
		pendingIntent.cancel();
		return true;
	}
}
