package com.georgebindragon.base.system.software;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.List;

/**
 * 创建人：George
 * 类名称：WindowsUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class WindowsUtil
{
	private static final String TAG = "WindowsUtil-->";

	/**
	 * 需要权限：
	 * {@link android.Manifest.permission#GET_TASKS}
	 *
	 * @param context 上下文
	 * @return 是否在前台
	 */
	public static boolean isAppForeground(Context context)
	{
		if (EmptyUtil.notEmpty(context))
		{
			ActivityManager                       activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks           = null;
			if (EmptyUtil.notEmpty(activityManager))
			{
				tasks = activityManager.getRunningTasks(1);
			}
			if (tasks != null && !tasks.isEmpty())
			{
				ComponentName topActivity = tasks.get(0).topActivity;
				if (topActivity.getPackageName().equals(context.getPackageName()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 需要权限：
	 * {@link android.Manifest.permission#REORDER_TASKS}
	 *
	 * @param context 上下文
	 * @param taskId  当前task的id
	 */
	@SuppressLint("MissingPermission")
	public static void moveAppToForeground(Context context, int taskId)
	{
		if (EmptyUtil.notEmpty(context))
		{
			try
			{
				ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
				if (EmptyUtil.notEmpty(am))
				{
					am.moveTaskToFront(taskId, ActivityManager.MOVE_TASK_WITH_HOME);
					LogProxy.d(TAG, "onStop()-->回到前台");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "moveAppToForeground", e);
			}
		}
	}
}
