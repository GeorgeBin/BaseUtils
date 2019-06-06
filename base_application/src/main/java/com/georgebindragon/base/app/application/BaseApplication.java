package com.georgebindragon.base.app.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.app.lifecycle.AppLifeCycleProxy;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.AppUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Locale;

import androidx.multidex.MultiDex;

/**
 * 创建人：George
 * 类名称：BaseApplication
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseApplication extends Application
{
	protected String TAG = "Application: " + getClass().getSimpleName() + "-->";

	@SuppressLint("StaticFieldLeak")
	private static Context context;

	public static Context getContext() { return context; }

	@Override
	protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
		MultiDex.install(this);
		Log.d(TAG,"attachBaseContext");
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		context = getApplicationContext();
		Log.d(TAG,"onCreate");

		initBase();
		checkMainProcess(this);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void onTerminate()
	{
		AppLifeCycleProxy.onTerminate();
		super.onTerminate();
	}

	private void checkMainProcess(Application application)
	{
		String packageName = AppUtil.getPackageName(this);
		String processName = AppUtil.getProcessName(Process.myPid());

		LogProxy.d(TAG, "packageName=" + packageName, "processName=" + processName);

		if (EmptyUtil.notEmpty(packageName, processName))
		{
			if (packageName.equals(processName))//确认包名和进程名相同
			{
				DoraemonKit.install(application);
				initInMainProcess(application);
			}
		}
	}

	protected void initBase()
	{
		//工具类 初始化
		BaseUtils.init(this);

		LogProxy.v(TAG, "打印一条log测试: 1");
		LogProxy.setLogVisibility(isLogEnable());
		LogProxy.v(TAG, "打印一条log测试: 2");

		Locale aDefault = Locale.getDefault();
		LogProxy.v(TAG, "当前应用内语言: "+aDefault.toString());
	}

	protected abstract boolean isLogEnable();//是否在控制栏打印log

	protected abstract void initInMainProcess(Application application);
}
