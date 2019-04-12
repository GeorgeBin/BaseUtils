package com.georgebindragon.base.thread.runnable;


import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 *
 * 类名称：BaseRunnable
 * 类概述：
 * 创建时间：2018-08-03 14:32
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseRunnable implements Runnable
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	@Override
	public void run()
	{
		try
		{
			isRunning = true;
			onRun();
			isRunning = false;
		} catch (Exception e)
		{
			isPausing = false;
			isRunning = false;
			LogProxy.e(TAG, "run", e);
		}
	}

	protected volatile boolean isRunning;
	protected volatile boolean isPausing;

	public boolean isRunning()
	{
		return isRunning;
	}

	public boolean isPausing()
	{
		return isPausing;
	}

	//线程控制
	private static final Object lock = new Object();

	/**
	 * 暂停线程
	 */
	protected void pause()
	{
		LogProxy.v(TAG, "pause");
		synchronized (lock)
		{
			try
			{
				isPausing = true;
				lock.wait();
				onPause();
			} catch (InterruptedException e)
			{
				isPausing = false;
				LogProxy.e(TAG, "pause", e);
			}
		}
	}

	/**
	 * 恢复线程的运行
	 */
	protected void resume()
	{
		LogProxy.v(TAG, "resume");
		synchronized (lock)
		{
			isPausing = false;
			lock.notifyAll();
			onResume();
		}
	}

	public abstract void onRun();

	public abstract void onPause();

	public abstract void onResume();
}
