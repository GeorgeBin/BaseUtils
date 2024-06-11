package com.georgebindragon.application.sample;

import android.app.Application;

import com.georgebindragon.base.app.application.BaseApplication;
import com.georgebindragon.base.app.lifecycle.AppLifeCycleProxy;
import com.georgebindragon.base.app.lifecycle.IAppLifeCycle;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建人：George
 * 类名称：MainApplication
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MainApplication extends BaseApplication implements IAppLifeCycle
{
	@Override
	protected boolean isLogEnable()
	{
		return true;
	}

	@Override
	protected IAppLifeCycle getAppLifeCycleImp() { return null; }

	@Override
	protected void initBaseInMainProcess(Application application)
	{
	}

	@Override
	protected void initInMultiProcess(Application application)
	{
		AppLifeCycleProxy.setImp(this);
	}

	@Override
	protected void initInMainProcess(Application application)
	{
		int screenWidth = QMUIDisplayHelper.getScreenWidth(application);
		LogProxy.d(TAG, "QMUIDisplayHelper.getScreenWidth=" + screenWidth);

		int screenHeight = QMUIDisplayHelper.getScreenHeight(application);
		LogProxy.d(TAG, "QMUIDisplayHelper.getScreenHeight=" + screenHeight);
	}

	@Override
	public void onAppStart()
	{
		LogProxy.i(TAG, "log测试: onAppStart");

		Map<String, String> testMap = new HashMap<>();
		testMap.put("key1", "value1");
		testMap.put("key2", "value2");
		testMap.put("key3", "value3");
		testMap.put("key4", "value4");
		testMap.put("key5", "value5");

		String printMap = StringUtil.getPrintString(testMap);

		LogProxy.i(TAG, "onAppStart-->printMap=" + printMap);

	}

	@Override
	public void onAppReceiveShutdown()
	{
		LogProxy.i(TAG, "log测试: onAppReceiveShutdown");
	}

	@Override
	public void onAppExit()
	{
		LogProxy.i(TAG, "log测试: onAppExit");
	}
}
