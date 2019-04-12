package com.georgebindragon.base.data.basic;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：NumberUtil
 * 类概述：数字操作类
 * 创建时间：2017-08-28 16:13
 * 详细描述：随机数
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NumberUtil
{
	public static final String TAG = "NumberUtil-->";

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
