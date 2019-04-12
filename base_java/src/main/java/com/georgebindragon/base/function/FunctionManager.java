package com.georgebindragon.base.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称：George2_Java_Base
 * 创建人：George
 * 类名称：FunctionManager
 * 类概述：
 * 创建时间：2019-03-22 10:26
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

class FunctionManager
{

	private static FunctionManager sInstance;

	public static FunctionManager getInstance()
	{
		if (null == sInstance)
		{
			synchronized (FunctionManager.class)
			{
				if (null == sInstance) { sInstance = new FunctionManager(); }
			}
		}
		return sInstance;
	}

	private Map<String, Object> serviceMap = new ConcurrentHashMap<>();

	private FunctionManager() { }


	public Object getLocalService(String module)
	{
		return serviceMap.get(module);
	}

	public void registerService(String module, Object serviceImpl)
	{
		serviceMap.put(module, serviceImpl);
	}

	public void unregisterService(String module)
	{
		serviceMap.remove(module);
	}
}
