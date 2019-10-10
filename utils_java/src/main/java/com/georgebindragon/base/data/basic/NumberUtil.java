package com.georgebindragon.base.data.basic;

import com.georgebindragon.base.function.log.LogProxy;

import java.util.Random;

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

	public static int getRandom(int max, int min)
	{
		try
		{
			if (max < min)
			{
				int temp = max;
				max = min;
				min = temp;
			}
			Random rand = new Random();
			//将被赋值为一个 MIN 和 MAX 范围内的随机数
			return rand.nextInt(max - min + 1) + min;
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getRandom", e);
		}
		return max;
	}
}
