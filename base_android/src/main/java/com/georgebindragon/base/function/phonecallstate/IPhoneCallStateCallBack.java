package com.georgebindragon.base.function.phonecallstate;

/**
 * 创建人：George
 *
 * 类名称：IBytesDataCallBacks
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IPhoneCallStateCallBack
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
