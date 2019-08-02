package com.georgebindragon.base.system.socket.connection.tcp;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.base.thread.BaseConnectionWriteLoopThread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

public class TCPWriteRunnable extends BaseConnectionWriteLoopThread
{
	private static final String TAG = "TCPWriteRunnable-->";

	private OutputStream outputStream;
	private long         socketMark;

	//---------------------------------------------------------外部接口操作---------------------------------------------------------

	public void init(OutputStream os, long socketMark)
	{
		LogProxy.i(TAG, "init socketMark=" + socketMark);
		this.outputStream = os;
		this.socketMark = socketMark;
	}

	//---------------------------------------------------------内部操作---------------------------------------------------------

	@Override
	public void writeDataOnOS(byte[] sendBytes)
	{
		LogProxy.i(TAG, "writeDataOnOS outputStream=" + (null == outputStream ? "NULL" : "not null"));

		if (null != sendBytes && null != outputStream)
		{
			try
			{
				outputStream.write(sendBytes, 0, sendBytes.length);//非阻塞
				outputStream.flush();
			}catch (SocketException e)
			{
				LogProxy.e(TAG, "write e=" + e.getMessage());
			}catch (IOException e)
			{
				LogProxy.e(TAG, "write e=" + e.getMessage());
			}
		}
	}

	@Override
	public void afterLoop()
	{
		try
		{
			clearData();
			if (null != outputStream) outputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
