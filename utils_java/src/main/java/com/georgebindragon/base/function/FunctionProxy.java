package com.georgebindragon.base.function;

/**
 * 创建人：George
 * 类名称：FunctionProxy
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class FunctionProxy
{

	public static void registerFunction(Class functionClass, Object functionImpl)
	{
		if (null == functionClass || null == functionImpl) return;

		FunctionManager.getInstance().registerService(functionClass.getCanonicalName(), functionImpl);
	}

	public static <T> T getFunction(Class functionClass)
	{
		if (null == functionClass) return null;
		try
		{
			return (T) FunctionManager.getInstance().getLocalService(functionClass.getCanonicalName());
		} catch (Exception e)
		{
			return null;
		}
	}

	public static void unregisterFunction(Class functionClass)
	{
		if (null == functionClass) return;

		FunctionManager.getInstance().unregisterService(functionClass.getCanonicalName());
	}
}
