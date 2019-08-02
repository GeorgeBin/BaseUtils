package com.georgebindragon.base.system.socket.base.thread;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.base.callbacks.IConnectionReadCallBack;
import com.georgebindragon.base.thread.thread.BaseLoopAndWaitThread;


/**
 * 创建人：George
 * 类概述：
 *
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseConnectionReadLoopThread extends BaseLoopAndWaitThread
{

	protected IConnectionReadCallBack callBack;

	public BaseConnectionReadLoopThread()
	{
		isRunning = false;
		isPausing = false;
		setNeedLooping(true);
	}

	//---------------------------------------------------------内部操作---------------------------------------------------------
	@Override
	public void beforeLoop()
	{
		super.beforeLoop();
		LogProxy.i(TAG, "run-->开始 threadId=" + Thread.currentThread().toString());
	}

	@Override
	public void afterLoop()
	{
		super.afterLoop();
		LogProxy.i(TAG, "run-->结束 threadId=" + Thread.currentThread().toString());
	}

	protected void onDataReceive(byte[] dataBytes)
	{
		if (null != callBack) callBack.onDataReceive(dataBytes);
	}

	protected void onSoTimeOut(long socketMark)
	{
		if (null != callBack) callBack.onSoTimeOut(socketMark);
	}

	protected void onErr(long socketMark)
	{
		if (null != callBack) callBack.onErr(socketMark);
	}

	public void setReadCallBack(IConnectionReadCallBack readCallBack)
	{
		this.callBack = readCallBack;
	}
}
