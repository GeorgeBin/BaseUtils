package com.georgebindragon.base.app.lifecycle;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * 创建人：George
 *
 * 描述：app的生命周期回调
 *
 * 修改人：
 * 修改时间：
 * 修改备注：去掉开机启动广播的回调，可能会来不及
 */


public interface IAppLifeCycle extends IFunction
{
	void onAppStart();

	//	void onAppReceiveBootCompleted();

	void onAppReceiveShutdown();

	void onTerminate();

	void onAppExit();
}
