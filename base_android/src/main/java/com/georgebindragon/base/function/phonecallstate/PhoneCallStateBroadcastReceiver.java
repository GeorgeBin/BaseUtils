package com.georgebindragon.base.function.phonecallstate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.BaseBroadcastReceiver;
import com.georgebindragon.base.system.hardware.call.PhoneCallUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 * 类名称：PhoneCallStateBroadcastReceiver
 * 类概述：获取机器的通话状态广播{@link TelephonyManager#ACTION_PHONE_STATE_CHANGED}
 * 详细描述：
 *
 * 需要权限：
 * {@link android.Manifest.permission#READ_PHONE_STATE}
 * 或者
 * {@link android.Manifest.permission#READ_CALL_LOG}
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class PhoneCallStateBroadcastReceiver extends BaseBroadcastReceiver<IPhoneCallStateCallBack>
{
	private static final String key1 = "incoming_number";
	private static final String key2 = "state";

	private static final PhoneCallStateBroadcastReceiver ourInstance = new PhoneCallStateBroadcastReceiver();

	static PhoneCallStateBroadcastReceiver getInstance() { return ourInstance; }

	private PhoneCallStateBroadcastReceiver() { super();}

	private int    state           = PhoneCallUtil.callState_Default;
	private String incoming_number = StringUtil.NULL;

	@Override
	public void setReceiverCallBack(IPhoneCallStateCallBack receiverCallBack)
	{
		super.setReceiverCallBack(receiverCallBack);
		if (EmptyUtil.notEmpty(receiverCallBack)) receiverCallBack.onPhoneState(state, StringUtil.getPrintString(incoming_number));
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);

		String action = intent.getAction();
		if (EmptyUtil.notEmpty(action))
		{
			if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(action))
			{
				Bundle extras = intent.getExtras();
				if (EmptyUtil.notEmpty(extras))
				{
					incoming_number = extras.getString(key1);
					String stateString = extras.getString(key2);

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
					if (EmptyUtil.notEmpty(receiverCallBack)) receiverCallBack.onPhoneState(state, StringUtil.getPrintString(incoming_number));

				} else LogProxy.d(TAG, "onReceive-->extras=" + StringUtil.getPrintString(extras));
			}
		} else LogProxy.d(TAG, "onReceive-->action=" + StringUtil.getPrintString(action));
	}

	protected int queryCurrentCallState(Context context)
	{
		return PhoneCallUtil.getCallState(context);
	}
}
