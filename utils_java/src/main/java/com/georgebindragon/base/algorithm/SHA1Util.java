package com.georgebindragon.base.algorithm;

import com.georgebindragon.base.function.log.LogProxy;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * 创建人：George
 * 类名称：SHA1Util
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SHA1Util
{
	private static final String TAG = "SHA1Util-->";

	public static String getSHA1(byte[] signature)
	{
		try
		{
			MessageDigest md        = MessageDigest.getInstance("SHA1");
			byte[]        publicKey = md.digest(signature);
			StringBuilder hexString = new StringBuilder();
			for (byte aPublicKey : publicKey)
			{
				String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase(Locale.US);
				if (appendString.length() == 1) hexString.append("0");
				hexString.append(appendString);
				hexString.append(":");
			}
			String result = hexString.toString();
			return result.substring(0, result.length() - 1);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getSHA1", e);
		}
		return null;
	}
}