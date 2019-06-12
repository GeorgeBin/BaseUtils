package com.georgebindragon.base.data.basic;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 2017/3/9
 * 二进制数组操作：复制，截取，拼接
 */

public class BytesUtil
{
	private static final String TAG = "BytesUtil-->";

	/**
	 * 截取二进制数组的一部分(截取即为新数组)
	 *
	 * @param srcBytes   数据来源
	 * @param srcStart   从哪里开始截取(包含)
	 * @param dataLength 要写入的长度
	 * @return 目的地数据
	 */
	public static byte[] subBytes(byte[] srcBytes, int srcStart, int dataLength)
	{
		return subBytes(srcBytes, srcStart, 0, dataLength);
	}

	/**
	 * 截取二进制数组的一部分(截取后数据,可以选择在新数组开始的位置)
	 *
	 * @param srcBytes   数据来源
	 * @param srcStart   从哪里开始截取(包含)
	 * @param destStart  从哪里开始写入
	 * @param dataLength 要写入的长度
	 * @return 目的地数据
	 */
	public static byte[] subBytes(byte[] srcBytes, int srcStart, int destStart, int dataLength)
	{
		if (EmptyUtil.isEmpty(srcBytes) || srcStart < 0 || destStart < 0 || dataLength <= 0)
		{
			LogProxy.e(TAG, "subBytes()，参数不合格",
					"srcBytes" + (EmptyUtil.isEmpty(srcBytes)?"=NULL":"长度="+srcBytes.length),"srcStart=" + srcStart, "destStart=" + destStart, "dataLength=" + dataLength);
			return null;
		}
		int srcLength = srcBytes.length;
		int srcSubLength = srcLength - srcStart;
		int destLength   = dataLength + destStart;

		LogProxy.d(TAG, "subBytes()-->",
				"srcBytes.length=" + srcLength, "srcStart=" + srcStart, "destStart=" + destStart, "dataLength=" + dataLength,
				"要截取的长度 srcSubLength=" + srcSubLength, "截取后的目标长度 destLength=" + destLength);

		//要截取的长度不合格, 或者 截取后的目标长度不合格
		if (srcLength <= 0 || srcSubLength <= 0 || srcSubLength > 10 * 1024 * 1024 || destLength > 10 * 1024 * 1024)
		{
			LogProxy.e(TAG, "subBytes()，要截取的长度不合格 || 截取后的目标长度不合格");
			return null;
		} else if (dataLength > srcSubLength)//要拷贝的长度, 大于 (数据实际长度-起始位置)=要拷贝的数据长度
		{
			LogProxy.e(TAG, "subBytes()，要拷贝的长度 > 要拷贝的数据长度");
			return copyBytes(srcBytes, srcStart, new byte[destStart + srcSubLength], destStart, dataLength);
		}
		return copyBytes(srcBytes, srcStart, new byte[destLength], destStart, dataLength);
	}

	/**
	 * @param byteList 不定参数，依次传入要拼接的二进制数组
	 * @return 拼接后的新二进制数组
	 */
	public static byte[] addBytes(byte[]... byteList)
	{
		byte[] result = null;
		if (byteList.length > 0)
		{
			int length = 0;
			for (byte[] bytes : byteList)
			{
				if (null != bytes)
				{
					length += bytes.length;
				}
			}
			result = new byte[length];
			int startLength = 0;
			for (byte[] bytes : byteList)
			{
				if (null != bytes)
				{
					copyBytes(bytes, 0, result, startLength, bytes.length);
					startLength += bytes.length;
				}
			}
		} else
		{
			LogProxy.e("addBytes", "未正常传入数据！");
		}
		return result;
	}

	public static byte[] copyBytes(byte[] srcBytes)
	{
		if (null == srcBytes || srcBytes.length <= 0) return null;
		return copyBytes(srcBytes, 0, new byte[srcBytes.length], 0, srcBytes.length);
	}

	public static byte[] copyBytes(byte[] srcBytes, int srcStart, int destStart, int dataLength)
	{
		if (null == srcBytes || srcBytes.length <= 0) return null;
		return copyBytes(srcBytes, srcStart, new byte[dataLength], destStart, dataLength);
	}

	/**
	 * 参数说明，参考{@link System#arraycopy}
	 *
	 * @param srcBytes   数据来源
	 * @param srcStart   从哪里开始截取(包含)
	 * @param destBytes  目的地数据
	 * @param destStart  从哪里开始写入
	 * @param dataLength 要写入的长度
	 * @return 目的地数据
	 */
	private static byte[] copyBytes(byte[] srcBytes, int srcStart, byte[] destBytes, int destStart, int dataLength)
	{
		if (null == srcBytes || null == destBytes || srcStart < 0 || destStart < 0 || dataLength < 0)
		{
			LogProxy.e("copyBytes", "数据传入错误");
			return null;
		} else
		{
			try
			{
				System.arraycopy(srcBytes, srcStart, destBytes, destStart, dataLength);
			} catch (Exception e)
			{
				LogProxy.e("copyBytes", "数据截取错误" + e.getMessage());
				return null;
			}
		}
		return destBytes;
	}
}
