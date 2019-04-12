package com.georgebindragon.base.test.receiver;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.abilities.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.receiver.BaseBroadcastReceiver;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
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
public class TestReceiver extends BaseBroadcastReceiver<IBaseReceiverCallBack>
{

	private static final TestReceiver ourInstance = new TestReceiver();

	static TestReceiver getInstance() { return ourInstance; }

	//接收到 启动广播
	@Override
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		BroadcastReceiverUtil.getBroadcastIntentDetail(intent);
		if (EmptyUtil.notEmpty(receiverCallBack)) receiverCallBack.onReceive(context, intent);
	}
}
