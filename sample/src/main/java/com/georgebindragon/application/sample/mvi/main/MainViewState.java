package com.georgebindragon.application.sample.mvi.main;

/**
 * 创建人：George
 * 类名称：MainViewState
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public final class MainViewState
{
	private final String buttonText;

	public MainViewState(String buttonText)
	{
		this.buttonText = buttonText;
	}

	public String getButtonText()
	{
		return buttonText;
	}
}
