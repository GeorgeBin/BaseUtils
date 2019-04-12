package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

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
}
