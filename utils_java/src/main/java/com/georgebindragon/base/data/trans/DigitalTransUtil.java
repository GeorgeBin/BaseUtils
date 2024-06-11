package com.georgebindragon.base.data.trans;

import com.georgebindragon.base.data.basic.BytesUtil;

/**
 * 2017/3/7.
 */

public class DigitalTransUtil
{

	// 二进制转~

	/**
	 * 二进制字符串转十进制
	 *
	 * @param binary 二进制字符串
	 * @return 十进制数值
	 */
	public static int binaryToAlgorism(String binary)
	{
		int max    = binary.length();
		int result = 0;
		for (int i = max; i > 0; i--)
		{
			char c        = binary.charAt(i - 1);
			int  algorism = c - '0';
			result += Math.pow(2, max - i) * algorism;
		}
		return result;
	}

	// 十进制转~

	/**
	 * 将十进制转换为指定长度的十六进制字符串
	 *
	 * @param algorism  int 十进制数字
	 * @param maxLength int 转换后的十六进制字符串长度
	 * @return String 转换后的十六进制字符串
	 */
	public static String algorismToHEXString(int algorism, int maxLength)
	{
		String result = "";
		result = Integer.toHexString(algorism);

		if (result.length() % 2 == 1)
		{
			result = "0" + result;
		}
		return patchHexString(result.toUpperCase(), maxLength);
	}

	// 自己写的算法-->需要校验
	public static byte[] algorismToBytes(int algorism, int maxLength)
	{
		return intToBytes(algorism, maxLength);
	}

	public static byte[] intToBytes(int number, int maxLength)
	{
		if (maxLength > 0 && maxLength <= 4)
		{
			byte[] result = new byte[maxLength];

			for (int i = maxLength - 1, j = 0; i >= 0; i--)
			{
				result[i] = (byte) ((number >> (8 * j)) & 0xFF);
				j++;
			}
			return result;
		} else if (maxLength > 4)
		{
			byte[] result1 = new byte[maxLength - 4];//用于补0
			byte[] result2 = new byte[4];

			for (int i = 4 - 1, j = 0; i >= 0; i--)
			{
				result2[i] = (byte) ((number >> (8 * j)) & 0xFF);
				j++;
			}
			return BytesUtil.addBytes(result1, result2);
		}

		return null;
	}

	// 十六进制转~

	/**
	 * 十六进制转字符串
	 *
	 * @param hexString  十六进制字符串
	 * @param encodeType 编码类型4：Unicode，2：普通编码
	 * @return 字符串
	 */
	public static String hexStringToString(String hexString, int encodeType)
	{
		String result = "";
		int    max    = hexString.length() / encodeType;
		for (int i = 0; i < max; i++)
		{
			char c = (char) DigitalTransUtil.hexStringToAlgorism(hexString.substring(i * encodeType, (i + 1) * encodeType));
			result += c;
		}
		return result;
	}

	/**
	 * 十六进制字符串装十进制
	 *
	 * @param hex 十六进制字符串
	 * @return 十进制数值
	 */
	public static int hexStringToAlgorism(String hex)
	{
		hex = hex.toUpperCase();
		int nResult = 0;
		if (!IsHex(hex))
			return nResult;
		String str = hex.toUpperCase();
		if (str.length() > 2)
		{
			if (str.charAt(0) == '0' && str.charAt(1) == 'X')
			{
				str = str.substring(2);
			}
		}
		int max = str.length();
		for (int i = max; i > 0; i--)
		{
			char c = str.charAt(i - 1);
			int  algorism;
			if (c >= '0' && c <= '9')
			{
				algorism = c - '0';
			} else
			{
				algorism = c - 55;
			}
			nResult += (int) Math.pow(16, max - i) * algorism;//特别注意,要先转int,再相乘
		}
		return nResult;
	}

	/**
	 * 十六转二进制
	 *
	 * @param hex 十六进制字符串
	 * @return 二进制字符串
	 */
	public static String hexStringToBinary(String hex)
	{
		hex = hex.toUpperCase();
		String result = "";
		int    max    = hex.length();
		for (int i = 0; i < max; i++)
		{
			char c = hex.charAt(i);
			switch (c)
			{
				case '0':
					result += "0000";
					break;
				case '1':
					result += "0001";
					break;
				case '2':
					result += "0010";
					break;
				case '3':
					result += "0011";
					break;
				case '4':
					result += "0100";
					break;
				case '5':
					result += "0101";
					break;
				case '6':
					result += "0110";
					break;
				case '7':
					result += "0111";
					break;
				case '8':
					result += "1000";
					break;
				case '9':
					result += "1001";
					break;
				case 'A':
					result += "1010";
					break;
				case 'B':
					result += "1011";
					break;
				case 'C':
					result += "1100";
					break;
				case 'D':
					result += "1101";
					break;
				case 'E':
					result += "1110";
					break;
				case 'F':
					result += "1111";
					break;
			}
		}
		return result;
	}
	// 字节组转~

	/**
	 * 字节数组转为普通字符串（ASCII对应的字符）
	 *
	 * @param bytearray byte[]
	 * @return String
	 */
	public static String bytetoString(byte[] bytearray)
	{
		String result = "";
		char   temp;

		int length = bytearray.length;
		for (int i = 0; i < length; i++)
		{
			temp = (char) bytearray[i];
			result += temp;
		}
		return result;
	}
	// 数字字符串转~

	/**
	 * 数字字符串转ASCII码字符串
	 *
	 * @param content String
	 *                字符串
	 * @return ASCII字符串
	 */
	public static String StringToAsciiString(String content)
	{
		String result = "";
		int    max    = content.length();
		for (int i = 0; i < max; i++)
		{
			char   c = content.charAt(i);
			String b = Integer.toHexString(c);
			result = result + b;
		}
		return result;
	}

	// ASCII码字符串~

	/**
	 * ASCII码字符串转数字字符串
	 *
	 * @param content String
	 * @return 字符串
	 */
	public static String AsciiStringToString(String content)
	{
		String result = "";
		int    length = content.length() / 2;
		for (int i = 0; i < length; i++)
		{
			String c = content.substring(i * 2, i * 2 + 2);
			int    a = hexStringToAlgorism(c);
			char   b = (char) a;
			String d = String.valueOf(b);
			result += d;
		}
		return result;
	}


	/**
	 * 十进制-数字，转换为：十六进制-字符串
	 *
	 * @param algorithm int 十进制的数字
	 * @return String 对应的十六进制字符串
	 */
	public static String algorismToHEXString(int algorithm)
	{
		String result = "";
		result = Integer.toHexString(algorithm);

		if (result.length() % 2 == 1)
		{
			result = "0" + result;

		}
		result = result.toUpperCase();

		return result;
	}

	/**
	 * HEX字符串前补0，主要用于长度位数不足。
	 *
	 * @param str       String 需要补充长度的十六进制字符串
	 * @param maxLength int 补充后十六进制字符串的长度
	 * @return 补充结果
	 */
	static public String patchHexString(String str, int maxLength)
	{
		String temp = "";
		for (int i = 0; i < maxLength - str.length(); i++)
		{
			temp = "0" + temp;
		}
		str = (temp + str).substring(0, maxLength);
		return str;
	}

	/**
	 * 将一个字符串转换为int
	 *
	 * @param s          String 要转换的字符串
	 * @param defaultInt int 如果出现异常,默认返回的数字
	 * @param radix      int 要转换的字符串是什么进制的,如16 8 10.
	 * @return int 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt, int radix)
	{
		int i = 0;
		try
		{
			i = Integer.parseInt(s, radix);
		} catch (NumberFormatException ex)
		{
			i = defaultInt;
		}
		return i;
	}

	/**
	 * 将一个十进制形式的数字字符串转换为int
	 *
	 * @param s          String 要转换的字符串
	 * @param defaultInt int 如果出现异常,默认返回的数字
	 * @return int 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt)
	{
		int i = 0;
		try
		{
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex)
		{
			i = defaultInt;
		}
		return i;
	}

	/**
	 * 十六进制字符串转为Byte数组,每两个十六进制字符转为一个Byte
	 *
	 * @param hex 十六进制字符串
	 * @return byte 转换结果
	 */
	public static byte[] hexStringToByte(String hex)
	{
		int    max     = hex.length() / 2;
		byte[] bytes   = new byte[max];
		String binarys = DigitalTransUtil.hexStringToBinary(hex);
		for (int i = 0; i < max; i++)
		{
			bytes[i] = (byte) DigitalTransUtil.binaryToAlgorism(binarys.substring(i * 8 + 1, (i + 1) * 8));
			if (binarys.charAt(8 * i) == '1')
			{
				bytes[i] = (byte) (0 - bytes[i]);
			}
		}
		return bytes;
	}

	/**
	 * 十六进制串转化为byte数组
	 *
	 * @param hex 十六进制串
	 * @return byte数组
	 * @throws IllegalArgumentException 解析错误
	 */
	public static final byte[] hex2byte(String hex) throws IllegalArgumentException
	{
		if (hex.length() % 2 != 0)
		{
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b   = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++)
		{
			String swap    = "" + arr[i++] + arr[i];
			int    byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = Integer.valueOf(byteint).byteValue();
		}
		return b;
	}

	/**
	 * 字节-数组，转换为：十六进制字符串
	 *
	 * @param b byte[] 需要转换的字节数组
	 * @return String 十六进制字符串
	 */
	public static final String byte2hex(byte b[])
	{
		if (b == null)
		{
			throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
		}
		String hs   = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			} else
			{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	//判断是否是16进制数
	public static boolean IsHex(String strHex)
	{
		int i = 0;
		if (strHex.length() > 2)
		{
			if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x'))
			{
				i = 2;
			}
		}
		for (; i < strHex.length(); ++i)
		{
			char ch = strHex.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))
				continue;
			return false;
		}
		return true;
	}
}
