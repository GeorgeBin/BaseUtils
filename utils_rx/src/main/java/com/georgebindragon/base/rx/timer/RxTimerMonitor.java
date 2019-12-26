package com.georgebindragon.base.rx.timer;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.rx.utils.RxTimer;
import com.georgebindragon.base.system.timer.BaseTimerMonitor;
import com.georgebindragon.base.system.timer.TimerConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class RxTimerMonitor extends BaseTimerMonitor
{
	private Disposable timerDisposable;

	public RxTimerMonitor(TimerConfig timerConfig) { super(timerConfig); }

	@Override
	protected void startTimer()
	{
		LogProxy.i(TAG, "startTimer-->");
		RxCommonUtil.dispose(timerDisposable);
		timerDisposable = RxTimer.getATimer(repeatTimeMillis, TimeUnit.MILLISECONDS)
				.subscribe(count ->
				{
					LogProxy.i(TAG, "accept-->count=" + count);
					onTimerTrigger();
				});
	}

	@Override
	protected void stopTimer()
	{
		LogProxy.i(TAG, "stopTimer-->");
		RxCommonUtil.dispose(timerDisposable);
	}
}
