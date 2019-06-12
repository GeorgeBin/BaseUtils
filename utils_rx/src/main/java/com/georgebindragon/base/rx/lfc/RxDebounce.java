package com.georgebindragon.base.rx.lfc;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;

/**
 * 创建人：George
 * 类名称：RxDebounce
 * 类概述：去抖 = 输入停止一段时间后再执行
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class RxDebounce<T> extends RxLFC<T>
{
	public RxDebounce(boolean isOnlyOnce, long time, TimeUnit timeUnit, RxLFCCallBack<T> callBack)
	{
		super(LFCType.RxDebounce, isOnlyOnce, time, timeUnit, callBack);
	}

	public RxDebounce(boolean isOnlyOnce, long time, TimeUnit timeUnit, Scheduler subscribeOn, Scheduler observeOn, RxLFCCallBack<T> callBack)
	{
		super(LFCType.RxDebounce, isOnlyOnce, time, timeUnit, subscribeOn, observeOn, callBack);
	}
}
