package com.georgebindragon.base.system.socket.connection.udp;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.base.thread.BaseConnectionWriteLoopThread;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPWriteRunnable extends BaseConnectionWriteLoopThread
{
	private static final String TAG = "UDPWriteRunnable-->";

	private DatagramSocket datagramSocket;
	private String         ip;
	private int            port;
	private InetAddress    inetAddress;

	//---------------------------------------------------------外部接口操作---------------------------------------------------------

	public void init(DatagramSocket datagramSocket, String ip, int port)
	{
		this.datagramSocket = datagramSocket;
		this.ip = ip;
		this.port = port;
		try
		{
			inetAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------内部操作---------------------------------------------------------

	@Override
	public void writeDataOnOS(byte[] sendBytes)
	{
		if (null != sendBytes && EmptyUtil.notEmpty(ip) && port != 0)
		{
			try
			{
				if (null == inetAddress) inetAddress = InetAddress.getByName(ip);
				//2、创建数据报，包含发送的信息
				DatagramPacket datagramPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
				//4、向服务器端发送数据报
				if (null != datagramSocket) datagramSocket.send(datagramPacket);
			} catch (IOException e)
			{
				e.printStackTrace();
				LogProxy.i(TAG, "sendPackage()-->Exception=" + e.getMessage());
			}
		}
	}
}
