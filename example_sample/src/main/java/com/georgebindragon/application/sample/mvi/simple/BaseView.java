package com.georgebindragon.application.sample.mvi.simple;

/**
 * 创建人：George
 * 类名称：BaseView
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface BaseView<T>
{
	void render(T viewState);
}
