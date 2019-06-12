package com.georgebindragon.base.thread.thread;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.thread.runnable.BaseLoopRunnable;

public abstract class BaseLoopThread extends BaseLoopRunnable
{
	protected volatile Thread thread;
	public void startThread()
	{
		LogProxy.v(TAG, "startThread-->isRunning=" + isRunning, "isPausing=" + isPausing);
		if (!isRunning())//没有在运行
		{
			setNeedLooping(true);
			thread = new Thread(this);
			thread.start();
		} else if (isPausing())//在运行+被暂停
		{
			resumeThread();
		}
	}

	public void resumeThread()
	{
		LogProxy.v(TAG, "resumeThread");
		resume();
	}

	public void stopThread()
	{
		LogProxy.v(TAG, "stopThread");
		try
		{
			setNeedLooping(false);
			resumeThread();//如果是暂停中，则释放暂停，结束线程
			if(null!=thread)
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
	{
		LogProxy.v(TAG, "onPause");
	}

	@Override
	public void onResume()
	{
		LogProxy.v(TAG, "onResume");
	}

	@Override
	public void beforeLoop()
	{
		LogProxy.v(TAG, "beforeLoop");
	}

	@Override
	public void afterLoop()
	{
		LogProxy.v(TAG, "afterLoop");
	}
}
