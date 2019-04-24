package com.georgebindragon.base.service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：BaseRemoteService
 * 类概述：远程服务基类
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseRemoteService extends Service
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	private Binder mBinder=getBinder();

	@Override
	public IBinder onBind(Intent intent)
	{
		LogProxy.d(TAG, "onBind");
		return mBinder;
	}

	protected abstract Binder getBinder();

	@Override
	public void onDestroy()
	{
		LogProxy.d(TAG, "onDestroy");
		super.onDestroy();
	}
}
