package com.georgebindragon.base.system.socket.connection.tcp;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.socket.base.listener.ITCPListener;
import com.georgebindragon.base.system.socket.connection.BaseConnection;
import com.georgebindragon.base.system.socket.connection.ConnectionStatus;
import com.georgebindragon.base.system.socket.connection.TCPConnectError;
import com.georgebindragon.base.thread.thread.BaseThread;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class TCPConnection extends BaseConnection<ITCPListener>
{
	private TCPWriteRunnable writeRunnable;
	private TCPReadRunnable  readRunnable;
	//2
	private BaseThread       connectThread;

	public TCPConnection()
	{
		writeRunnable = new TCPWriteRunnable();
		readRunnable = new TCPReadRunnable();
		setBaseReadThread(readRunnable);

		connectThread = new BaseThread()
		{
			@Override
			public void onRun()
			{
				connectSocket();
			}
		};
	}

	private          Socket  mSocket      = null;
	private volatile boolean isConnecting = false;

	public Socket getSocket()
	{
		return mSocket;
	}

	public boolean isConnecting()
	{
		return isConnecting;
	}


	@Override
	public void connect()
	{
		super.connect();
		if (isConnecting) return;
		isConnecting = true;
		connectThread.startThread();
	}

	@Override
	public void disconnect()
	{
		super.disconnect();
		try
		{
			if (null != mSocket)
			{
				if (!mSocket.isClosed()) mSocket.close();
				if (!mSocket.isInputShutdown()) mSocket.shutdownInput();
				if (!mSocket.isOutputShutdown()) mSocket.shutdownOutput();

				writeRunnable.stopThread();
				readRunnable.stopThread();

				setConnectionOn(false, SocketUtilProxy.getTimeStamp());
				LogProxy.i(TAG, "disConnect(),完全关闭连接");
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "disConnect:错误=" + e.getMessage());
		}
	}


	@Override
	public void write(byte[] tcpBytes)
	{
		super.write(tcpBytes);
		LogProxy.i(TAG, "tcpWrite ");
		if (socketStatus == ConnectionStatus.CONNECTED)
		{
			if (mSocket != null && mSocket.isConnected())
			{
				writeRunnable.addDataAndWrite(tcpBytes);
				LogProxy.i(TAG, "tcpWrite success");
			} else
			{
				LogProxy.i(TAG, "tcpWrite failed 1");
			}
		} else
		{
			LogProxy.i(TAG, "tcpWrite failed 2");
		}
	}

	private void connectSocket()
	{
		LogProxy.i(TAG, "connectSocket()");
		if (EmptyUtil.notEmpty(connectionConfig))
		{
			setSocketStatus(ConnectionStatus.DIS_CONNECT);
			try
			{
				if (null != mSocket)
				{
					LogProxy.i(TAG, "connect()-->关闭旧连接");
					mSocket.close();//关闭之前的连接
					if (null != writeRunnable) writeRunnable.stopThread();
					if (null != readRunnable) readRunnable.stopThread();
				}

				LogProxy.i(TAG, "connect()-->建立新连接");
				mSocket = new Socket();
				socketMark = SocketUtilProxy.getTimeStamp();

				String            ip                = connectionConfig.getIP();
				int               port              = connectionConfig.getPort();
				InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);

				int connectTimeOut = connectionConfig.getConnectTimeOut();//连接过程超时时长设置,毫秒
				if (connectTimeOut > 0)
				{
					mSocket.connect(inetSocketAddress, connectTimeOut);
				} else
				{
					mSocket.connect(inetSocketAddress);
				}
				mSocket.setTcpNoDelay(true);//关闭 Nagle 算法,无论TCP数据报大小,立即发送
				LogProxy.i(TAG, "connect()-->已经连接上");

				if (mSocket.isConnected())
				{
					setSocketStatus(ConnectionStatus.CONNECTED);
					try
					{
						int soTimeOut = connectionConfig.getSoTimeOut();//读写的阻塞超时,毫秒
						if (soTimeOut > 0) mSocket.setSoTimeout(soTimeOut);

						OutputStream outputStream = mSocket.getOutputStream();
						InputStream  inputStream  = mSocket.getInputStream();
						writeRunnable.init(outputStream, socketMark);
						readRunnable.init(inputStream, socketMark);
						setConnectionOn(true, socketMark);
						isConnecting = false;
					} catch (IOException e)
					{
						e.printStackTrace();
						LogProxy.e(TAG, "Exception=" + e.getMessage());
						setSocketStatus(ConnectionStatus.DIS_CONNECT);
					}
				} else if (mSocket.isClosed())
				{
					setSocketStatus(ConnectionStatus.DIS_CONNECT);
				}
			} catch (SocketTimeoutException e)
			{
				LogProxy.e(TAG, "ConnectTimeOut=" + e.getMessage());

				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.TimeOut);
				setSocketStatus(ConnectionStatus.DIS_CONNECT);

			} catch (UnsupportedOperationException e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 UnsupportedOperationException=", e);

				setSocketStatus(ConnectionStatus.DIS_CONNECT);

			} catch (ConnectException e)
			{
				//不正确的端口     -->java.net.ConnectException: Connection refused (Connection refused)
				//客户端没有网络   -->java.net.ConnectException: Network is unreachable (connect failed)

				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 ConnectException=", e);

				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.NetworkIsUnreachable);
				setSocketStatus(ConnectionStatus.DIS_CONNECT);

			} catch (UnknownHostException e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 UnknownHostException=", e);

				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.UnknownHost);
				setSocketStatus(ConnectionStatus.DIS_CONNECT);

			} catch (SocketException e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 SocketException=", e);

				setSocketStatus(ConnectionStatus.DIS_CONNECT);
				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.Error);
			} catch (IOException e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 IOException=", e);

				setSocketStatus(ConnectionStatus.DIS_CONNECT);
				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.Error);
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "connect()-->连接错误 Exception=", e);

				setSocketStatus(ConnectionStatus.DIS_CONNECT);
				if (null != socketListener) socketListener.onConnectError(getId(), socketMark, TCPConnectError.Error);
			}
		}
		isConnecting = false;
	}


	@Override
	public void setConnectionOn(boolean tcpOn, long mark)
	{
		super.setConnectionOn(tcpOn, mark);
		if (mark >= socketMark)
		{
			//当前socket传来的消息，认真变化
			if (!tcpOn)
			{
				mSocket = null;
			}
		}
	}
}
