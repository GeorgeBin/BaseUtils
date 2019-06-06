package com.georgebindragon.base.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 创建人：George
 * 类名称：UtilsReceiver
 * 类概述：
 * 监听广播：
 *
 * 各种需要测试的广播
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UtilsReceiver extends BroadcastReceiver
{
	private static final UtilsReceiver ourInstance = new UtilsReceiver();

	public static UtilsReceiver getInstance() { return ourInstance; }

	//接收到广播
	@SuppressLint("UnsafeProtectedBroadcastReceiver")
	@Override
	public void onReceive(Context context, Intent intent)
	{
		UtilsActions.getInstance().onBroadcastReceived(context, intent);
	}
}
