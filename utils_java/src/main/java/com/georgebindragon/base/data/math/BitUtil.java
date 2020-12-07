package com.georgebindragon.base.data.math;

/**
 * 创建人：George
 * 类名称：BitUtil
 *
 * 描述：用int的每一位作为一个状态值，运算工具类
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BitUtil
{
	// example
	private final static int Status_1 = 1 << 1;
	private final static int Status_2 = 1 << 2;

	public static int addAll(int oldStatus, int... addList)
	{
		if (null != addList && addList.length >= 1)
		{
			for (int status : addList) { oldStatus = add(oldStatus, status); }
		}
		return oldStatus;
	}

	public static int add(int oldStatus, int add) { return oldStatus | add; }

	public static int removeAll(int oldStatus, int... removeList)
	{
		if (null != removeList && removeList.length >= 1)
		{
			for (int status : removeList) { oldStatus = remove(oldStatus, status); }
		}
		return oldStatus;
	}

	public static int remove(int oldStatus, int remove) { return oldStatus & ~remove; }

	public static boolean withoutAll(int status, int... values)// 不包含所有的值
	{
		for (int value : values)
		{
			if (contains(status, value)) return false; // 如果有一个包含，则false
		}
		return true;
	}

	public static boolean without(int status, int value)
	{
		return (status & value) == 0;
	}

	public static boolean containsAll(int status, int... values)// 包含所有的值
	{
		for (int value : values)
		{
			if (without(status, value)) return false; // 如果有一个不包含，则false
		}
		return true;
	}

	public static boolean contains(int status, int value)
	{
		return (status & value) != 0;
	}

}