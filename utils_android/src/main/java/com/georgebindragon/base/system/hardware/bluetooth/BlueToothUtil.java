package com.georgebindragon.base.system.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.georgebindragon.base.BaseUtils;


/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：BlueToothUtil
 * 类概述：蓝牙工具类
 * 创建时间：2017-08-31 10:08
 * 详细描述：蓝牙相关工具类：蓝牙是否连接
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BlueToothUtil
{
	private static final String TAG = "BlueToothUtil-->";

	public static final int SpeakerMode = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? AudioManager.MODE_IN_COMMUNICATION : AudioManager.MODE_IN_CALL;

	public static boolean isBTOpened()
	{
		BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
		return blueAdapter != null && blueAdapter.isEnabled();
	}

	public static boolean isBTOpenAndConnected()
	{
		return isBTOpened() && (getBTConnectedState() > 0);
	}

	public static final int BTState_None                  = -2;
	public static final int BTState_NotOpened             = -1;
	public static final int BTState_Opened_NoOneConnected = 0;
	public static final int BTState_HeadSet               = BluetoothProfile.HEADSET;
	public static final int BTState_A2dp                  = BluetoothProfile.A2DP;
	public static final int BTState_Health                = BluetoothProfile.HEALTH;
	public static final int BTState_Internet              = 4;

	/**
	 * 获取蓝牙当前的连接状态
	 *
	 * @return 连接的具体设备：
	 * 值：-2：不存在、-1：关闭、0：蓝牙打开，但是没有连接、1：播放+话筒设备、2：播放设备、3：穿戴设备、4：通过蓝牙上网
	 */
	public static int getBTConnectedState()
	{
		int              blueConState = -3;
		BluetoothAdapter ba           = BluetoothAdapter.getDefaultAdapter();

		//蓝牙适配器是否存在，即是否发生了错误
		if (null == ba)
		{
			blueConState = BTState_None;//不存在蓝牙功能
		} else if (ba.isEnabled())//蓝牙开启
		{
			blueConState = BTState_Opened_NoOneConnected;
			int headset = ba.getProfileConnectionState(BluetoothProfile.HEADSET);        //蓝牙头戴式耳机，支持语音输入输出
			int a2dp    = ba.getProfileConnectionState(BluetoothProfile.A2DP);           //可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
			int health  = ba.getProfileConnectionState(BluetoothProfile.HEALTH);         //蓝牙穿戴式设备

			//查看是否蓝牙是否连接到三种设备的一种，以此来判断是否处于连接状态还是打开并没有连接的状态
			if (headset == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BTState_HeadSet;
			} else if (a2dp == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BTState_A2dp;
			} else if (health == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BTState_Health;
			}
			ConnectivityManager cm = (ConnectivityManager) BaseUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != cm)
			{
				NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
				if (null != netInfo)
				{
					NetworkInfo.State blt = netInfo.getState();
					if (blt == NetworkInfo.State.CONNECTED) //是否是使用蓝牙 分享/连接 网络
					{
						blueConState = BTState_Internet;
					}
				}
			}
		} else
		{
			blueConState = BTState_NotOpened; // 蓝牙关闭
		}
		return blueConState;
	}


	public static void openBluetoothSCO()
	{
		AudioManager audioManager = (AudioManager) BaseUtils.getContext().getSystemService(Context.AUDIO_SERVICE);
		if (null != audioManager)
		{
			audioManager.setBluetoothScoOn(true);
			audioManager.startBluetoothSco();
			audioManager.setMode(SpeakerMode);
		}
	}

	public static void closeBluetoothSCO()
	{
		AudioManager audioManager = (AudioManager) BaseUtils.getContext().getSystemService(Context.AUDIO_SERVICE);
		if (null != audioManager && audioManager.isBluetoothScoOn())
		{
			audioManager.setBluetoothScoOn(false);
			audioManager.stopBluetoothSco();
			audioManager.setMode(0);
		}
	}
}
