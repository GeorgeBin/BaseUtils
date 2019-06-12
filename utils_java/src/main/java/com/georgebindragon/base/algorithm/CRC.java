package com.georgebindragon.base.algorithm;

/**
 * 2017/3/8
 * CRC校验算法,来自于服务器的C代码
 */

public class CRC
{

	private static final int CRC_SEED1 = 2014;

	public static int ZTE_CRC(byte[] data, int len)
	{
		int a = 1;
		int b = 0;
		if (data == null || len == 0)
		{
			return 0;
		}
		for (int index = 0; index < len; index++)
		{
			a = (a + (data[index] & 255)) ^ CRC_SEED1;
			b = (b + a) ^ CRC_SEED1;
		}
		return (b << 16) | a;
	}

	/**
	 * 获取字符数组的crc值
	 *
	 * @param bytes 需要计算crc的字节数组
	 * @return crc值
	 */
	public static int getCRC(byte[] bytes)
	{
		return ZTE_CRC(bytes, bytes.length);
	}
}
