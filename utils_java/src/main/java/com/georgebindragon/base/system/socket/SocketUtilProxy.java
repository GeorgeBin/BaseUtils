package com.georgebindragon.base.system.socket;

import com.georgebindragon.base.function.FunctionProxy;

/**
 * 创建人：George
 * 类名称：SocketUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public final class SocketUtilProxy
{
	private static final String TAG = "SocketUtilProxy-->";

	private static SocketUtilInterface function;
	private static boolean             isInit = false;

	private static void initImp()
	{
		function = FunctionProxy.getFunction(SocketUtilInterface.class);
		if (null == function) function = SocketUtil_Java.getInstance();
		isInit = true;
	}

	//内部方法, 用于获取具体实现, 可在此添加默认实现
	private static SocketUtilInterface getImp()
	{
		if (!isInit) initImp();
		return function;
	}

	//设置服务的具体承载者
	public static void setImp(SocketUtilInterface imp)
	{
		isInit = false;
		FunctionProxy.registerFunction(SocketUtilInterface.class, imp);
		initImp();
	}

	public static long getTimeStamp()
	{
		return getImp().getTimeStamp();
	}

	public static void keepCPUAwake()
	{
		getImp().keepCPUAwake();
	}
}
