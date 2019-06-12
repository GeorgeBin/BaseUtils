package com.georgebindragon.base.data.basic;

/**
 * 创建人：George
 * 类名称：NumberUtil
 * 类概述：数字操作类
 * 详细描述：随机数
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NumberUtil
{
	private static final String TAG = "NumberUtil-->";

	public static double getRandom(int max)
	{
		if (max > 0)
		{
			return (Math.random() * max) + 1;
		} else
		{
			return Math.random();
		}
	}
}
