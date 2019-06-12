package com.georgebindragon.base.receiver;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 * 类名称：BroadcastMonitor
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class BroadcastMonitor extends BaseListenerMonitor<IBaseReceiverCallBack>
{
	void notifyListeners(Context context, Intent intent)
	{
		final Queue<IBaseReceiverCallBack> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (IBaseReceiverCallBack listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener))
				{
					listener.onReceive(context, intent);
				}
			}
		}
	}
}
