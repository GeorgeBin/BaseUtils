package com.georgebindragon.base.thread;

import android.os.Handler;
import android.os.Looper;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 *
 * 描述：Handler工具类
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class HandlerUtil
{
	private static final String TAG = "HandlerUtil-->";

	private static final Handler MainHandler  = new Handler(Looper.getMainLooper());
	private static final Handler otherHandler = new Handler();

	public static Handler getMainHandler()
	{
		return MainHandler;
	}

	public static Handler getOtherHandler()
	{
		return otherHandler;
	}

	public static void init()
	{
		LogProxy.e(TAG, "init-->主动初始化, 避免在线程中, 无法创建 Handler");
	}

	public static Handler postRunnableOnMainThread(Runnable runnable)
	{
		return postDelayedRunnable(MainHandler, runnable, -1);
	}

	public static Handler postDelayedRunnableOnMainThread(Runnable runnable, long delayMillis)
	{
		return postDelayedRunnable(MainHandler, runnable, delayMillis);
	}

	public static Handler postRunnable(Handler handler, Runnable runnable)
	{
		return postDelayedRunnable(handler, runnable, -1);
	}

	public static Handler postDelayedRunnable(Handler handler, Runnable runnable, long delayMillis)
	{
		if (EmptyUtil.notEmpty(runnable))
		{
			if (EmptyUtil.isEmpty(handler)) handler = otherHandler;

			if (delayMillis > 0)
			{
				handler.postDelayed(runnable, delayMillis);
			} else
			{
				handler.post(runnable);
			}
			return handler;
		} else
		{
			LogProxy.e(TAG, "postDelayedRunnable-->参数错误");
			return null;
		}
	}
}
