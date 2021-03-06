package com.georgebindragon.base.system.hardware.battery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.ActivityUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import static android.content.Context.BATTERY_SERVICE;

/**
 * 创建人：George
 * 类名称：BatteryUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BatteryUtil
{
	private static final String TAG = "BatteryUtil-->";

	public static int getBatteryPercent()
	{
		int percent = -1;
		try
		{
			Intent batteryInfoIntent = BaseUtils.getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			percent = getBatteryPercentFromIntent(batteryInfoIntent);

			if (percent <= 0)
			{
				if (Build.VERSION.SDK_INT >= 21)
				{
					BatteryManager batteryManager = (BatteryManager) BaseUtils.getContext().getSystemService(BATTERY_SERVICE);
					if (EmptyUtil.notEmpty(batteryManager))
					{
						int CHARGE_COUNTER  = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
						int CURRENT_AVERAGE = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
						int CURRENT_NOW     = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
						int CAPACITY        = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
						int ENERGY_COUNTER  = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);

						percent = CAPACITY;
						LogProxy.i(TAG, "getBatteryPercent-->CAPACITY=" + CAPACITY
								, "CURRENT_AVERAGE=" + CURRENT_AVERAGE, "ENERGY_COUNTER=" + ENERGY_COUNTER
								, "CHARGE_COUNTER=" + CHARGE_COUNTER, "CURRENT_NOW=" + CURRENT_NOW);
					}
				}
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getBatteryPercent", e);
		}
		return percent;
	}

	public static int getBatteryPercentFromIntent(Intent intent)
	{
		if (EmptyUtil.notEmpty(intent) && Intent.ACTION_BATTERY_CHANGED.equalsIgnoreCase(intent.getAction()))
		{
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);//获取当前电量
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);//电量的总刻度
			LogProxy.i(TAG, "getBatteryPercentFromIntent-->level=" + level, "scale=" + scale);

			//把它转成百分比
			if (scale == 100)//普通情况
			{
				return level;
			} else //异常情况
			{
				if (level > 0 && scale > 0)
				{
					return (level * 100) / scale;
				}
			}
			return level;
		}
		return -1;
	}

	/**
	 * {@link BatteryManager#BATTERY_STATUS_UNKNOWN }=1
	 * {@link BatteryManager#BATTERY_STATUS_CHARGING }=2
	 * {@link BatteryManager#BATTERY_STATUS_DISCHARGING }=3
	 * {@link BatteryManager#BATTERY_STATUS_NOT_CHARGING }=4
	 * {@link BatteryManager#BATTERY_STATUS_FULL }=5
	 *
	 * @param intent 电池电量变化广播
	 * @return 电池当前状态
	 */
	public static int getBatteryStatusFromIntent(Intent intent)
	{
		if (EmptyUtil.notEmpty(intent) && Intent.ACTION_BATTERY_CHANGED.equalsIgnoreCase(intent.getAction()))
		{

			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);//获取当前状态
			LogProxy.i(TAG, "getBatteryStatusFromIntent-->status=" + status);
			return status;
		}
		return -1;
	}


	public static boolean isIgnoringBatteryOptimizations(Context context, String packageName)
	{
		try
		{
			if (null == context) context = BaseUtils.getContext();
			if (EmptyUtil.notEmpty(packageName) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			{
				PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
				if (pm != null) return pm.isIgnoringBatteryOptimizations(packageName);
			} else return true;
		} catch (Exception e) { LogProxy.e(TAG, "isIgnoringBatteryOptimizations", e); }

		return false;
	}

	@SuppressLint("BatteryLife") // 这个提示是告诉我们，Google play store 不让直接申请添加白名单
	public static void gotoSettingIgnoringBatteryOptimizations_WithoutResult(String packageName)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			try
			{
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
				intent.setData(Uri.parse("package:" + packageName));
				PackageManager pm = BaseUtils.getContext().getPackageManager();
				if (null != pm && intent.resolveActivity(pm) != null)
				{
					ActivityUtil.jumpActivity(BaseUtils.getContext(), intent);
				} else
				{
					LogProxy.d(TAG, "Market client not available.");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("BatteryLife") // 这个提示是告诉我们，Google play store 不让直接申请添加白名单
	public static void gotoSettingIgnoringBatteryOptimizations(Activity activity, String packageName, int requestCode)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			try
			{
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
				intent.setData(Uri.parse("package:" + packageName));
				PackageManager pm = activity.getPackageManager();
				if (null != pm && intent.resolveActivity(pm) != null)
				{
					activity.startActivityForResult(intent, requestCode);
				} else
				{
					LogProxy.d(TAG, "Market client not available.");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}