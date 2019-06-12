package com.georgebindragon.base.system.hardware.call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.system.software.PermissionUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Queue;

/**
 * 创建人：George
 * 类名称：PhoneCallMonitor
 * 类概述：电话呼叫状态的监听器
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PhoneCallMonitor extends BaseListenerMonitor<PhoneCallMonitor.PhoneCallStateListener>
{
	private static final PhoneCallMonitor ourInstance = new PhoneCallMonitor();

	public static PhoneCallMonitor getInstance() { return ourInstance; }

	private static final String phoneStateAction = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

	private void onBroadcastReceived(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (EmptyUtil.notEmpty(action) && phoneStateAction.equalsIgnoreCase(action))
		{
			LogProxy.d(TAG, "onBroadcastReceived-->电话状态");

			Bundle extras = intent.getExtras();
			if (EmptyUtil.notEmpty(extras))
			{
				String incoming_number = extras.getString("incoming_number");
				String stateString = extras.getString("state");

				int state;
				if (EmptyUtil.notEmpty(stateString))
				{
					switch (stateString)
					{
						case "RINGING":
							state = TelephonyManager.CALL_STATE_RINGING;
							break;
						case "IDLE":
							state = TelephonyManager.CALL_STATE_IDLE;
							break;
						case "OFFHOOK":
							state = TelephonyManager.CALL_STATE_OFFHOOK;
							break;
						default:
							state = queryCurrentCallState(context);
							LogProxy.d(TAG, "onReceive-->stateString 未能识别=" + stateString);
							break;
					}
				} else
				{
					state = queryCurrentCallState(context);
				}
				notifyListeners(state, StringUtil.getPrintString(incoming_number));

			} else
			{
				LogProxy.d(TAG, "onReceive-->extras=" + StringUtil.getPrintString(extras));
			}
		} else
		{
			LogProxy.d(TAG, "onReceive-->action=" + StringUtil.getPrintString(action));
		}
	}

	/**
	 * 注册监听器
	 *
	 * @param context 注册Broadcast需要的context
	 * @return 是否注册成功, 如果返回false, 请申请权限
	 */
	public boolean registerMonitor(Context context)
	{
		UtilsActions.getInstance().listenSomeAction(phoneStateAction, this::onBroadcastReceived);
		LogProxy.d(TAG, "registerMonitor-->注册");

		//有这两个权限之一, 即可获取到状态
		if (!PermissionUtil.checkPermission(context, Manifest.permission.READ_PHONE_STATE) && !PermissionUtil.checkPermission(context, Manifest.permission.READ_CALL_LOG))
		{
			LogProxy.e(TAG, "registerMonitor-->提示：尚未获得权限");
			return false;
		}
		return true;
	}

	/**
	 * 注销监听器
	 * 注销后, 所有的Listener都不会再收到监听回调
	 */
	public void unregisterMonitor()
	{
		UtilsActions.getInstance().stopSomeAction(phoneStateAction);
		clearListeners();
		LogProxy.d(TAG, "unregisterMonitor-->注销");
	}

	private void notifyListeners(int callState, String incomingNumber)
	{
		final Queue<PhoneCallStateListener> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (PhoneCallStateListener listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onPhoneState(callState, incomingNumber);
			}
		}
	}

	private int queryCurrentCallState(Context context)
	{
		return PhoneCallUtil.getCallState(context);
	}

	public interface PhoneCallStateListener
	{
		/**
		 * 呼叫状态的回调，返回的消息为：呼叫状态（int）+呼叫号码（String）
		 *
		 * @param callState      {@link android.telephony.TelephonyManager#CALL_STATE_RINGING}
		 *                       {@link android.telephony.TelephonyManager#CALL_STATE_IDLE}
		 *                       {@link android.telephony.TelephonyManager#CALL_STATE_OFFHOOK}
		 * @param incomingNumber 呼叫的号码（需要根据其他状态判断，为呼入还是呼出）
		 */
		void onPhoneState(int callState, String incomingNumber);
	}
}
