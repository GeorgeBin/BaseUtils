package com.georgebindragon.base.data.basic;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ShortUtil
{
	private static final String TAG = "ShortUtil-->";

	public static short getShort(byte data1, byte data2)
	{
		return (short) ((data1 & 0xFF) | (data2 << 8));
	}
}