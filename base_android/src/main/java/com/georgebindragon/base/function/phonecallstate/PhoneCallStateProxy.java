package com.georgebindragon.base.function.phonecallstate;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.system.software.PermissionUtil;

/**
 * 创建人：George
 * 类名称：PhoneCallStateProxy
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


public class PhoneCallStateProxy
{
	private static final String TAG = "PhoneCallStateProxy-->";

	private static String[] phoneStateAction = {TelephonyManager.ACTION_PHONE_STATE_CHANGED,};

	public static void registerPhoneCallStateBroadcast(Context context, IPhoneCallStateCallBack callBack)
	{
		BroadcastReceiverUtil.registerBroadcastByActionStrings(context, phoneStateAction, Integer.MAX_VALUE, PhoneCallStateBroadcastReceiver.getInstance());
		PhoneCallStateBroadcastReceiver.getInstance().setReceiverCallBack(callBack);
		LogProxy.d(TAG, "registerPhoneCallStateBroadcast-->注册");
		if (!PermissionUtil.checkPermission(context, Manifest.permission.READ_PHONE_STATE)
				&& !PermissionUtil.checkPermission(context, Manifest.permission.READ_CALL_LOG))
		{
			LogProxy.e(TAG, "registerPhoneCallStateBroadcast-->提示：尚未获得权限");
		}
	}

	public static void unregisterPhoneCallStateBroadcast(Context context)
	{
		BroadcastReceiverUtil.unregisterBroadcastReceiver(context, PhoneCallStateBroadcastReceiver.getInstance());
		PhoneCallStateBroadcastReceiver.getInstance().setReceiverCallBack(null);
		LogProxy.d(TAG, "unregisterPhoneCallStateBroadcast-->注销");
	}

}
