package com.georgebindragon.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：BaseBroadcastReceiver
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseBaseBroadcastReceiver extends BroadcastReceiver
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		LogProxy.d(TAG, "onReceive-->收到广播，广播Action为：" + ((null == intent) ? "NULL" : intent.getAction()));
		if (null == intent) return;
		onBroadcastReceived(context, intent);
	}

	protected abstract void onBroadcastReceived(Context context, Intent intent);
}
