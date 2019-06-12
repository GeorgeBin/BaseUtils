package com.georgebindragon.base.system.software;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;


/**
 * 2017/5/16
 */

public class InternetConnectionUtil
{
	public static final String TAG = "InternetConnectionUtil-->";

	@SuppressLint("MissingPermission")
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (EmptyUtil.notEmpty(manager))
		{
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			boolean     result      = (EmptyUtil.notEmpty(networkinfo) && networkinfo.isAvailable());
			LogProxy.d(TAG , "isNetworkAvailable=" + result);
			return result;
		}
		return false;
	}

	public static boolean isAirPlaneModeOn(Context context)
	{

		if (Build.VERSION.SDK_INT >= 17)
		{
			int mode = 0;
			try
			{
				mode = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON);
			} catch (Settings.SettingNotFoundException e)
			{
				e.printStackTrace();
			}
			return mode == 1;//为1的时候是飞行模式
		} else
		{
			return false;
		}
	}

}
