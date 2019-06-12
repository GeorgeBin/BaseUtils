package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 2017/5/3
 */

public class PermissionUtil
{
	public static boolean checkPermission(Context context, String permission)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//>=23
		{
			int checkSelfPermission = context.checkSelfPermission(permission);
			return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
		} else
		{
			return true;
		}
	}

	public static boolean checkPermission(Context context, String[] permissions)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//>=23
		{
			for (String p : permissions)
			{
				if (EmptyUtil.notEmpty(p))
				{
					boolean have = checkPermission(context, p);
					if (!have) return false;
				}
			}
		}
		return true;
	}
}
