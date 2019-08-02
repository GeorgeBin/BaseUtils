package com.georgebindragon.base.system.socket.connection;

import com.georgebindragon.base.function.log.LogProxy;

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


public enum TCPConnectError
{
	UNKNOWN,
	TimeOut,
	NetworkIsUnreachable,
	UnknownHost,
	Error;

	private static final String TAG = "TCPConnectError-->";

	public static TCPConnectError valueOf(int ordinal)
	{
		if (ordinal < 0 || ordinal >= values().length)
		{
			LogProxy.e(TAG, "Invalid ordinal");
			return UNKNOWN;
		}
		return values()[ordinal];
	}
}
