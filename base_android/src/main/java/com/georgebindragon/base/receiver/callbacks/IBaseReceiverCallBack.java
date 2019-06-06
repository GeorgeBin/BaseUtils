package com.georgebindragon.base.receiver.callbacks;

import android.content.Context;
import android.content.Intent;

/**
 * 创建人：George
 * 类名称：IBaseReceiverCallBack
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IBaseReceiverCallBack extends IReceiverCallBacks
{
	void onReceive(Context context, Intent intent);
}
