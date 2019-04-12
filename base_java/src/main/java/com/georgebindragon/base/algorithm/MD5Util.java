package com.georgebindragon.base.algorithm;

import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：MD5Util
 * 类概述：MD5计算工具
 * 创建时间：2017-07-11 09:37
 * 详细描述：计算文件、字符串等MD5值
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MD5Util
{
	public static final  String TAG                  = "MD5Util-->";
	/**
	 * MS5
	 */
	private static final int    STREAM_BUFFER_LENGTH = 1024;

	public static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException
	{
		return MessageDigest.getInstance(algorithm);
	}

	public static byte[] md5(String txt)
	{
		return md5(txt.getBytes());
	}

	public static byte[] md5(byte[] bytes)
	{
		try
		{
			MessageDigest digest = getDigest("MD5");
			digest.update(bytes);
			return digest.digest();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] md5(InputStream is) throws NoSuchAlgorithmException, IOException
	{
		return updateDigest(getDigest("MD5"), is).digest();
	}

	public static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws IOException
	{
		final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
		int          read   = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

		while (read > -1)
		{
			digest.update(buffer, 0, read);
			read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
		}

		return digest;
	}

	public static String getFileMd5(String file)
	{
		if (EmptyUtil.notEmpty(file))
		{
			return getFileMd5(new File(file));
		} else
		{
			return null;
		}
	}

	/**
	 * RandomAccessFile 获取文件的MD5值
	 *
	 * @param file 文件路径
	 * @return md5
	 */
	public static String getFileMd5(File file)
	{
		MessageDigest    messageDigest;
		RandomAccessFile randomAccessFile = null;
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			if (file == null)
			{
				return "";
			}
			if (!file.exists())
			{
				return "";
			}
			randomAccessFile = new RandomAccessFile(file, "r");
			byte[] bytes = new byte[1024 * 1024 * 10];
			int    len   = 0;
			while ((len = randomAccessFile.read(bytes)) != -1)
			{
				messageDigest.update(bytes, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, messageDigest.digest());
			String     md5    = bigInt.toString(16);
			while (md5.length() < 32)
			{
				md5 = "0" + md5;
			}
			return md5;
		} catch (NoSuchAlgorithmException | IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (randomAccessFile != null)
				{
					randomAccessFile.close();
					randomAccessFile = null;
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return "";
	}
}
