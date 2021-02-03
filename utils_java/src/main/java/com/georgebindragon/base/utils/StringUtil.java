package com.georgebindragon.base.utils;

import com.georgebindragon.base.function.log.LogProxy;

import java.util.Collection;
import java.util.Map;

/**
 * 创建人：George
 * 类名称：StringUtil
 * 类概述：字符串相关工具类
 * 详细描述：
 *
 * 1、获取可打印字符串
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class StringUtil
{
	private static final String TAG = "StringUtil-->";

	public static final String NULL = "NULL";

	public static String getSuccessString(boolean success) { return success ? "成功" : "失败"; }

	//返回可安全打印的字符串
	public static String getPrintString(Object object) { return getPrintString(object, NULL); }

	public static String getPrintString(Object object, String defaultValue)
	{
		try
		{
			if (EmptyUtil.isEmpty(defaultValue)) defaultValue = NULL;
			if (EmptyUtil.isEmpty(object)) return defaultValue;

			return getObjectString(object);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getPrintString", e);
			e.printStackTrace();
		}
		return "Exception";
	}

	public static String getObjectString(Object object)
	{
		try
		{
			if (EmptyUtil.isEmpty(object)) return NULL;

			if (object instanceof String) return (String) object;

			else if (object instanceof byte[]) return getBytesString((byte[]) object);
			else if (object instanceof Collection) return getCollectionString((Collection<?>) object);
			else if (object instanceof Map) return getMapString((Map<?, ?>) object);
			else
			{
				return object.toString();// 很多类型都可以直接由这个得到有效的打印数据，例如：Enum、八大基础类型
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getObjectString", e);
		}
		return "Exception";
	}

	public static String getBytesString(byte[] bytes)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("byte[]: ");

		if (null != bytes && bytes.length > 0)
		{
			buffer.append("{");
			for (byte b : bytes)
			{
				buffer.append(b);
				buffer.append(",");
			}
			buffer.append("}");

			return buffer.toString();
		} else
		{
			buffer.append(NULL);
		}
		return buffer.toString();
	}

	public static String getMapString(Map<?, ?> map)
	{
		StringBuilder buffer = new StringBuilder();

		if (null != map && map.size() > 0)
		{
			boolean addHead = false;

			for (Map.Entry<?, ?> entry : map.entrySet())
			{
				Object key   = entry.getKey();
				Object value = entry.getValue();
				if (!addHead)
				{
					addHead = true;
					buffer.append("Map");
					buffer.append("<");
					if (EmptyUtil.notEmpty(key))
					{
						buffer.append(key.getClass().getSimpleName());
					} else
					{
						buffer.append(NULL);
					}
					buffer.append(",");
					if (EmptyUtil.notEmpty(value))
					{
						buffer.append(value.getClass().getSimpleName());
					} else
					{
						buffer.append(NULL);
					}
					buffer.append(">:{");
				}

				buffer.append("<");
				buffer.append(getObjectString(key));
				buffer.append(",");
				buffer.append(getObjectString(value));
				buffer.append(">");
				buffer.append(",");
			}

			buffer.append("}");

			return buffer.toString();
		} else
		{
			buffer.append("Map: ");
			buffer.append(NULL);
		}
		return buffer.toString();
	}

	public static String getCollectionString(Collection<?> collection)
	{
		StringBuilder buffer = new StringBuilder();

		if (null != collection && collection.size() > 0)
		{
			boolean addHead = false;
			for (Object b : collection)
			{
				if (!addHead)
				{
					addHead = true;
					buffer.append("Collection");
					buffer.append("<");
					if (EmptyUtil.notEmpty(b))
					{
						buffer.append(b.getClass().getSimpleName());
					} else
					{
						buffer.append(NULL);
					}
					buffer.append(">:{");
				}

				buffer.append(getObjectString(b));
				buffer.append(",");
			}
			buffer.append("}");

			return buffer.toString();
		} else
		{
			buffer.append("Collection: ");
			buffer.append(NULL);
		}
		return buffer.toString();
	}

}
