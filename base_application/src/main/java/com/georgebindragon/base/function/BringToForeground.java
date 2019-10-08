package com.georgebindragon.base.function;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
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

	public void listenToClickBroadcast()
	{
		LogProxy.i(TAG, "listenToClickBroadcast-->Broadcast=" + StringUtil.getPrintString(getClickBroadcast()));

		UtilsActions.getInstance().listenSomeKey(getClickBroadcast(), this);
	}

	abstract String getClickBroadcast();

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
			if (null != action && action.length() > 0 && action.equalsIgnoreCase(getClickBroadcast())) appBackToForeground(BaseUtils.getContext());
		}
	}

	protected void appBackToForeground(Context context)
	{
		if (null == context) context = BaseUtils.getContext();
		try
		{
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
			context.startActivity(intent);

			LogProxy.i(TAG, "appBackToForeground-->intent=" + StringUtil.getPrintString(intent));
		} catch (Exception e)
		{
			LogProxy.e(TAG, "appBackToForeground", e);
		}
	}
}
