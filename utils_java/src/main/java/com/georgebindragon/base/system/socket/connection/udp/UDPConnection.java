package com.georgebindragon.base.system.socket.connection.udp;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.socket.base.listener.IUDPListener;
import com.georgebindragon.base.system.socket.connection.BaseConnection;
import com.georgebindragon.base.system.socket.connection.ConnectionStatus;

import java.net.DatagramSocket;


public class UDPConnection extends BaseConnection<IUDPListener>
{
	private DatagramSocket datagramSocket;

	private UDPReadRunnable  udpReadRunnable;
	private UDPWriteRunnable udpWriteRunnable;

	public UDPConnection()
	{
		udpReadRunnable = new UDPReadRunnable();
		udpWriteRunnable = new UDPWriteRunnable();

		setBaseReadThread(udpReadRunnable);
	}

	@Override
	public void connect()
	{
		super.connect();
		try
		{
			if (null != datagramSocket)
			{
				LogProxy.i(TAG, "checkSocket()-->关闭旧连接");
				datagramSocket.close();//关闭之前的连接
			}
			LogProxy.i(TAG, "checkSocket()-->建立新连接");
			datagramSocket = new DatagramSocket();
			socketMark = SocketUtilProxy.getTimeStamp();

			String ip   = connectionConfig.getIP();
			int    port = connectionConfig.getPort();

			int soTimeOut = connectionConfig.getSoTimeOut();//读写的阻塞超时,毫秒
			if (soTimeOut > 0) datagramSocket.setSoTimeout(soTimeOut);//读写的阻塞超时

			udpReadRunnable.init(datagramSocket, ip, socketMark);
			udpWriteRunnable.init(datagramSocket, ip, port);
			setSocketStatus(ConnectionStatus.CONNECTED);
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "connect", e);
			setSocketStatus(ConnectionStatus.DIS_CONNECT);
		}
	}

	@Override
	public void disconnect()
	{
		super.disconnect();
		if (null != datagramSocket)
		{
			datagramSocket.close();
			datagramSocket = null;
			udpReadRunnable.stopThread();
			udpWriteRunnable.stopThread();
			setSocketStatus(ConnectionStatus.DIS_CONNECT);
		}
	}

	@Override
	public void write(byte[] udpBytes)
	{
		super.write(udpBytes);
		if (socketStatus == ConnectionStatus.CONNECTED)
		{
			udpWriteRunnable.addDataAndWrite(udpBytes);
			lastDataTime = SocketUtilProxy.getTimeStamp();
			LogProxy.i(TAG, "write");
		}
	}
}
