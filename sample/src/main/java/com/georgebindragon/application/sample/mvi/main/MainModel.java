package com.georgebindragon.application.sample.mvi.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * 创建人：George
 * 类名称：MainModel
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MainModel
{
	public static final String    TAG         = "MainModel-->";
	private static      MainModel ourInstance = new MainModel();

	public static MainModel getInstance() { return ourInstance; }

	boolean last = false;

	private MainModel()
	{
//		Disposable subscribe = Observable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>()
		Disposable subscribe = Observable.interval(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>()
		{
			@Override
			public void accept(Long aLong) throws Exception
			{
				last = !last;
				result.onNext(last);
			}
		});
	}

	private final PublishSubject<Boolean> result = PublishSubject.create();

	public Observable<Boolean> getResult()
	{
		return result;
	}
}
