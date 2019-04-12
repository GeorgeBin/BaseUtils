package com.georgebindragon.base.thread;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：ThreadUtil_Java
 * 类概述：Thread 相关工具
 * 创建时间：2017-06-23 13:47
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ThreadUtil_Java
{
	private static final String TAG = "ThreadUtil_Java-->";

	public static long getThreadId()
	{
		return Thread.currentThread().getId();
	}

	public static void sleep(long mills)
	{
		try
		{
			Thread.sleep(mills);
		} catch (InterruptedException e)
		{
			LogProxy.e(TAG, "Thread.sleep 出错", e);
		}
	}
}
