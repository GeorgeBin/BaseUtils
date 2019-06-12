package com.georgebindragon.base.data.math;

import java.math.BigDecimal;

/**
 * 创建人：George
 * 类名称：DoubleUtil
 * 类概述：Double 不丢失经度的计算
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class DoubleUtil
{
	// 进行加法运算
	public static double add(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	// 进行减法运算
	public static double subtract(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	// 进行乘法运算
	public static double multiply(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	// 进行除法运算
	public static double divide(double d1, double d2, int len)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	// 进行四舍五入操作
	public static double round(double d, int len)
	{
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
