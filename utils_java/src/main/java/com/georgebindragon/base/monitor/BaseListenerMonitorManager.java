package com.georgebindragon.base.monitor;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 *
 * 描述：Monitor的管理类，如果 Monitor 不能满足需求的时候（例如：需要监听多个事件，根据需求监听）
 * 例如：
 * 1. 网络连接的变化、蓝牙连接的变化、音频焦点变化、电话通话状态变化，为单一事件，使用 Monitor 即可
 * 2. 根据不同ID设置闹钟、所有广播的监听，不是单一事件，使用 MonitorManager
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseListenerMonitorManager<K, V extends BaseListenerMonitor<L>, L> extends BaseMonitorManager<K, V>
{

	public void listenSomeKey(K key, L listener)
	{
		LogProxy.i(TAG, "listenSomeKey-->key=" + StringUtil.getPrintString(key));

		if (EmptyUtil.notEmpty(key, listener))
		{
			V monitor = getMonitor(key);
			if (null == monitor)
			{
				monitor = onMonitorFirstCreate(key);
				LogProxy.i(TAG, "listenSomeKey-->onMonitorFirstCreate-->此前没有此 Monitor: " + StringUtil.getPrintString(key));

				if (null != monitor)
				{
					registerMonitor(key, monitor);
					LogProxy.i(TAG, "listenSomeKey-->registerMonitor-->未注册过, 先注册此 key");
				} else
				{
					LogProxy.i(TAG, "listenSomeKey-->registerMonitor--> monitor==NULL");
				}
			}
			if (null != monitor)
			{
				monitor.addListener(listener);
				LogProxy.i(TAG, "listenSomeKey-->添加listener");
			} else
			{
				LogProxy.i(TAG, "listenSomeKey-->addListener--> monitor==NULL");
			}
		} else
		{
			LogProxy.i(TAG, "listenSomeKey-->失败");
		}
	}

	protected abstract V onMonitorFirstCreate(K key);

	public void listenSomeKeys(K[] keys, L listener)
	{
		if (EmptyUtil.notEmpty(keys, listener))
		{
			for (K key : keys)
			{
				listenSomeKey(key, listener);
			}
		}
	}

	public void stopSomeListener(K key, L listener)
	{
		LogProxy.i(TAG, "stopSomeListener-->key=" + StringUtil.getPrintString(key));

		if (EmptyUtil.notEmpty(key, listener))
		{
			BaseListenerMonitor<L> monitor = getMonitor(key);
			if (null != monitor)
			{
				monitor.removeListener(listener);
				LogProxy.i(TAG, "stopSomeListener-->注册过, 移除此 key 下的 回调");
			}
		} else
		{
			LogProxy.i(TAG, "stopSomeListener-->失败");
		}
	}

	public void stopSomeListeners(K[] keys, L listener)
	{
		LogProxy.i(TAG, "stopSomeListeners-->keys=" + StringUtil.getPrintString(keys));
		if (EmptyUtil.notEmpty(keys, listener))
		{
			for (K key : keys)
			{
				stopSomeListener(key, listener);
			}
		}
	}

	public void stopSomeKey(K key)
	{
		LogProxy.i(TAG, "stopSomeKey-->key=" + StringUtil.getPrintString(key));

		if (EmptyUtil.notEmpty(key))
		{
			unregisterMonitor(key);
			V monitor = getMonitor(key);
			if (null != monitor) monitor.onThisMonitorRemoved();
		}
	}

	public void stopSomeKeys(K[] keys)
	{
		LogProxy.i(TAG, "stopSomeKeys-->keys=" + StringUtil.getPrintString(keys));

		if (null != keys && keys.length > 0)
		{
			for (K key : keys)
			{
				stopSomeKey(key);
			}
		}
	}
}
