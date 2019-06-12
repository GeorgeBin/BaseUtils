package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：BroadcastUtil
 * 类概述：广播相关工具
 * 详细描述：发送广播、
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BroadcastUtil
{
	public static final String TAG = "BroadcastUtil-->";

	public static void sendBroadcast(Intent intent)
	{
		Context utilContext = BaseUtils.getContext();
		sendBroadcast(utilContext, intent);
	}

	public static void sendBroadcast(Context context, Intent intent)
	{
		if (EmptyUtil.notEmpty(BaseUtils.getContext(), intent)) context.sendBroadcast(intent);
	}
}
