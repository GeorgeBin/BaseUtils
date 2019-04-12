package com.georgebindragon.base.thread;

import android.os.Looper;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：ThreadUtil
 * 类概述：Thread 相关工具
 * 创建时间：2017-06-23 13:47
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ThreadUtil extends ThreadUtil_Java
{
	private static final String TAG = "ThreadUtil-->";

	public static boolean isMainThread()
	{
		return Thread.currentThread() == Looper.getMainLooper().getThread();
	}

	public static int getThreadPid()
	{
		return android.os.Process.myPid();
	}
}
