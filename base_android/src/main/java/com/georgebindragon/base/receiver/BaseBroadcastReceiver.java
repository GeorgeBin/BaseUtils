package com.georgebindragon.base.receiver;

/**
 * 创建人：George
 * 类名称：BaseBroadcastReceiver
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseBroadcastReceiver<T> extends BaseBaseBroadcastReceiver
{

	protected T receiverCallBack;

	public void setReceiverCallBack(T receiverCallBack)
	{
		this.receiverCallBack = receiverCallBack;
	}
}
