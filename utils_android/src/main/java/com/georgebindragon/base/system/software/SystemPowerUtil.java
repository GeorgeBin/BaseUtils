package com.georgebindragon.base.system.software;

import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：SystemPowerUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SystemPowerUtil
{
	private static final String TAG = "SystemPowerUtil-->";

	//  <uses-permission android:name="android.permission.SHUTDOWN" tools:ignore="ProtectedPermissions" /><!-- 系统权限：关机、重启 -->

	// 实测有效，有正常的UI界面
	public static void rebootByAndroid() { rebootByAndroid(true); }

	public static void rebootByAndroid(boolean showDialog)
	{
		Intent intent = new Intent(Intent.ACTION_REBOOT);
		intent.putExtra("nowait", 1);// 立即重启：1
		intent.putExtra("interval", 1);// 重启次数：1
		intent.putExtra("window", showDialog ? 0 : 1);// 不出现弹窗：0=出现
		ActivityUtil.jumpActivity(BaseUtils.getContext(), intent);
	}

	public static void shutdownByAndroid()
	{
		shutdownByAndroid(false);
	}

	public static void shutdownByAndroid(boolean showConfirmDialog)
	{
		Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");

		intent.putExtra("android.intent.extra.KEY_CONFIRM", showConfirmDialog);// 是否弹出提示框
		ActivityUtil.jumpActivity(BaseUtils.getContext(), intent);
	}

	// <uses-permission android:name="android.permission.MASTER_CLEAR" tools:ignore="ProtectedPermissions" /> <!-- 系统权限：恢复出厂设置 -->
	public static void resetDevice()
	{
		LogProxy.i(TAG, "resetDevice-->");
		BroadcastUtil.sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
		// 系统会重启，在启动时，恢复出厂设置
	}

}