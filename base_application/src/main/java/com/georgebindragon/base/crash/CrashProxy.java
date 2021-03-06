package com.georgebindragon.base.crash;

import android.content.Context;

import com.georgebindragon.base.function.FunctionProxy;

/**
 * 创建人：George
 * 类名称：ICrash
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public final class CrashProxy
{
	private static final String TAG = "CrashProxy-->";

	private static ICrashHandler function;
	private static boolean       isInit = false;

	private static void initImp()
	{
		function = FunctionProxy.getFunction(ICrashHandler.class);
		// if (null == function) function = ReleaseCrashHandler.getInstance();
		isInit = true;
	}

	//内部方法, 用于获取具体实现, 可在此添加默认实现
	private static ICrashHandler getImp()
	{
		if (!isInit) initImp();
		return function;
	}

	//设置服务的具体承载者
	public static void setImp(ICrashHandler imp)
	{
		isInit = false;
		FunctionProxy.registerFunction(ICrashHandler.class, imp);
		initImp();
	}

	public static void init(Context context)
	{
		if (null != getImp()) getImp().init(context);
	}

	public static void setCallBack(CrashCallBack crashCallBack)
	{
		if (null != getImp()) getImp().setCallBack(crashCallBack);
	}
}
