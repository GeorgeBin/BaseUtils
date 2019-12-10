package com.georgebindragon.base.app.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.app.lifecycle.AppLifeCycleProxy;
import com.georgebindragon.base.app.lifecycle.IAppLifeCycle;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.system.software.AppUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

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
	protected String TAG = "BaseApplication: " + getClass().getSimpleName() + "-->";

	@SuppressLint("StaticFieldLeak")
	private static Context context;

	public static Context getContext() { return context; }

	@Override
	protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
		MultiDex.install(this);
		Log.d(TAG, "attachBaseContext");
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		context = getApplicationContext();
		Log.d(TAG, "onCreate");

		initBase(this);

		initInMultiProcess(this);

		if (isMainProcess())
		{
			LogProxy.i(TAG, "主进程中初始化");
			initInMainProcess(this);
			AppLifeCycleProxy.onAppStart();
		}
	}

	@Override
	public void onTerminate()
	{
		AppLifeCycleProxy.onTerminate();
		super.onTerminate();
	}

	protected boolean isMainProcess()
	{
		String packageName = AppUtil.getPackageName(this);
		String processName = AppUtil.getProcessName(Process.myPid());

		LogProxy.d(TAG, "packageName=" + packageName, "processName=" + processName);

		if (EmptyUtil.notEmpty(packageName))
		{
			if (packageName.equalsIgnoreCase(processName))//确认包名和进程名相同
			{
				LogProxy.i(TAG, "主进程");
				return true;
			}
		}
		return false;
	}

	protected void initBase(Application application)
	{
		//工具类 初始化
		BaseUtils.init(application);

		//注册开机广播和关机广播监听-->只会回调到主进程上
		if (isMainProcess())
		{
			initBaseInMainProcess(application);
			AppLifeCycleProxy.setImp(getAppLifeCycleImp());// 先设置回调
			// UtilsActions.getInstance().listenSomeKey(Intent.ACTION_BOOT_COMPLETED, (context, intent) -> AppLifeCycleProxy.onAppReceiveBootCompleted());
			UtilsActions.getInstance().listenSomeKey(Intent.ACTION_SHUTDOWN, (context, intent) -> AppLifeCycleProxy.onAppReceiveShutdown());
		}

		LogProxy.setLogVisibility(isLogEnable());
		LogProxy.v(TAG, "打印log测试");

		Locale defaultLocale = Locale.getDefault();
		LogProxy.i(TAG, "此时Locale.getDefault()= " + StringUtil.getPrintString(defaultLocale));

		LogProxy.i(TAG, "多进程中初始化");

		ActivitiesManager.getInstance().init(application);//Activity 管理
	}

	protected abstract boolean isLogEnable();//是否在控制栏打印log

	protected abstract IAppLifeCycle getAppLifeCycleImp();//获取生命周期的回调

	protected abstract void initBaseInMainProcess(Application application);//需要在主进程中初始化的

	protected abstract void initInMultiProcess(Application application);//需要在多进程中初始化的

	protected abstract void initInMainProcess(Application application);//需要在主进程中初始化的
}
