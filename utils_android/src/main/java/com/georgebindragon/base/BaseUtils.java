package com.georgebindragon.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.android.log.LogFunctionAndroidImp;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.UtilsActions;
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
 *  Proxy: 代理, 能切换具体实现类 或者 能进行解耦的代理
 *
 *  Function: 功能, 库本身封装的功能
 *  Executive: 管理者, 一般是调用系统的功能
 *  Monitor: 监听器, 用于Listener 和 CallBack的管理者
 *
 *  Listener: 可注册多个
 *  CallBack: 只可以注册一个, 或回调一次
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("StaticFieldLeak")
public class BaseUtils
{
	private static Context context;

	public static void init(Context context2)
	{
		context = context2;

		LogProxy.setLogImp(LogFunctionAndroidImp.getInstance());//设置log的具体实现服务

		HandlerUtil.init();

		//语言 & 资源 的管理
		LanguageUtil.init(context2);
	}

	public static Context getContext()
	{
		return context;
	}
}
