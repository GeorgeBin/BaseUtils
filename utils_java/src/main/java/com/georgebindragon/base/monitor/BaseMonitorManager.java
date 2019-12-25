package com.georgebindragon.base.monitor;

import com.georgebindragon.base.function.MapManager;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseMonitorManager<K, V extends BaseMonitor> extends MapManager<K, V>
{

	protected V getMonitor(K key)
	{
		return getValueByKey(key);
	}

	protected void registerMonitor(K key, V monitor)
	{
		setKeyAndValue(key, monitor);
	}

	protected void unregisterMonitor(K key)
	{
		deleteKeyAndValue(key);
	}

	public boolean isListeningThisKey(K key)
	{
		V monitor = getMonitor(key);
		return monitor != null;
	}

}
