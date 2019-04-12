package com.georgebindragon.base.abilities;

import com.georgebindragon.base.abilities.callbacks.ICallBack;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 创建人：George
 * 类名称：CallBacksManager
 * 类概述：
 * 详细描述：
 *
 * 1、注册
 * 2、遍历
 * 3、通知
 * 4、取消
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CallBacksManager<T extends ICallBack>
{
	private static final String TAG = "CallBacksManager-->";

	private Queue<T>                      queue;
	private onCallBackListChangedListener listChangedListener;

	public CallBacksManager()
	{
		queue = new ConcurrentLinkedQueue<>();
	}

	public void registerCallBack(T callback)
	{
		if (null != callback && !queue.contains(callback))
		{
			queue.offer(callback);
			notifyChanged();
		}
	}

	public void unregisterCallBack(T callback)
	{
		if (null != callback)
		{
			boolean remove = queue.remove(callback);
			if (remove) notifyChanged();
		}
	}

	public Queue<T> getCallBacks()
	{
		return queue;
	}

	public void notifyChanged()
	{
		if (null != listChangedListener)listChangedListener.onCallBacksChanged(queue.size());
	}

	public void setOnCallBackListChangedListener(onCallBackListChangedListener listChangedListener)
	{
		this.listChangedListener = listChangedListener;
	}

	public interface onCallBackListChangedListener
	{
		void onCallBacksChanged(int size);
	}
}
