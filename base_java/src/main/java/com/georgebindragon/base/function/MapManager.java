package com.georgebindragon.base.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 创建人：George
 * 类名称：FunctionManager
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class MapManager<T>
{
	protected String TAG = "MapManager: " + getClass().getSimpleName() + "-->";

	//
	//	private static MapManager instance;
	//
	//	public static MapManager getInstance()
	//	{
	//		if (null == instance)
	//		{
	//			synchronized (MapManager.class)
	//			{
	//				if (null == instance) { instance = new MapManager(); }
	//			}
	//		}
	//		return instance;
	//	}
	//
	//	private MapManager() { }

	protected Map<String, T> map = new ConcurrentHashMap<>();

	public T getValue(String key)
	{
		return map.get(key);
	}

	public void setKeyAndValue(String key, T t)
	{
		map.put(key, t);
	}

	public void deleteKeyAndValue(String key)
	{
		map.remove(key);
	}
}
