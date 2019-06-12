package com.georgebindragon.application.sample.mvi.main;

import com.georgebindragon.application.sample.mvi.simple.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 创建人：George
 * 类名称：MainPresenter
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MainPresenter extends BasePresenter<MainView, MainViewState>
{
	public static final String        TAG         = "MainPresenter-->";
	private static      MainPresenter ourInstance = new MainPresenter();

	public static MainPresenter getInstance() { return ourInstance; }

	private MainPresenter()
	{
	}


	public void initButton()
	{
		Observable<Boolean> booleanObservable = view.onButtonClick().flatMap((Function<String, ObservableSource<Boolean>>) s -> MainModel.getInstance().getResult());
		Disposable subscribe = booleanObservable
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(aBoolean -> view.render(new MainViewState("" + aBoolean)));
	}
}
