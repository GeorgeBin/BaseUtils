package com.georgebindragon.base.debug.receiver;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.abilities.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.receiver.BaseBroadcastReceiver;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：DebugReceiver
 * 类概述：用于接收debug的广播, 从而触发一些debug的功能
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DebugReceiver extends BaseBroadcastReceiver<IBaseReceiverCallBack>
{

	private static final DebugReceiver ourInstance = new DebugReceiver();

	static DebugReceiver getInstance() { return ourInstance; }

	//接收到 启动广播
	@Override
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		BroadcastReceiverUtil.getBroadcastIntentDetail(intent);
		if (EmptyUtil.notEmpty(receiverCallBack)) receiverCallBack.onReceive(context, intent);
	}
}
