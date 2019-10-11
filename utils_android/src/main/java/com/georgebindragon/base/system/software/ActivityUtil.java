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
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					context.startActivity(intent);
					return true;
				} catch (Exception e) { LogProxy.e(TAG, "jumpActivity", e); }
			}
		}
		return false;
	}
}
