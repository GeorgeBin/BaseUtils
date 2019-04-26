package com.georgebindragon.base.test.receiver;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.abilities.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：PhoneCallStateMonitor
 * 类概述：电话状态监听
 * 详细描述：
 *
 * 获取机器的通话状态广播{@link TelephonyManager#ACTION_PHONE_STATE_CHANGED}
 *
 * 需要权限：
 * {@link Manifest.permission#READ_PHONE_STATE}
 * 或者
 * {@link Manifest.permission#READ_CALL_LOG}
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class TestProxy
{
	private static final String TAG = "TestProxy-->";

	public static void registerTestBroadcast(Context context, String[] actions, IBaseReceiverCallBack callBack)
	{
		BroadcastReceiverUtil.registerBroadcastByActionStrings(context, actions, TestReceiver.getInstance());
		LogProxy.d(TAG, "registerTestBroadcast-->注册");

		if (EmptyUtil.notEmpty(callBack))
		{
			TestReceiver.getInstance().setReceiverCallBack(callBack);
			LogProxy.d(TAG, "registerTestBroadcast-->设置回调");
		} else LogProxy.e(TAG, "registerTestBroadcast-->设置回调错误：参数错误");

	}

	public static void unregisterTestBroadcast(Context context)
	{
		BroadcastReceiverUtil.unregisterBroadcastReceiver(context, TestReceiver.getInstance());
		TestReceiver.getInstance().setReceiverCallBack(null);
		LogProxy.d(TAG, "unregisterTestBroadcast-->注销");
	}

}
