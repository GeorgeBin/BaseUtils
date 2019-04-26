package com.georgebindragon.base.rx.lfc;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * 创建人：George
 * 类名称：RxLFC
 * 类概述：负载频率控制器
 * 详细描述：
 *
 * 控制负载频率
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class RxLFC<T>
{
	protected String            TAG         = "" + getClass().getSimpleName() + "-->";
	private   PublishSubject<T> subject;
	private   Disposable        disposable;
	private   long              time;
	private   TimeUnit          timeUnit;
	private   RxLFCCallBack<T>  rxLFCCallBack;
	private   boolean           isOnlyOnce  = false;
	private   LFCType           type        = LFCType.RxDebounce;
	private   Scheduler         subscribeOn = AndroidSchedulers.mainThread();
	private   Scheduler         observeOn   = AndroidSchedulers.mainThread();

	public RxLFC(LFCType type, boolean isOnlyOnce, long time, TimeUnit timeUnit, RxLFCCallBack<T> callBack)
	{
		subject = PublishSubject.create();
		setParameters(type, isOnlyOnce, time, timeUnit, subscribeOn, observeOn, callBack);
	}

	public RxLFC(LFCType type, boolean isOnlyOnce, long time, TimeUnit timeUnit, Scheduler subscribeOn, Scheduler observeOn, RxLFCCallBack<T> callBack)
	{
		subject = PublishSubject.create();
		setParameters(type, isOnlyOnce, time, timeUnit, subscribeOn, observeOn, callBack);
	}

	private void setParameters(LFCType type, boolean isOnlyOnce, long time, TimeUnit timeUnit, Scheduler subscribeOn, Scheduler observeOn, RxLFCCallBack<T> callBack)
	{
		this.type = type;
		this.time = time;
		this.timeUnit = timeUnit;
		this.rxLFCCallBack = callBack;
		this.isOnlyOnce = isOnlyOnce;
		this.subscribeOn = subscribeOn;
		this.observeOn = observeOn;
	}

	public void setNext(T object)
	{
		if (EmptyUtil.notEmpty(subject)) { subject.onNext(object);}
	}

	public void startListen()
	{
		RxCommonUtil.dispose(disposable);
		LogProxy.d(TAG, "startListen", "time=" + time + "\ttimeUnit=" + timeUnit);
		if (type == LFCType.RxDebounce)
		{
			disposable = subject.debounce(time, timeUnit)// xx时间后再执行
					.subscribeOn(subscribeOn)
					.observeOn(observeOn)
					.subscribe(this::inside);
		} else if (type == LFCType.RxThrottle)
		{
			disposable = subject.throttleFirst(time, timeUnit)// 在第一次时即触发,然后隔xx时间后,再次触发
					.subscribeOn(subscribeOn)
					.observeOn(observeOn)
					.subscribe(this::inside);
		} else
		{
			LogProxy.d(TAG, "startListen, 未知 type");
		}
	}

	private void inside(T object)
	{
		LogProxy.d(TAG, "ThreadName=" + Thread.currentThread().getName());
		if (EmptyUtil.notEmpty(rxLFCCallBack)) rxLFCCallBack.OnRxLFCCallBack(object);
		if (isOnlyOnce)
		{
			LogProxy.d(TAG, "只回调一次，结束！");
			stopListen();
		}
	}

	public void stopListen()
	{
		LogProxy.d(TAG, "stopListen()");
		RxCommonUtil.dispose(disposable);
	}
}
