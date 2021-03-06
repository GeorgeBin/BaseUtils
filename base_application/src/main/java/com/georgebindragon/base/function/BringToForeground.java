package com.georgebindragon.base.function;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.app.application.ActivitiesManager;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.system.software.ActivityUtil;
import com.georgebindragon.base.system.software.AppUtil;
import com.georgebindragon.base.system.software.WindowsUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 * 类名称：BringToForeground
 *
 * 描述：点击通知栏常驻前台通知-->发出广播-->收到广播-->回到前台
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BringToForeground implements IBaseReceiverCallBack
{
	protected String TAG = "BringToForeground: " + getClass().getSimpleName() + "-->";

	private static final String ClickBroadcast_PackageName = "Broadcast_" + AppUtil.getPackageName() + "_NotificationClick";

	private static String ClickBroadcast = null;

	public void listenToClickBroadcast()
	{
		LogProxy.i(TAG, "listenToClickBroadcast-->Broadcast=" + StringUtil.getPrintString(getClickBroadcast()));

		UtilsActions.getInstance().listenSomeKey(getClickBroadcast(), this);
	}

	protected abstract String getClickBroadcastString();

	private String getClickBroadcast()
	{
		if (EmptyUtil.isEmpty(ClickBroadcast))
		{
			String clickBroadcastString = getClickBroadcastString();
			ClickBroadcast = EmptyUtil.notEmpty(clickBroadcastString) ? clickBroadcastString : ClickBroadcast_PackageName;
		}

		return ClickBroadcast;
	}

	public PendingIntent getBroadcastPendingIntent()
	{
		return getBroadcastPendingIntent(null, null);
	}

	public PendingIntent getBroadcastPendingIntent(Context context, Intent intent)
	{
		if (null == context) context = BaseUtils.getContext();
		if (null == intent) intent = new Intent();

		intent.setAction(getClickBroadcast());
		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		LogProxy.i(TAG, "onReceive-->context=" + context, "intent=" + StringUtil.getPrintString(intent));
		if (null != intent)
		{
			String action = intent.getAction();
			if (null != action && action.length() > 0 && action.equalsIgnoreCase(getClickBroadcast()))
			{
				bringMyselfBackToForeground(context);
			}
		}
	}

	protected void bringMyselfBackToForeground(Context context)
	{
		Activity currentActivity = ActivitiesManager.getInstance().getCurrentActivity();
		if (null != currentActivity)
		{
			int taskId = currentActivity.getTaskId();
			WindowsUtil.moveAppToForeground(context, taskId);
		} else
		{
			ActivityUtil.launchApp(context, context.getPackageName());
		}
	}
}
