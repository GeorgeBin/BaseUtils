package com.georgebindragon.base.system.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.thread.DebounceHandler;
import com.georgebindragon.base.thread.HandlerUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 *
 * 描述：蓝牙状态监听器：是否连接，连接的设备类型
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BlueToothMonitor extends BaseListenerMonitor<BlueToothMonitor.BlueToothListener>
{
	private static final String TAG = "BlueToothMonitor-->";

	private static final BlueToothMonitor ourInstance = new BlueToothMonitor();

	public static BlueToothMonitor getInstance() { return ourInstance; }

	protected Runnable        checkRunnable = this::checkBlueToothAndNotify;
	private   DebounceHandler debounceHandler;

	private BlueToothMonitor()
	{
		debounceHandler = new DebounceHandler(HandlerUtil.getOtherHandler(), checkRunnable, 3000);
		init();
	}

	private void init()
	{
		UtilsActions.getInstance().listenSomeKey(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED, this::onBlueToothStateChangeReceive);//蓝牙设备连接状态
		UtilsActions.getInstance().listenSomeKey(BluetoothAdapter.ACTION_STATE_CHANGED, this::onBlueToothStateChangeReceive);//蓝牙打开关闭
		LogProxy.d(TAG, "init-->注册");

		onBlueToothChange();
	}

	public void destroy()
	{
		UtilsActions.getInstance().stopSomeListener(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED, this::onBlueToothStateChangeReceive);
		UtilsActions.getInstance().stopSomeListener(BluetoothAdapter.ACTION_STATE_CHANGED, this::onBlueToothStateChangeReceive);
		debounceHandler.destroy();
		LogProxy.d(TAG, "destroy-->注销");
	}

	private void onBlueToothStateChangeReceive(Context context, Intent intent)
	{
		if (EmptyUtil.isEmpty(intent)) return;
		String action = intent.getAction();
		if (EmptyUtil.isEmpty(action)) return;

		LogProxy.i(TAG, "onBlueToothStateChangeReceive-->action=" + action);

		if (BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED.equals(action) || BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
		{
			onBlueToothChange();
		}
	}

	private void onBlueToothChange()
	{
		LogProxy.i(TAG, "onBlueToothChange-->");
		debounceHandler.onDebounce();
	}

	private BlueToothState lastBTState;

	private void checkBlueToothAndNotify()
	{
		LogProxy.i(TAG, "checkBlueToothAndNotify-->");

		BlueToothState btConnectedState = BlueToothUtil.getBTConnectedState();
		if (null != btConnectedState && btConnectedState != lastBTState)
		{
			notifyListeners(btConnectedState);
			lastBTState = btConnectedState;
		}
	}

	private void notifyListeners(BlueToothState btConnectedState)
	{
		final Queue<BlueToothListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (BlueToothListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onBlueToothStateChange(btConnectedState);
			}
		}
	}

	public interface BlueToothListener
	{
		void onBlueToothStateChange(BlueToothState btConnectedState);
	}

}
