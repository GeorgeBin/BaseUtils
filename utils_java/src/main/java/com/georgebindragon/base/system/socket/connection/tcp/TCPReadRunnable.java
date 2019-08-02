package com.georgebindragon.base.system.socket.connection.tcp;


import com.georgebindragon.base.data.basic.BytesUtil;
import com.georgebindragon.base.data.format.FormatTimeUtil;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.socket.base.thread.BaseConnectionReadLoopThread;
import com.georgebindragon.base.system.socket.connection.ConnectionConstant;
import com.georgebindragon.base.utils.TimeUtil_Java;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

public class TCPReadRunnable extends BaseConnectionReadLoopThread
{
	private InputStream inputStream;
	private long        socketMark;

	//---------------------------------------------------------外部接口操作---------------------------------------------------------

	public void init(InputStream inputStream2, long socketMark)
	{
		LogProxy.i(TAG, "init socketMark=" + socketMark);

		if (null != inputStream)
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "init e=" + e.getMessage());
			}
		}
		inputStream = inputStream2;
		this.socketMark = socketMark;
		startThread();
	}

	//---------------------------------------------------------内部操作---------------------------------------------------------

	@Override
	public void onLoop()
	{
		try
		{
			byte[] bufArray = new byte[ConnectionConstant.TCP_ReadLength];

			int length = readDataFromOS(bufArray);
			LogProxy.i(TAG, "readDataFromOS length=" + length);
			if (length <= 0)
			{
				if (length == ConnectionConstant.ReadTimeOut_ID)
				{
					LogProxy.i(TAG, "连接读取超时, 暂时不断开");
					onSoTimeOut(socketMark);
				} else
				{
					LogProxy.i(TAG, "与服务器链接断开！");
					onErr(socketMark);
					Thread.sleep(50);
					if (isNeedLooping()) pause();//断开连接，则暂停线程
				}
			} else
			{
				LogProxy.i(TAG, "ReadThread-->接收到的消息，长度有效，length=" + length);
				byte[] bytes = BytesUtil.subBytes(bufArray, 0, length);
				onDataReceive(bytes);
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "read e=" + e.getMessage());
			onErr(socketMark);
			if (isNeedLooping()) pause();//读取错误，则暂停线程
		}
		LogProxy.i(TAG, "TCPConnection 读取数据线程 ReadRunnable 运行一个循环：" + FormatTimeUtil.formatTimeToFullString(TimeUtil_Java.getMilliSeconds()));
	}

	private int readDataFromOS(byte[] dataBytes)
	{
		LogProxy.i(TAG, "readDataFromOS inputStream=" + (null == inputStream ? "NULL" : "not null"));
		if (null != inputStream)
		{
			try
			{
				LogProxy.i(TAG, "准备接收数据 开始阻塞=" + FormatTimeUtil.formatTimeToFullString(TimeUtil_Java.getMilliSeconds()));
				int len = inputStream.read(dataBytes);
				LogProxy.i(TAG, "准备接收数据 阻塞结束=" + FormatTimeUtil.formatTimeToFullString(TimeUtil_Java.getMilliSeconds()));
				SocketUtilProxy.keepCPUAwake();
				return len;
			} catch (SocketTimeoutException e)
			{
				LogProxy.e(TAG, "SoTimeOut=" + e.getMessage());
				return ConnectionConstant.ReadTimeOut_ID;
			} catch (Exception e)
			{
				onErr(socketMark);
				LogProxy.e(TAG, "readDataFromOS e=" + e.getMessage());
				return 0;
			}
		}
		return 0;
	}

	@Override
	public void afterLoop()
	{
		LogProxy.i(TAG, "run-->结束 threadId=" + Thread.currentThread().toString());
		try
		{
			inputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "afterLoop,inputStream.close()  e=", e);
		}
	}

}
