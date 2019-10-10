package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：AppInfoUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AppInfoUtil
{
	private static final String TAG = "AppInfoUtil-->";

	public static String getSignatureString(Context context)
	{
		try
		{
			PackageInfo   packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[]   signatures  = packageInfo.signatures;
			StringBuilder builder     = new StringBuilder();
			for (Signature signature : signatures)
			{
				builder.append(signature.toCharsString());
			}
			return builder.toString();
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getSign", e);
		}
		return "";
	}
}