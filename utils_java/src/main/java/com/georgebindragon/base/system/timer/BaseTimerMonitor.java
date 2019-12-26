package com.georgebindragon.base.system.timer;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Queue;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseTimerMonitor extends BaseListenerMonitor<TimerListener>
{
	protected String TAG = "BaseTimerMonitor: " + getClass().getSimpleName() + "-->";

	protected boolean needRepeat;
	protected long    repeatTimeMillis;

	public BaseTimerMonitor(TimerConfig timerConfig)
	{
		if (null != timerConfig)
		{
			this.needRepeat = timerConfig.isNeedRepeat();
			this.repeatTimeMillis = timerConfig.getRepeatTimeMillis();
		}
		LogProxy.i(TAG, "构造函数-->timerConfig=" + StringUtil.getPrintString(timerConfig));
	}

	protected void onTimerTrigger()
	{
		LogProxy.i(TAG, "onTimerTrigger-->");
		notifyListeners();
	}

	private void notifyListeners()
	{
		final Queue<TimerListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (TimerListener listener : listeners) if (EmptyUtil.notEmpty(listener)) listener.onTimer();
		}
	}

	@Override
	public void addListener(TimerListener listener)
	{
		super.addListener(listener);
		startTimer();
	}

	@Override
	protected void notifyListenerListChanged()
	{
		super.notifyListenerListChanged();
		if (null != getListenerList() && getListenerList().size() <= 0)
		{
			stopTimer();
		}
	}

	@Override
	public void onThisMonitorRemoved()
	{
		super.onThisMonitorRemoved();
		stopTimer();
	}

	protected abstract void startTimer();

	protected abstract void stopTimer();

}
