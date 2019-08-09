package com.georgebindragon.base.app.lifecycle;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * 创建人：George
 * 类名称：IAppLifeCycle
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IAppLifeCycle extends IFunction
{
	void onAppStart();

	void onAppReceiveBootCompleted();

	void onAppReceiveShutdown();

	void onTerminate();

	void onAppExit();
}
