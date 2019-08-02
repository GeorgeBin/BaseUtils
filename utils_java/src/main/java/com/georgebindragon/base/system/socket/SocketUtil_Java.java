package com.georgebindragon.base.system.socket;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.TimeUtil_Java;

/**
 * 创建人：George
 * 类名称：SocketUtilInterface
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class SocketUtil_Java implements SocketUtilInterface
{
	private static final String TAG = "SocketUtil_Java-->";

	private static SocketUtil_Java instance = new SocketUtil_Java();

	static SocketUtil_Java getInstance() { return instance; }

	private SocketUtil_Java() { }

	@Override
	public long getTimeStamp()
	{
		return TimeUtil_Java.getMilliSeconds();
	}

	@Override
	public void keepCPUAwake()
	{
		LogProxy.i(TAG, "keepCPUAwake-->");
	}

}
