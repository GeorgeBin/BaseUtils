package com.georgebindragon.base.rx.lfc;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;

/**
 * 创建人：George
 * 类名称：RxThrottle
 * 类概述：节流 = 固定速率执行
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class RxThrottle<T> extends RxLFC<T>
{
	public RxThrottle(boolean isOnlyOnce, long time, TimeUnit timeUnit, RxLFCCallBack<T> callBack)
	{
		super(LFCType.RxThrottle, isOnlyOnce, time, timeUnit, callBack);
	}

	public RxThrottle(boolean isOnlyOnce, long time, TimeUnit timeUnit, Scheduler subscribeOn, Scheduler observeOn, RxLFCCallBack<T> callBack)
	{
		super(LFCType.RxThrottle, isOnlyOnce, time, timeUnit, subscribeOn, observeOn, callBack);
	}
}
