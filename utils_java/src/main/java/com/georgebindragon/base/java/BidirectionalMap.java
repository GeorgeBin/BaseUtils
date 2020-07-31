package com.georgebindragon.base.java;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人：George
 * 类名称：BidirectionalMap
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BidirectionalMap<K, V>
{
	private ConcurrentHashMap<K, V> kv;
	private ConcurrentHashMap<V, K> vk;

	public BidirectionalMap()
	{
		kv = new ConcurrentHashMap<>();
		vk = new ConcurrentHashMap<>();
	}

	public V getValue(K key)
	{
		return kv.get(key);
	}

	public K getKey(V value)
	{
		return vk.get(value);
	}

	public V put(K key, V value)
	{
		vk.put(value, key);
		return kv.put(key, value);
	}

	public V remove(K key)
	{
		V remove = kv.remove(key);
		if (remove != null) vk.remove(remove);
		return remove;
	}

	public boolean replace(K key, V oldValue, V newValue)
	{
		vk.remove(oldValue);
		vk.put(newValue, key);
		return kv.replace(key, oldValue, newValue);
	}
}
