package com.georgebindragon.base.monitor;

/**
 * 创建人：George
 * 类名称：BaseCallBackMonitor
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseCallBackMonitor<T> extends BaseMonitor
{
	protected T callback = null;

	public void setCallBack(T callBack)
	{
		this.callback = callBack;
	}

	public void cancelCallBack()
	{
		this.callback = null;
	}

	//	private void onCallBack(int xxx, String xxxx)
	//	{
	//		if (EmptyUtil.notEmpty(callback))
	//		{
	//			callback.onXXXX(xxx, xxxx);
	//		}
	//	}
}
