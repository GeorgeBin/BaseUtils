package com.georgebindragon.base.system.hardware.call;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：PhoneCallUtil
 * 类概述：电话相关的工具类
 * 详细描述：
 *
 * 1. 查询当前电话状态
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PhoneCallUtil
{
	public static final int callState_Default = -1;

	public static int getCallState(Context context)
	{
		int phoneState = callState_Default;
		if (EmptyUtil.notEmpty(context))
		{
			TelephonyManager mTelephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
			if (mTelephonyManager != null) phoneState = mTelephonyManager.getCallState();
		}
		return phoneState;
	}
}
