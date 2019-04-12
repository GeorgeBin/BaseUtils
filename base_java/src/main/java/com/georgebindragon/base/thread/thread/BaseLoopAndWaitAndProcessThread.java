package com.georgebindragon.base.thread.thread;

import com.georgebindragon.base.abilities.function.IDataProcess;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 创建人：George
 * 类名称：BaseLoopAndWaitAndProcessThread
 * 类概述：
 * 创建时间：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseLoopAndWaitAndProcessThread<T> extends BaseLoopAndWaitThread implements IDataProcess<T>
{
	private static final Object lock = new Object();


	protected Queue<T> dataList = new ConcurrentLinkedQueue<>();

	public BaseLoopAndWaitAndProcessThread()
	{
		needLooping = false;
		isRunning = false;
	}
	//---------------------------------------------------------外部api---------------------------------------------------------

	public void addDataAndProcess(T data)
	{
		needLooping = true;
		addData(data);
		startThread();
	}

	public void addData(T data)
	{
		if (null != data) dataList.offer(data);
	}

	public T pollData()
	{
		synchronized (lock)//使用同步
		{
			T pollData = null;
			if (!dataList.isEmpty())
			{
				pollData = dataList.poll();
				resetPauseCount();
			}
			return pollData;
		}
	}

	public void clearData()
	{
		if (null != dataList) dataList = new ConcurrentLinkedQueue<>();
	}

	//---------------------------------------------------------内部操作---------------------------------------------------------

	@Override
	public void onLoop()
	{
		T data = pollData();

		if (data != null)
		{
			dataProcess(data);
		} else
		{
			pauseOnCount();
		}
	}

	@Override
	public void stopThread()
	{
		super.stopThread();
		clearData();
	}
}
