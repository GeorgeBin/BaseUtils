package com.georgebindragon.base.function.log;

import com.georgebindragon.base.function.FunctionProxy;
import com.georgebindragon.base.java.LogFunctionJavaImp;

/**
 * 创建人：George
 * 类名称：LogProxy
 * 类概述：Log的代理类
 * 详细描述：
 *
 * Log的一应方法，都从这里调用
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LogProxy
{
	private static final String TAG = "LogProxy-->";

	//内部方法, 用于获取具体实现, 可在此添加默认实现
	private static LogFunctionI getAdapter()
	{
		LogFunctionI function = FunctionProxy.getFunction(LogFunctionI.class);
		if (null == function) return LogFunctionJavaImp.getInstance();
		return function;
	}

	//设置服务的具体承载者
	public static void setLogImp(LogFunctionI logAdapter) { FunctionProxy.registerFunction(LogFunctionI.class, logAdapter); }

	//服务内方法: 设置log开关
	public static void setLogVisibility(boolean isLogEnable) { getAdapter().setLogVisibility(isLogEnable); }

	//服务内方法: 设置log可见度
	public static void setLogLevel(int level) { getAdapter().setLogLevel(level); }

	//服务内方法: .......
	public static void wtf(String TAG, String... msg) { getAdapter().wtf(TAG, msg); }

	public static void e(String TAG, String... msg) { getAdapter().e(TAG, msg); }

	public static void e(String TAG, String method, Exception e) { getAdapter().e(TAG, method, e); }

	public static void w(String TAG, String... msg) { getAdapter().w(TAG, msg); }

	public static void i(String TAG, String... msg) { getAdapter().i(TAG, msg); }

	public static void d(String TAG, String... msg) { getAdapter().d(TAG, msg); }

	public static void v(String TAG, String... msg) { getAdapter().v(TAG, msg); }

}
