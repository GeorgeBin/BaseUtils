package com.georgebindragon.base.system.hardware.media;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PCMUtil
{
	private static final String TAG = "PCMUtil-->";

	public static final double GAIN_Max = 10000;
	public static final double GAIN_Min = 1.0E-4;

	private static final int max = Short.MAX_VALUE;
	private static final int min = Short.MIN_VALUE;

	/**
	 * @param seed 取值在 -80 ~ 80 之间，表示增大多少db
	 * @return 需要设置的增益
	 */
	public static double getGain(double seed)
	{
		double result = Math.pow(10, seed / 20);
		if (result > GAIN_Max) result = GAIN_Max;
		if (result < GAIN_Min) result = GAIN_Min;
		return result;
	}

	public static byte[] pcm16BitGain(byte[] pcm, double multiple)
	{
		if (null == pcm || pcm.length <= 0) return null;
		LogProxy.i(TAG, "pcm16BitGain-->pcm.length=" + pcm.length, "multiple=" + multiple);

		int    index     = 0;
		int    pcmLength = pcm.length;
		byte[] result    = new byte[pcmLength];
		double temp;
		while (index < pcmLength)
		{
			temp = getShort(pcm, index) * multiple;
			if (temp > max)
			{
				temp = max;
			} else if (temp < min)
			{
				temp = min;
			}

			result[index] = (byte) ((short) temp & 0xFF);
			result[index + 1] = (byte) (((short) temp >> 8) & 0xFF);

			index += 2;
		}
		return result;
	}

	public static short getShort(byte[] data, int index)
	{
		return (short) ((data[index] & 0xFF) | (data[index + 1] << 8));
	}

}