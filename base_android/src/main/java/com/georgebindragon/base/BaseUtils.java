package com.georgebindragon.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.georgebindragon.base.android.log.LogFunctionAndroidImp;
import com.georgebindragon.base.function.log.LogProxy;
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
	}

	public static Context getContext()
	{
		return context;
	}
}
