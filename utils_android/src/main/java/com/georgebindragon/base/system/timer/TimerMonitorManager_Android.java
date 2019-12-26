package com.georgebindragon.base.system.timer;

import com.georgebindragon.base.monitor.BaseListenerMonitorManager;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class TimerMonitorManager_Android extends BaseListenerMonitorManager<TimerConfig, BaseTimerMonitor, TimerListener>
{
	private static final TimerMonitorManager_Android ourInstance = new TimerMonitorManager_Android();

	public static TimerMonitorManager_Android getInstance() { return ourInstance; }

	private TimerMonitorManager_Android() { }

	@Override
	protected BaseTimerMonitor onMonitorFirstCreate(TimerConfig timerConfig)
	{
		return new HandlerTimerMonitor(timerConfig);
	}
}
