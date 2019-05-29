package com.georgebindragon.base.receiver;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.base.receiver.BaseBaseBroadcastReceiver;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.system.software.LanguageUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：PowerSwitchReceiver
 * 类概述：组别旋钮广播
 * 创建时间：2017-06-22 11:42
 * 监听广播：
 * 各种需要测试的广播
 * 需要权限：无
 * 详细描述：测试用
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UtilsReceiver extends BaseBaseBroadcastReceiver
{
	private static final String[] utilsActions =
			{
					Intent.ACTION_LOCALE_CHANGED,
			};

	private static final UtilsReceiver ourInstance = new UtilsReceiver();

	public static UtilsReceiver getInstance() { return ourInstance; }

	public void registerUtilsBroadcast(Context context)
	{
		BroadcastReceiverUtil.registerBroadcastByActionStrings(context, utilsActions, UtilsReceiver.getInstance());
		LogProxy.i(TAG, "registerUtilsBroadcast-->注册 Utils使用的 广播接收者");
	}

	public void unregisterUtilsBroadcast(Context context)
	{
		BroadcastReceiverUtil.unregisterBroadcastReceiver(context, UtilsReceiver.getInstance());
		LogProxy.i(TAG, "unregisterTestBroadcast-->注销 Utils使用的 广播接收者");
	}

	//接收到广播
	@Override
	protected void onBroadcastReceived(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (EmptyUtil.notEmpty(action))
		{
			switch (action)
			{
				case Intent.ACTION_LOCALE_CHANGED:
					LanguageUtil.onSystemLocalChanged(context, intent);
					break;
				default:
					break;
			}
		}
	}
}
