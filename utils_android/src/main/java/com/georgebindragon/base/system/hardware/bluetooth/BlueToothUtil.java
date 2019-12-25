package com.georgebindragon.base.system.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.system.hardware.media.AudioManagerUtil;


/**
 * 创建人：George
 *
 * 描述：蓝牙相关工具类：蓝牙是否开启，蓝牙是否连接着设备，蓝牙的连接状态
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BlueToothUtil
{
	private static final String TAG = "BlueToothUtil-->";

	public static boolean isBTOpened()
	{
		BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
		return blueAdapter != null && blueAdapter.isEnabled();
	}

	public static boolean isBTOpenAndConnected()
	{
		return isBTOpened() && (BlueToothState.isConnectedState(getBTConnectedState()));
	}

	/**
	 * 获取蓝牙当前的连接状态
	 *
	 * @return 连接的具体设备：
	 * 值：-2：不存在、-1：关闭、0：蓝牙打开，但是没有连接、1：播放+话筒设备、2：播放设备、3：穿戴设备、4：通过蓝牙上网
	 */
	public static BlueToothState getBTConnectedState()
	{
		BlueToothState   blueConState;
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();

		//蓝牙适配器是否存在，即是否发生了错误
		if (null == ba)
		{
			blueConState = BlueToothState.BTState_None;//不存在蓝牙功能
		} else if (ba.isEnabled())//蓝牙开启
		{
			blueConState = BlueToothState.BTState_Opened_NoOneConnected;
			int headset = ba.getProfileConnectionState(BluetoothProfile.HEADSET);        //蓝牙头戴式耳机，支持语音输入输出
			int a2dp    = ba.getProfileConnectionState(BluetoothProfile.A2DP);           //可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
			int health  = ba.getProfileConnectionState(BluetoothProfile.HEALTH);         //蓝牙穿戴式设备

			//查看是否蓝牙是否连接到三种设备的一种，以此来判断是否处于连接状态还是打开并没有连接的状态
			if (headset == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BlueToothState.BTState_HeadSet;
			} else if (a2dp == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BlueToothState.BTState_A2dp;
			} else if (health == BluetoothProfile.STATE_CONNECTED)
			{
				blueConState = BlueToothState.BTState_Health;
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
						blueConState = BlueToothState.BTState_Internet;
					}
				}
			}
		} else
		{
			blueConState = BlueToothState.BTState_NotOpened; // 蓝牙关闭
		}
		return blueConState;
	}


	public static void openBluetoothSCO()
	{
		AudioManager audioManager = getAudioManager();
		if (null != audioManager)
		{
			audioManager.setBluetoothScoOn(true);
			audioManager.startBluetoothSco();
		}
	}

	public static void closeBluetoothSCO()
	{
		AudioManager audioManager = getAudioManager();
		if (null != audioManager && audioManager.isBluetoothScoOn())
		{
			audioManager.setBluetoothScoOn(false);
			audioManager.stopBluetoothSco();
		}
	}

	public static AudioManager getAudioManager() { return AudioManagerUtil.getAudioManager(); }

}
