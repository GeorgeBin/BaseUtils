package com.georgebindragon.base.thread;

import android.os.Looper;

/**
 * 创建人：George
 * 类名称：ThreadUtil
 * 类概述：Thread 相关工具
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
