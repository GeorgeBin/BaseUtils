package com.georgebindragon.base.system.timer;

import com.georgebindragon.base.function.log.LogProxy;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class JavaTimerMonitor extends BaseTimerMonitor
{
	public JavaTimerMonitor(TimerConfig timerConfig)
	{
		super(timerConfig);
		timer = new Timer(timerConfig.getKey());
	}

	private Timer timer;

	@Override
	protected void startTimer()
	{
		LogProxy.i(TAG, "startTimer-->needRepeat=" + needRepeat);
		try
		{
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					onTimerTrigger();
				}
			}, repeatTimeMillis, needRepeat ? repeatTimeMillis : -1);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "startTimer", e);
		}
	}

	@Override
	protected void stopTimer()
	{
		LogProxy.i(TAG, "stopTimer-->");

		timer.purge();
		timer.cancel();
	}
}
