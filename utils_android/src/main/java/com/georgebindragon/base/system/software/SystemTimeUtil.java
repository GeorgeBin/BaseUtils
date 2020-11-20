package com.georgebindragon.base.system.software;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 创建人：George
 * 类名称：SystemTimeUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SystemTimeUtil
{
	private static final String TAG = "SystemTimeUtil-->";

	//---------------------------------------- 设置系统时间：具体时间值 ---------------------------------------------------

	public static boolean setAndroidSystemTime(Context mContext, long timeInMillis)
	{
		boolean method1 = setAndroidSystemTime_AlarmManager(mContext, timeInMillis);
		boolean method2 = false;

		if (!method1)
		{
			method2 = setAndroidSystemTime_SystemClock(timeInMillis);
		}
		return method1 || method2;
	}

	private static boolean setAndroidSystemTime_AlarmManager(Context mContext, long timeInMillis)// 方法1：
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			if (alarmManager != null)
			{
				alarmManager.setTime(timeInMillis);
				return true;
			} else
			{
				LogProxy.e(TAG, "setAndroidSystemTime_AlarmManager AlarmManager==NULL");
			}
		} catch (Exception e) { LogProxy.e(TAG, "setAndroidSystemTime_AlarmManager", e); }
		return false;
	}

	private static boolean setAndroidSystemTime_SystemClock(long timeInMillis)// 方法2：
	{
		try
		{
			SystemClock.setCurrentTimeMillis(timeInMillis);
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setAndroidSystemTime_SystemClock", e); }
		return false;
	}

	//---------------------------------------- 系统时间：时区 ---------------------------------------------------

	public static boolean isTimeZoneAuto(Context mContext)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				return Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME_ZONE) > 0;
			}
		} catch (Exception e) { LogProxy.e(TAG, "isTimeZoneAuto", e); }
		return false;
	}

	public static boolean setTimeZoneAuto(Context mContext, boolean auto)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, isOn(auto));
			}
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setTimeZoneAuto", e); }
		return false;
	}

	public static String getTimeZone()
	{
		try
		{
			return TimeZone.getDefault().getID();
		} catch (Exception e) { LogProxy.e(TAG, "getTimeZone", e); }
		return "";
	}

	public static boolean setTimeZone(Context mContext, String timeZoneString)
	{
		LogProxy.i(TAG, "setTimeZone-->timeZoneString=" + StringUtil.getPrintString(timeZoneString));

		try
		{
			LogProxy.i(TAG, "setTimeZone-->setTimeZone_AlarmManager");

			if (null == mContext) mContext = BaseUtils.getContext();
			AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			if (alarmManager != null)
			{
				alarmManager.setTimeZone(timeZoneString);
				return true;
			} else
			{
				LogProxy.e(TAG, "setTimeZone_AlarmManager AlarmManager==NULL");
			}
		} catch (Exception e) { LogProxy.e(TAG, "setTimeZone_AlarmManager", e); }

		try
		{
			LogProxy.i(TAG, "setTimeZone-->setTimeZone_Calendar");

			final Calendar now      = Calendar.getInstance();
			TimeZone       timeZone = TimeZone.getTimeZone(timeZoneString);
			now.setTimeZone(timeZone);
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setTimeZone_Calendar", e); }
		return false;
	}

	//---------------------------------------- 系统时间：自动获取 ---------------------------------------------------

	public static boolean isDateTimeAuto(Context mContext)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				return Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME) > 0;
			}
		} catch (Exception e) { LogProxy.e(TAG, "isDateTimeAuto", e); }
		return false;
	}

	public static boolean setDateTimeAuto(Context mContext, boolean on)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, isOn(on));
			}
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setDateTimeAuto", e); }
		return false;
	}


	//---------------------------------------- 系统时间：24小时制 ---------------------------------------------------

	public static boolean is24HourFormat(Context mContext)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			return DateFormat.is24HourFormat(mContext);
		} catch (Exception e) { LogProxy.e(TAG, "is24HourFormat", e); }
		return false;
	}

	public static boolean set24HourFormat(Context mContext, boolean is24)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			android.provider.Settings.System.putString(mContext.getContentResolver(), android.provider.Settings.System.TIME_12_24, is24 ? "24" : "12");
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setAutoTimeZone", e); }
		return false;
	}


	//---------------------------------------- 系统时间：时间服务器 ---------------------------------------------------
	private static final String NTP_SERVER = "ntp_server";

	public static String getNTPServer(Context mContext)
	{
		try
		{
			if (null == mContext) mContext = BaseUtils.getContext();
			return android.provider.Settings.System.getString(mContext.getContentResolver(), NTP_SERVER);
		} catch (Exception e) { LogProxy.e(TAG, "getNTPServer", e); }
		return null;
	}

	public static boolean setNTPServer(Context mContext, String server)
	{
		try
		{
			if (null == server) return false;
			if (null == mContext) mContext = BaseUtils.getContext();
			android.provider.Settings.System.putString(mContext.getContentResolver(), NTP_SERVER, server);
			return true;
		} catch (Exception e) { LogProxy.e(TAG, "setAutoTimeZone", e); }
		return false;
	}


	//---------------------------------------- 内部方法 ---------------------------------------------------

	private static int isOn(boolean on)
	{
		return on ? 1 : 0;
	}
}
