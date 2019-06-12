package com.georgebindragon.base.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class UtilsActions
{
	private static final String TAG = "UtilsActions-->";

	private static UtilsActions sInstance;

	public static UtilsActions getInstance()
	{
		if (null == sInstance)
		{
			synchronized (UtilsActions.class)
			{
				if (null == sInstance) { sInstance = new UtilsActions(); }
			}
		}
		return sInstance;
	}

	private Map<String, BroadcastMonitor> monitorMap = new ConcurrentHashMap<>();

	private UtilsActions() { }

	private BroadcastMonitor getMonitor(String action)
	{
		return monitorMap.get(action);
	}

	private void registerMonitor(String action, BroadcastMonitor monitor)
	{
		monitorMap.put(action, monitor);
	}

	private void unregisterMonitor(String action)
	{
		monitorMap.remove(action);
	}

	public void listenSomeAction(String action, IBaseReceiverCallBack listener)
	{
		LogProxy.i(TAG, "listenSomeAction-->action=" + StringUtil.getPrintString(action));

		if (EmptyUtil.notEmpty(action, listener))
		{
			BroadcastMonitor monitor = getMonitor(action);
			if (null == monitor)
			{
				monitor = new BroadcastMonitor();
				registerMonitor(action, monitor);
				LogProxy.i(TAG, "listenSomeAction-->未注册过, 先注册此action");

				BroadcastReceiverUtil.registerBroadcastByActionString(BaseUtils.getContext(), action, UtilsReceiver.getInstance());
			}
			monitor.addListener(listener);
			LogProxy.i(TAG, "listenSomeAction-->添加回调");
		} else
		{
			LogProxy.i(TAG, "listenSomeAction-->失败");
		}
	}

	public void listenSomeActions(String[] actions, IBaseReceiverCallBack listener)
	{
		if (EmptyUtil.notEmpty(actions, listener))
		{
			for (String action : actions)
			{
				listenSomeAction(action, listener);
			}
		}
	}

	public void stopSomeListener(String action, IBaseReceiverCallBack listener)
	{
		LogProxy.i(TAG, "stopListenSomeAction-->action=" + StringUtil.getPrintString(action));

		if (EmptyUtil.notEmpty(action, listener))
		{
			BroadcastMonitor monitor = getMonitor(action);
			if (null != monitor)
			{
				monitor.removeListener(listener);
				LogProxy.i(TAG, "stopListenSomeAction-->注册过, 移除此 action 下的回调");
			}
		} else
		{
			LogProxy.i(TAG, "stopListenSomeAction-->失败");
		}
	}

	public void stopSomeListener(String[] actions, IBaseReceiverCallBack listener)
	{
		if (EmptyUtil.notEmpty(actions, listener))
		{
			for (String action : actions)
			{
				listenSomeAction(action, listener);
			}
		}
	}

	public void stopSomeAction(String action)
	{
		LogProxy.i(TAG, "stopSomeAction-->action=" + StringUtil.getPrintString(action));

		if (EmptyUtil.notEmpty(action))
		{
			unregisterMonitor(action);
		}
	}

	public void stopSomeAction(String[] actions)
	{
		LogProxy.i(TAG, "stopSomeAction-->action=" + StringUtil.getPrintString(actions));

		if (null != actions && actions.length > 0)
		{
			for (String action : actions)
			{
				stopSomeAction(action);
			}
		}
	}

	//接收到广播
	void onBroadcastReceived(Context context, Intent intent)
	{
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
}
