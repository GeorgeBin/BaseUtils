package com.georgebindragon.base.system.socket.connection;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 2017/3/24
 */

public enum ConnectionStatus
{
	UNKNOWN,
	NETWORK_UNKNOWN,
	NETWORK_UNAVAILABLE,

	GATEWAY_FAILED,
	SERVER_FAILED,//连接服务器失败
	SERVER_DOWN,//服务器不能提供服务了

	CLINT_CUT,//客户端主动断开

	DIS_CONNECT,//断开
	CONNECTTING,//连接中
	CONNECTED;//已连接

	private static final String TAG = "RongChatSocketProxy-->";

	public static ConnectionStatus valueOf(int ordinal)
	{
		if (ordinal < 0 || ordinal >= values().length)
		{
			LogProxy.e(TAG, "Invalid ordinal");
			return UNKNOWN;
		}
		return values()[ordinal];
	}

}
