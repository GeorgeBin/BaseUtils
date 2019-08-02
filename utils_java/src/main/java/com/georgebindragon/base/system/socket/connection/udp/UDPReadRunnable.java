package com.georgebindragon.base.system.socket.connection.udp;


import com.georgebindragon.base.data.basic.BytesUtil;
import com.georgebindragon.base.data.format.FormatTimeUtil;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.socket.base.thread.BaseConnectionReadLoopThread;
import com.georgebindragon.base.system.socket.connection.ConnectionConstant;
import com.georgebindragon.base.utils.TimeUtil_Java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class UDPReadRunnable extends BaseConnectionReadLoopThread
{
	private static final String TAG = "UDPReadRunnable-->";

	private DatagramSocket datagramSocket;
	private String         ip;
	private long           socketMark;

	public void init(DatagramSocket datagramSocket, String ip, long socketMark)
	{
		this.datagramSocket = datagramSocket;
		this.ip = ip;
		this.socketMark = socketMark;
		startThread();
	}

	@Override
	public void onLoop()
	{
		byte[]         dataBytes      = new byte[ConnectionConstant.UDP_ReadLength];
		DatagramPacket datagramPacket = new DatagramPacket(dataBytes, dataBytes.length);
		if (null != datagramSocket)
		{
			try
			{
				LogProxy.i(TAG, "准备接收数据 开始阻塞=" + FormatTimeUtil.formatTimeToFullString(TimeUtil_Java.getMilliSeconds()));
				datagramSocket.receive(datagramPacket);//程序阻塞在这里，所以查询是运行状态，又不能结束，所以就没有了接收
				LogProxy.i(TAG, "准备接收数据 阻塞结束=" + FormatTimeUtil.formatTimeToFullString(TimeUtil_Java.getMilliSeconds()));
				SocketUtilProxy.keepCPUAwake();
				//校验数据
				if (null != datagramPacket.getAddress() && datagramPacket.getAddress().getHostAddress().equalsIgnoreCase(ip))
				{
					int length = datagramPacket.getLength();
					if (length > 0)
					{
						byte[] data     = datagramPacket.getData();
						byte[] subBytes = BytesUtil.subBytes(data, 0, length);
						onDataReceive(subBytes);//回调给自己的上级
						LogProxy.i(TAG, "ReadThread-->接收到的消息，长度有效，length=" + length);
					} else
					{
						LogProxy.i(TAG , "接收数据 长度小于等于零!");
					}
				}
			} catch (SocketTimeoutException e)
			{
				LogProxy.e(TAG, "datagramSocket.receive SoTimeOut" , e);
				onSoTimeOut(socketMark);
			} catch (IOException e)
			{
				LogProxy.e(TAG , "datagramSocket.receive" , e);
				onErr(socketMark);
				pause();//读取出错，则暂停读取线程
			}
		} else
		{
			LogProxy.i(TAG , "datagramSocket==null，结束线程");
		}
		//阻塞线程，会根据实际收包情况来进行
	}
}
