package com.georgebindragon.base.system.software;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：SystemTimeUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SystemTimeUtil
{
	public static boolean setTimeZone(Context context, String timeZone)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		// 需要先确认权限
		if (EmptyUtil.notEmpty(context, timeZone) && PermissionUtil.checkPermission(context, Manifest.permission.SET_TIME_ZONE))
		{
			AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			if (EmptyUtil.notEmpty(alarmManager))
			{
				alarmManager.setTimeZone(timeZone);
				return true;
			}
		}
		return false;
	}
}
