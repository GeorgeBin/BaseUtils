package com.georgebindragon.base.app.lifecycle;

import android.content.Context;

import com.georgebindragon.base.crash.CrashUtil;
import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class Lifecycle_App_Control
{
	private static final String TAG = "Lifecycle_App_Control-->";

	public static void restartAppByPhoenix(Context context)
	{
		ProcessPhoenix.triggerRebirth(context);
	}

	public static void restartAppByAlarm(Context context)
	{
		CrashUtil.restartApp(context);
	}

}