package com.georgebindragon.base.thread.thread;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.thread.runnable.BaseRunnable;

/**
 * 创建人：George
 *
 * 类名称：BaseLoopThread
 * 类概述：
 * 创建时间：2018-08-03 15:20
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseThread extends BaseRunnable
{
	protected volatile Thread thread;

	public void startThread()
	{
		LogProxy.v(TAG, "startThread");
		if (!isRunning())//没有在运行
		{
			thread = new Thread(this);
			thread.start();
		} else if (isPausing())//在运行+被暂停
		{
			resumeThread();
		}
	}

	public void resumeThread()
	{
		resume();
	}

	public void stopThread()
	{
		LogProxy.v(TAG, "stopThread");
		try
		{
			resumeThread();//如果是暂停中，则释放暂停，结束线程
			if (null != thread)
			{
				Thread moribund = thread;
				thread = null;
				if (null != moribund) moribund.interrupt();
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "stopThread", e);
		}
	}

	@Override
	public void onPause()
	{ }

	@Override
	public void onResume()
	{ }
}
