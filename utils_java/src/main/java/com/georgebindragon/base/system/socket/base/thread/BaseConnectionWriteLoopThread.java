package com.georgebindragon.base.system.socket.base.thread;

import com.georgebindragon.base.thread.thread.BaseLoopAndWaitAndProcessThread;

/**
 * 创建人：George
 * 类名称：BaseConnectionWriteLoopThread
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseConnectionWriteLoopThread extends BaseLoopAndWaitAndProcessThread<byte[]>
{
	public BaseConnectionWriteLoopThread()
	{
		super();
		setCount(25,20);// 25*20=500
	}

	@Override
	public void dataProcess(byte[] data)
	{
		writeDataOnOS(data);
	}

	public void addDataAndWrite(byte[] data)
	{
		addDataAndProcess(data);
	}

	public abstract void writeDataOnOS(byte[] sendBytes);
}
