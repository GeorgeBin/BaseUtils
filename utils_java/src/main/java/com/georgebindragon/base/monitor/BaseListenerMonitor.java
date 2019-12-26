package com.georgebindragon.base.monitor;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 创建人：George
 *
 * 描述：单一事件的监控 + 可设置多个监听器
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseListenerMonitor<T> extends BaseMonitor
{
	private Queue<T> listenerList = new ConcurrentLinkedQueue<>();

	protected synchronized void initListenerList()
	{
		if (EmptyUtil.isEmpty(listenerList)) listenerList = new ConcurrentLinkedQueue<>();
	}

	protected Queue<T> getListenerList()
	{
		if (EmptyUtil.isEmpty(listenerList)) initListenerList();
		return listenerList;
	}

	public void addListener(T listener)
	{
		if (EmptyUtil.isEmpty(listener)) return;
		initListenerList();

		if (!listenerList.contains(listener))
		{
			listenerList.offer(listener);
			notifyListenerListChanged();
		}
	}

	public void addListeners(List<T> list)
	{
		if (EmptyUtil.isEmpty(list)) return;
		initListenerList();

		for (T t : list)
		{
			addListener(t);
		}
	}

	public void removeListener(T listener)
	{
		if (EmptyUtil.isEmpty(listenerList)) return;

		boolean remove = listenerList.remove(listener);
		if (remove) notifyListenerListChanged();
	}

	public void clearListeners()
	{
		listenerList = new ConcurrentLinkedQueue<>();
		notifyListenerListChanged();
	}

	public void onThisMonitorRemoved()
	{
		LogProxy.i(TAG, "onThisMonitorRemoved-->");
	}

	protected void notifyListenerListChanged()
	{
		LogProxy.v(TAG, "notifyListenerListChanged, size=" + (null == listenerList ? "0" : listenerList.size()));
	}

	//	通知到监听器的示例

	//	private void notifyListeners(int xxx, String xxxx)
	//	{
	//		final CopyOnWriteArrayList<T> listeners = listenerList;
	//		if (EmptyUtil.notEmpty(listeners))
	//		{
	//			for (T listener : listeners)
	//			{
	//				if (EmptyUtil.notEmpty(listener)) listener.onXXXX(xxx, xxxx);
	//			}
	//		}
	//	}
}
