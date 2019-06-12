package com.georgebindragon.base.system.software;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 创建人：George
 * 类名称：AppUtil
 * 类概述：App相关工具类
 * 详细描述：
 *
 * 1、获取app包名
 * 2、获取当前进程名
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AppUtil
{
	private static final String          TAG = "AppUtil-->";

	public static boolean isMainProcess(Context context)
	{
		String packageName = AppUtil.getPackageName(context);//包名为主进程的processName
		String processName = AppUtil.getProcessName();//根据当前的pid, 获取 processName

		LogProxy.d(TAG, "checkProcessName", "packageName=" + StringUtil.getPrintString(packageName), "processName=" + StringUtil.getPrintString(processName));

		if (EmptyUtil.notEmpty(packageName, processName))
		{
			return (packageName.equals(processName));//确认包名和进程名相同
		}
		return false;
	}

	public static String getPackageName(Context context)
	{
		if (context != null)
		{
			return context.getApplicationContext().getPackageName();
		}
		return "";
	}

	private static String getProcessName(Context context, int pid)
	{
		if (context != null)
		{
			ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

			List<ActivityManager.RunningAppProcessInfo> runningApps = null;
			if (am != null)
			{
				runningApps = am.getRunningAppProcesses();
			}
			if (runningApps == null)
			{
				return null;
			}
			for (ActivityManager.RunningAppProcessInfo procInfo : runningApps)
			{
				if (procInfo.pid == pid)
				{
					return procInfo.processName;
				}
			}
		}
		return null;
	}

	public static String getProcessName()
	{
		return getProcessName(Process.myPid());
	}

	/**
	 * 获取进程号对应的进程名
	 *
	 * @param pid 进程号
	 * @return 进程名
	 */
	public static String getProcessName(int pid)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName))
			{
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable)
		{
			throwable.printStackTrace();
		} finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		return null;
	}
}
