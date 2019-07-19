package com.georgebindragon.base.function;

/**
 * 创建人：George
 * 类名称：BaseFunction
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseFunction
{
	protected String TAG = "Function: " + getClass().getSimpleName() + "-->";

	protected boolean isFunctionOn=false;

	public boolean isFunctionOn()
	{
		return isFunctionOn;
	}

	public void setFunctionOn(boolean on)
	{
		isFunctionOn = on;
	}
}
