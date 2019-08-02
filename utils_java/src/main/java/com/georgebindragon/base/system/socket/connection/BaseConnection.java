package com.georgebindragon.base.system.socket.connection;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.socket.base.callbacks.IConnectionReadCallBack;
import com.georgebindragon.base.system.socket.base.function.ISocketConnection;
import com.georgebindragon.base.system.socket.base.listener.ISocketListener;
import com.georgebindragon.base.system.socket.base.thread.BaseConnectionReadLoopThread;

public abstract class BaseConnection<T extends ISocketListener> implements ISocketConnection, IConnectionReadCallBack
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	protected void setBaseReadThread(BaseConnectionReadLoopThread baseReadThread)
	{
		if (null != baseReadThread) baseReadThread.setReadCallBack(this);
	}

	protected ConnectionConfig connectionConfig;
	protected long             socketMark = 0;

	public long getSocketMark() { return socketMark; }

	@Override
	public void init(ConnectionConfig config)
	{
		connectionConfig = config;
		id = getDefaultID();
		receiveCounter = 0;
		writeCounter = 0;
		unResponsePackageCounter = 0;
	}

	@Override
	public void destroy()
	{
		connectionConfig = null;
		receiveCounter = 0;
		writeCounter = 0;
		unResponsePackageCounter = 0;
	}

	@Override
	public void connect()
	{
		LogProxy.i(TAG, "connect()");
		if (null == connectionConfig) throw new NullPointerException("connectionConfig is null, init first! ");
		receiveCounter = 0;
		writeCounter = 0;
		unResponsePackageCounter = 0;
	}

	@Override
	public void disconnect()
	{
		LogProxy.i(TAG, "disconnect");
		if (null != socketListener) socketListener.connectInfo(getId(), socketMark, false, ConnectionStatus.DIS_CONNECT);
		receiveCounter = 0;
		writeCounter = 0;
		unResponsePackageCounter = 0;
	}

	protected ConnectionStatus socketStatus;

	public ConnectionStatus getSocketStatus()
	{
		return socketStatus;
	}

	public void setSocketStatus(ConnectionStatus socketStatus)
	{
		LogProxy.i(TAG, "setSocketStatus socketStatus=" + socketStatus.toString());
		this.socketStatus = socketStatus;

		switch (socketStatus)
		{
			case CONNECTED:
				break;
			case CONNECTTING:
			case DIS_CONNECT:
				break;
			default:
				break;
		}
	}

	public void setConnectionOn(boolean isOn, long mark)
	{
		LogProxy.i(TAG, "setConnectionOn isOn=" + isOn, "socketMark=" + socketMark, "mark=" + mark);
		if (mark < socketMark)
		{
			//旧的socket传来的消息，忽略
			LogProxy.d(TAG, "setConnectionOn-->旧的socket传来的消息，忽略");
		} else//当前socket传来的消息，认真变化
		{
			if (isOn)
			{
				if (null != socketListener) socketListener.connectInfo(getId(), socketMark, true, ConnectionStatus.CONNECTED);
			} else
			{
				if (null != socketListener) socketListener.connectInfo(getId(), socketMark, false, ConnectionStatus.DIS_CONNECT);
			}
		}
	}

	//---------------------------------------------------------数据---------------------------------------------------------
	private String id = getDefaultID();

	public void setID(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	protected T socketListener;

	public void setListener(T listener)
	{
		this.socketListener = listener;
	}

	protected String getDefaultID()
	{
		//		return "SocketID@" + Integer.toHexString(this.hashCode()) + "@" + (null == connectionConfig ? "ConnectionConfig=null" : connectionConfig.toString());
		return (null == connectionConfig ? "null" : (null == connectionConfig.getIP() ? "null" : connectionConfig.getIP()));
	}

	protected volatile int  writeCounter             = 0;
	protected volatile int  receiveCounter           = 0;
	protected volatile long lastDataTime             = 0;
	protected volatile int  unResponsePackageCounter = 0;

	public long getLastDataTime()
	{
		return lastDataTime;
	}

	public int getUnResponsePackageCounter()
	{
		return unResponsePackageCounter;
	}
	//---------------------------------------------------------内部实现---------------------------------------------------------

	@Override
	public void write(byte[] dataBytes)
	{
		writeCounter = writeCounter + 1;
		unResponsePackageCounter = unResponsePackageCounter + 1;
	}

	@Override
	public void onDataReceive(byte[] dataBytes)
	{
		if (null != socketListener) socketListener.onDataReceive(getId(), socketMark, dataBytes);
		receiveCounter = receiveCounter + 1;
		lastDataTime = SocketUtilProxy.getTimeStamp();
		unResponsePackageCounter = 0;//只要收到了, 就认为包已经回复了, 具体包没有收到的问题, 由上层来负责重发
	}

	@Override
	public void onSoTimeOut(long socketMark)
	{
		if (null != socketListener) socketListener.onSoTimeOut(getId(), socketMark);
	}

	@Override
	public void onErr(long socketMark)
	{
		setSocketStatus(ConnectionStatus.DIS_CONNECT);
		if (null != socketListener) socketListener.connectInfo(getId(), socketMark, false, ConnectionStatus.DIS_CONNECT);
	}
}
