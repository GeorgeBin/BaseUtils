package com.georgebindragon.base.data.basic;

/**
 * 创建人：George
 * 类名称：FloatUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class FloatUtil
{
	private static final String TAG = "FloatUtil-->";

	public static float getFloat(int molecular)
	{
		return getFloat(molecular, 100);
	}

	/**
	 * @param molecular   分子
	 * @param denominator 分母
	 * @return float值
	 */
	public static float getFloat(int molecular, int denominator)
	{
		if ((float) denominator != 0)
		{
			return (float) molecular / (float) denominator;
		} else
		{
			return 0f;
		}
	}
}