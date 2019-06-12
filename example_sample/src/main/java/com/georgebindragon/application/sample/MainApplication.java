package com.georgebindragon.application.sample;

import android.app.Application;

import com.georgebindragon.base.app.application.BaseApplication;
import com.georgebindragon.base.function.log.LogProxy;
import com.qmuiteam.qmui.layout.QMUILayoutHelper;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

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


public class MainApplication extends BaseApplication
{
	@Override
	protected boolean isLogEnable()
	{
		return BuildConfig.DEBUG;
	}

	@Override
	protected void initInMainProcess(Application application)
	{
		int screenWidth = QMUIDisplayHelper.getScreenWidth(application);
		LogProxy.d(TAG, "QMUIDisplayHelper.getScreenWidth=" + screenWidth);

		int screenHeight = QMUIDisplayHelper.getScreenHeight(application);
		LogProxy.d(TAG, "QMUIDisplayHelper.getScreenHeight=" + screenHeight);
	}
}
