package com.georgebindragon.base.monitor;

/**
 * 创建人：George
 *
 * 描述：单一事件的监控 + 只能设置一个监听器
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

	//	通知到监听器的示例

	//	private void onCallBack(int xxx, String xxxx)
	//	{
	//		if (EmptyUtil.notEmpty(callback))
	//		{
	//			callback.onXXXX(xxx, xxxx);
	//		}
	//	}
}
