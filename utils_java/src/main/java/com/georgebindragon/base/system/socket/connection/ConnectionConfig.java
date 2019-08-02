package com.georgebindragon.base.system.socket.connection;

/**
 * 创建人：George
 * 类名称：ConnectionConfig
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ConnectionConfig
{
	private String IP;
	private int    port;
	private int    connectTimeOut;
	private int    soTimeOut;

	public ConnectionConfig(String IP, int port, int connectTimeOut, int soTimeOut)
	{
		this.IP = IP;
		this.port = port;
		this.connectTimeOut = connectTimeOut;
		this.soTimeOut = soTimeOut;
	}

	public String getIP()
	{
		return IP;
	}

	public void setIP(String IP)
	{
		this.IP = IP;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public int getConnectTimeOut()
	{
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut)
	{
		this.connectTimeOut = connectTimeOut;
	}

	public int getSoTimeOut()
	{
		return soTimeOut;
	}

	public void setSoTimeOut(int soTimeOut)
	{
		this.soTimeOut = soTimeOut;
	}

	@Override
	public String toString()
	{
		return "ConnectionConfig={" +
				"IP='" + IP + '\'' +
				", port=" + port +
				", connectTimeOut=" + connectTimeOut +
				", soTimeOut=" + soTimeOut +
				'}';
	}
}
