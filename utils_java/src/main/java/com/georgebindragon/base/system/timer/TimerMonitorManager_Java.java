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


public class TimerMonitorManager_Java extends BaseListenerMonitorManager<TimerConfig, BaseTimerMonitor, TimerListener>
{
	private static final TimerMonitorManager_Java ourInstance = new TimerMonitorManager_Java();

	public static TimerMonitorManager_Java getInstance() { return ourInstance; }

	private TimerMonitorManager_Java() { }

	@Override
	protected BaseTimerMonitor onMonitorFirstCreate(TimerConfig timerConfig)
	{
		return new JavaTimerMonitor(timerConfig);
	}
}
