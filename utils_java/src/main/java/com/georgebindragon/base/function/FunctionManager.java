package com.georgebindragon.base.function;

/**
 * 创建人：George
 * 类名称：FunctionManager
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

class FunctionManager extends MapManager<String,Object>
{
	private static FunctionManager sInstance;

	static FunctionManager getInstance()
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

	private FunctionManager() { }

	Object getLocalService(String module)
	{
		return getValueByKey(module);
	}

	void registerService(String module, Object serviceImpl)
	{
		setKeyAndValue(module, serviceImpl);
	}

	void unregisterService(String module)
	{
		deleteKeyAndValue(module);
	}
}
