package com.georgebindragon.base.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitorManager;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：PowerSwitchReceiver
 * 类概述：组别旋钮广播
 * 监听广播：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressLint("StaticFieldLeak")
public class UtilsActions extends BaseListenerMonitorManager<String, BroadcastMonitor, IBaseReceiverCallBack>
{
	private static final UtilsActions ourInstance = new UtilsActions();

	public static UtilsActions getInstance() { return ourInstance; }

	private UtilsActions() { }

	//接收到广播
	void onBroadcastReceived(Context context, Intent intent)
	{
		LogProxy.i(TAG, "onBroadcastReceived");

		if (EmptyUtil.notEmpty(intent))
		{
			String action = intent.getAction();
			if (EmptyUtil.notEmpty(action))
			{
				BroadcastMonitor monitor = getMonitor(action);
				if (null != monitor)
				{
					monitor.notifyListeners(context, intent);
				}
			}
		}
	}

	@Override
	protected BroadcastMonitor onMonitorFirstCreate(String key)
	{
		BroadcastReceiverUtil.registerBroadcastByActionString(BaseUtils.getContext(), key, UtilsReceiver.getInstance());
		return new BroadcastMonitor();
	}
}
