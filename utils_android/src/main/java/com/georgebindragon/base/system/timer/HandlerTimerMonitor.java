package com.georgebindragon.base.system.timer;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.thread.DebounceHandler;
import com.georgebindragon.base.thread.HandlerUtil;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class HandlerTimerMonitor extends BaseTimerMonitor
{
	private DebounceHandler debounceHandler;

	public HandlerTimerMonitor(TimerConfig timerConfig)
	{
		super(timerConfig);
		debounceHandler = new DebounceHandler(HandlerUtil.getOtherHandler(), () ->
		{
			LogProxy.i(TAG, "onTimerTrigger-->");
			onTimerTrigger();
		}, repeatTimeMillis);
	}

	@Override
	protected void onTimerTrigger()
	{
		super.onTimerTrigger();
		if (needRepeat)
		{
			startTimer();
		} else
		{
			stopTimer();
		}
	}

	@Override
	protected void startTimer()
	{
		LogProxy.i(TAG, "startTimer-->");
		debounceHandler.onDebounce();
	}

	@Override
	protected void stopTimer()
	{
		LogProxy.i(TAG, "stopTimer-->");
		debounceHandler.destroy();
	}
}
