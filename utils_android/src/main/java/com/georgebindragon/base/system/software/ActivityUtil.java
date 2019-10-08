package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 * 类名称：ActivityUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ActivityUtil
{
	private static final String TAG = "ActivityUtil-->";

	public static boolean jumpActivity(Context context, Intent intent)
	{
		LogProxy.i(TAG, "jumpActivity-->intent=" + StringUtil.getPrintString(intent));

		if (null == context) context = BaseUtils.getContext();

		if (EmptyUtil.notEmpty(context, intent))
		{
			if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
			{
				try
				{
					context.startActivity(intent);
					return true;
				} catch (Exception e) { LogProxy.e(TAG, "jumpActivity", e); }
			}
		}
		return false;
	}

	public static boolean bringMyselfBackToForeground(Context context)
	{
		LogProxy.i(TAG, "bringMyselfBackToForeground-->");

		if (null == context) context = BaseUtils.getContext();
		if (EmptyUtil.notEmpty(context))
		{
			return bringAppBackToForeground(context, context.getPackageName());
		}
		return false;
	}

	public static boolean bringAppBackToForeground(Context context, String packageName)
	{
		if (null == context) context = BaseUtils.getContext();
		try
		{
			if (EmptyUtil.notEmpty(context, packageName))
			{
				Intent  intent  = context.getPackageManager().getLaunchIntentForPackage(packageName);
				boolean success = jumpActivity(context, intent);
				LogProxy.i(TAG, "bringAppBackToForeground-->跳转=" + (success ? "成功" : "失败"));
				return success;
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "bringAppBackToForeground", e);
		}
		return false;
	}
}
