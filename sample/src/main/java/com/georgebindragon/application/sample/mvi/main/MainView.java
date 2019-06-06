package com.georgebindragon.application.sample.mvi.main;

import com.georgebindragon.application.sample.mvi.simple.BaseView;

import io.reactivex.Observable;

/**
 * 创建人：George
 * 类名称：MainView
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface MainView extends BaseView<MainViewState>
{
	Observable<String> onButtonClick();
}
