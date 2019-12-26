package com.georgebindragon.base.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人：George
 *
 * 描述：Map管理的一种实现：使用 ConcurrentHashMap
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class MapManager<K, V>
{
	protected String TAG = "MapManager: " + getClass().getSimpleName() + "-->";

	protected Map<K, V> map = new ConcurrentHashMap<>();

	public V getValueByKey(K key)
	{
		return map.get(key);
	}

	public void setKeyAndValue(K key, V t)
	{
		map.put(key, t);
	}

	public void deleteKeyAndValue(K key)
	{
		map.remove(key);
	}

	public void clearMap()
	{
		map = new ConcurrentHashMap<>();
	}
}
