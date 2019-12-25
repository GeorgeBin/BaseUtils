package com.georgebindragon.base.system.hardware.bluetooth;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 *
 * 描述：不存在、关闭、蓝牙打开，但是没有连接、播放+话筒设备、播放设备、穿戴设备、通过蓝牙上网
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public enum BlueToothState
{
	UNKNOWN,
	BTState_None,
	BTState_NotOpened,
	BTState_Opened_NoOneConnected,

	BTState_HeadSet,// 1：播放+话筒设备
	BTState_A2dp,// 2：播放设备
	BTState_Health,// 3：穿戴设备
	BTState_Internet;// 4：通过蓝牙上网


	private static final String TAG = "BlueToothState-->";

	public static BlueToothState valueOf(int ordinal)
	{
		if (ordinal < 0 || ordinal >= values().length)
		{
			LogProxy.e(TAG, "Invalid ordinal");
			return UNKNOWN;
		}
		return values()[ordinal];
	}

	public static boolean isConnectedState(BlueToothState blueToothState)
	{
		return (blueToothState.ordinal() >= BTState_HeadSet.ordinal());
	}
}
