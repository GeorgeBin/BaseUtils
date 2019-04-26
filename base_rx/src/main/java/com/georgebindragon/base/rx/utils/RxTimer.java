package com.georgebindragon.base.rx.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * 2017/3/25
 */

public class RxTimer
{
	public static final Observable<Long> OneSecondTimer = getATimer(1, TimeUnit.SECONDS);//常用的秒跳动

	public static Observable<Long> getATimer(long timeCell, TimeUnit timeUnit)
	{
		return Observable.interval(timeCell, timeUnit);
	}

	public static Observable<Long> getACount(long timeCell, TimeUnit timeUnit)
	{
		return Observable.timer(timeCell, timeUnit);
	}

	public static Observable<Long> getOneSecondTimer()
	{
		return OneSecondTimer;
//		return getATimer(1, TimeUnit.SECONDS);//常用的秒跳动
	}
}
