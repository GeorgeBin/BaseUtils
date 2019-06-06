package com.georgebindragon.application.sample.mvi.simple;

/**
 * 创建人：George
 * 类名称：BasePresenter
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BasePresenter<V extends BaseView<S>, S>
{
	protected V view;

	public void init(V v)
	{
		view = v;
	}

	public void onView(S s)
	{
		view.render(s);
	}
}
