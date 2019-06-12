package com.georgebindragon.base.system.software;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Set;

/**
 * 创建人：George
 * 类名称：BroadcastReceiverUtil
 * 类概述：
 * 详细描述：
 *
 * 1. 完整解析Broadcast的Intent的内容
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BroadcastReceiverUtil
{
	private static final String TAG = "BroadcastReceiverUtil-->";

	public static void getBroadcastIntentDetail(Intent intent)
	{
		try
		{
			if (EmptyUtil.isEmpty(intent)) return;

			String action = intent.getAction();
			LogProxy.d(TAG, "getBroadcastIntentDetail-->action=" + StringUtil.getPrintString(action));
			if (null == action) return;

			Bundle extras = intent.getExtras();
			if (null == extras) return;

			Set<String> keySet = extras.keySet();  //获取所有的Key
			if (null == keySet) return;

			for (String key : keySet)//bundle.get(key);来获取对应的value
			{
				Object value     = extras.get(key);
				String keyInfo   = "key" + (EmptyUtil.isEmpty(key) ? "" : "(" + key.getClass().getSimpleName() + ")") + "=" + StringUtil.getPrintString(key);
				String valueInfo = "value" + (EmptyUtil.isEmpty(value) ? "" : "(" + value.getClass().getSimpleName() + ")") + "=" + StringUtil.getPrintString(value);

				LogProxy.d(TAG, "getBroadcastIntentDetail-->extras：Bundle：" + keyInfo + "\t\t" + valueInfo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getBroadcastIntentDetail", e);
		}
	}

	public static void registerBroadcastByActionStrings(Context context, String[] actionStrings, BroadcastReceiver receiver)
	{
		registerBroadcastByActionStrings(context, actionStrings, 0, receiver);
	}

	public static void registerBroadcastByActionStrings(Context context, String[] actionStrings, int priority, BroadcastReceiver receiver)
	{
		if (EmptyUtil.notEmpty(context, actionStrings, receiver))
		{
			IntentFilter intentFilter = new IntentFilter();
			for (String action : actionStrings)
			{
				if (EmptyUtil.notEmpty(action)) intentFilter.addAction(action);
			}
			intentFilter.setPriority(priority);//设置 广播接受者 优先级
			context.getApplicationContext().registerReceiver(receiver, intentFilter);
			LogProxy.i(TAG, "registerBroadcastByActionString-->注册广播");
		}
	}

	public static void registerBroadcastByActionString(Context context, String actionString, BroadcastReceiver receiver)
	{
		registerBroadcastByActionString(context, actionString, 0, receiver);
	}

	public static void registerBroadcastByActionString(Context context, String actionString, int priority, BroadcastReceiver receiver)
	{
		if (EmptyUtil.notEmpty(context, actionString, receiver))
		{
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(actionString);
			intentFilter.setPriority(priority);//设置 广播接受者 优先级
			context.getApplicationContext().registerReceiver(receiver, intentFilter);
			LogProxy.i(TAG, "registerBroadcastByActionString-->注册广播");
		}
	}

	public static void unregisterBroadcastReceiver(Context context, BroadcastReceiver receiver)
	{
		if (EmptyUtil.notEmpty(context, receiver))
		{
			try
			{
				context.getApplicationContext().unregisterReceiver(receiver);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "unregisterBroadcastReceiver", e);
			}
		}
	}
}
