package com.georgebindragon.base.thread.thread;

import com.georgebindragon.base.function.log.LogProxy;


/**
 * 创建人：George
 * 类名称：BaseLoopAndWaitThread
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseLoopAndWaitThread extends BaseLoopThread
{
	private static final int DefaultCount = 5;
	private static final int DefaultSleep = 10;

	@Override
	public void onLoop()
	{
		LogProxy.v(TAG, "onLoop");
	}

	@Override
	public void onPause()
	{
		pauseCount = 1;
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}


	private int pauseCount = 1;

	public void resetPauseCount()
	{
		pauseCount = 1;
		LogProxy.v(TAG, "resetPauseCount");
	}

	//---------------------------------------------------------参数设置---------------------------------------------------------
	private int maxCount    = DefaultCount;
	private int sleepTimeMS = DefaultSleep;

	public void setCount(int maxCount, int sleepTimeMS)
	{
		this.maxCount = (maxCount <= 0) ? DefaultCount : maxCount;

		this.sleepTimeMS = (sleepTimeMS <= 0) ? DefaultSleep : sleepTimeMS;
	}

	//---------------------------------------------------------内部方法---------------------------------------------------------
	protected void pauseOnCount()
	{
		pauseOnCount(maxCount, sleepTimeMS);
	}

	private void pauseOnCount(int maxCount, int sleepTimeMS)
	{
		if (pauseCount <= maxCount)
		{
			if (sleepTimeMS > 0)
			{
				try
				{
					Thread.sleep(sleepTimeMS);
				} catch (InterruptedException e)
				{
					LogProxy.e(TAG, "pauseOnCount", e);
				}
				LogProxy.v(TAG, "thread  sleep -->" + sleepTimeMS + "<--thread  sleep ");
			}
			pauseCount++;
		} else
		{
			pause();
		}
	}
}
