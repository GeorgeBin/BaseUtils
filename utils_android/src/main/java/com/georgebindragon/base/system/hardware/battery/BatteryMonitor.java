package com.georgebindragon.base.system.hardware.battery;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 *
 * 描述：电池变化广播监听器：电池电量 + 电池充电状态
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BatteryMonitor extends BaseListenerMonitor<BatteryMonitor.BatteryListener> implements IBaseReceiverCallBack
{
	private static final String TAG = "BatteryMonitor-->";

	private static final BatteryMonitor ourInstance = new BatteryMonitor();

	public static BatteryMonitor getInstance() { return ourInstance; }

	private BatteryMonitor() { }

	private static final String ACTION_BATTERY = Intent.ACTION_BATTERY_CHANGED;

	public void init()
	{
		UtilsActions.getInstance().listenSomeKey(ACTION_BATTERY, this);
		LogProxy.d(TAG, "init-->注册");
	}

	public void destroy()
	{
		UtilsActions.getInstance().stopSomeListener(ACTION_BATTERY, this);
		LogProxy.d(TAG, "destroy-->注销完成");
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (EmptyUtil.isEmpty(intent)) return;
		String action = intent.getAction();
		if (EmptyUtil.isEmpty(action)) return;

		if (ACTION_BATTERY.equals(action))
		{
			int status  = BatteryUtil.getBatteryStatusFromIntent(intent);
			int percent = BatteryUtil.getBatteryPercentFromIntent(intent);
			checkAndNotify(status, percent);
			LogProxy.i(TAG, "onReceive-->percent=" + percent, "status=" + status);
		}
	}

	private int lastStatus  = -1;
	private int lastPercent = -1;

	public int getBatteryStatus() { return lastStatus; }

	public int getBatteryPercent() { return lastPercent; }

	private void checkAndNotify(int status, int percent)
	{
		if (percent > 0 && (lastStatus != status || lastPercent != percent))
		{
			notifyListeners(status, percent);
			lastStatus = status;
			lastPercent = percent;
			LogProxy.i(TAG, "checkAndNotify-->percent=" + percent, "status=" + status);
		}
	}

	private void notifyListeners(int status, int percent)
	{
		final Queue<BatteryListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (BatteryListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onBatteryChange(status, percent);
			}
		}
	}

	public interface BatteryListener
	{
		void onBatteryChange(int status, int percent);
	}
}
