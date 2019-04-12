package com.georgebindragon.base.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 创建人：George
 * 类名称：EmptyUtil
 * 类概述：检查是否为空
 *
 * 详细描述：每检查一样，就要添加一样
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class EmptyUtil
{
	public static boolean isEmpty(Object object)
	{
		if (object == null)
		{
			return true;
		} else
		{
			if (object instanceof Map)//Map
			{
				return ((Map) object).size() <= 0;
			} else if (object instanceof Collection)//集合
			{
				return ((Collection) object).size() <= 0;
			}
			else if (object instanceof String)
			{
				return ((String) object).length() == 0;
			} else if (object instanceof Integer)
			{
				return ((int) object) == 0;
			} else if (object.getClass().isArray())
			{
				return Array.getLength(object) <= 0;
			}
		}
		return false;
	}

	public static boolean notEmpty(Object object)
	{
		return !isEmpty(object);//true=不为空，false=空
	}

	public static boolean notEmpty(Object... objects)
	{
		if (isEmpty(objects)) return false;//1. 参数本身就是空的 (null或者数组size<=0)

		for (Object object : objects)
		{
			boolean empty = isEmpty(object);
			if (empty) return false;//2. 如果有一个为空，则 “都不为空” 的判断结果肯定为false
		}

		return true;//3. 遍历后，都不为空，则为true
	}
}
