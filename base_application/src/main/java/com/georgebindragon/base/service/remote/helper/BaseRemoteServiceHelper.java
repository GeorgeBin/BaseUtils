package com.georgebindragon.base.service.remote.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：BaseRemoteServiceHelper
 * 类概述：远程服务基础帮助类(相当于客户端, 这样客户端就可以直接调用helper, 进行使用)
 * 详细描述：
 *
 * 服务的:初始化, 绑定, 解绑
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("StaticFieldLeak")
public abstract class BaseRemoteServiceHelper
{
	protected String TAG = "RemoteServiceHelper-->" + this.getClass().getSimpleName() + "-->";

	protected Context           context;
	protected ServiceConnection connection= getConnection();

	protected abstract Class getServiceClass();

	protected BaseRemoteServiceHelper()
	{ }

	protected abstract ServiceConnection getConnection();

	protected abstract void onInit();

	public void init(Context context)
	{
		this.context = context;

		if (EmptyUtil.notEmpty(getServiceClass()))
		{
			bindService(getServiceClass());
			LogProxy.d(TAG, "init-->" + getServiceClass().getSimpleName());
		} else
		{
			LogProxy.d(TAG, "init-->NULL");
		}
		onInit();
	}

	public void destroy()
	{
		unbindService();
		if (EmptyUtil.notEmpty(getServiceClass()))
		{
			stopService(getServiceClass());
			LogProxy.d(TAG, "destroy-->" + getServiceClass().getSimpleName());
		} else
		{
			LogProxy.d(TAG, "destroy-->NULL");
		}
	}

	private void bindService(Class clasz)
	{
		try
		{
			Intent intent = new Intent(context, clasz);
			context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
			LogProxy.d(TAG, "bindService");
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "bindService-->", e);
		}
	}

	protected void rebindService()
	{
		if (EmptyUtil.notEmpty(getServiceClass()))
		{
			bindService(getServiceClass());
			LogProxy.d(TAG, "rebindService-->" + getServiceClass().getSimpleName());
		} else
		{
			LogProxy.d(TAG, "rebindService-->NULL");
		}
	}

	private void unbindService()
	{
		try
		{
			context.unbindService(connection);
			LogProxy.d(TAG, "unbindService");
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "unbindService-->", e);
		}
	}

	private void stopService(Class clasz)
	{
		try
		{
			Intent intent = new Intent(context, clasz);
			context.stopService(intent);
			LogProxy.d(TAG, "stopService");
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "stopService-->", e);
		}
		LogProxy.d(TAG, "init-->" + getServiceClass().getSimpleName());
	}
}
