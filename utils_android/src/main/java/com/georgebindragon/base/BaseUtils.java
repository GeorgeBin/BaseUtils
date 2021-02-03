package com.georgebindragon.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.georgebindragon.base.android.log.LogFunctionAndroidImp;
import com.georgebindragon.base.android.socket.SocketUtil_Android;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.socket.SocketUtilProxy;
import com.georgebindragon.base.system.software.LanguageUtil;
import com.georgebindragon.base.thread.HandlerUtil;

/**
 * 创建人：George
 * 类名称：BaseUtils
 * 类概述：BaseUtils 的初始化
 * 详细描述：
 *
 * 要求:
 * 1. 主线程初始化
 *
 * 定义:
 *
 * Proxy: 代理, 能切换具体实现类 或者 能进行解耦的代理
 *
 * Function: 功能, 库本身封装的功能
 * Executive: 管理者, 一般是调用系统的功能
 * Monitor: 监听器, 用于Listener 和 CallBack的管理者
 *
 * Listener: 可注册多个
 * CallBack: 只可以注册一个, 或回调一次
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("StaticFieldLeak")
public class BaseUtils
{
	private static final String TAG = "BaseUtils-->";

	private static Context context;

	public static void init(Context context2)
	{
		context = context2;

		LogProxy.setLogImp(LogFunctionAndroidImp.getInstance());//设置log的具体实现服务
		SocketUtilProxy.setImp(SocketUtil_Android.getInstance());//设置socket工具类的具体实现

		HandlerUtil.init();

		//语言 & 资源 的管理
		LanguageUtil.init(context2);
	}

	public static Context getContext()
	{
		if (null != context) return context;

		Application applicationByReflect = getApplicationByReflect();
		if (null != applicationByReflect)
		{
			init(applicationByReflect);
		} else
		{
			LogProxy.e(TAG, "getContext", "请调用init手动初始化");
		}
		return context;
	}

	public static Application getApplication()
	{
		if (context instanceof Application) return (Application) context;
		return getApplicationByReflect();
	}

	@SuppressLint("PrivateApi")
	private static Application getApplicationByReflect()
	{
		try
		{
			Class<?> activityThread = Class.forName("android.app.ActivityThread");
			Object   thread         = activityThread.getMethod("currentActivityThread").invoke(null);
			Object   app            = activityThread.getMethod("getApplication").invoke(thread);

			return (Application) app;
		} catch (Exception e) { LogProxy.e(TAG, "getApplicationByReflect", e); }
		return null;
	}

}
