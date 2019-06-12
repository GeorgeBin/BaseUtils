package com.georgebindragon.base.function.log;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * 创建人：George
 * 类名称：LogFunctionI
 * 类概述：LogAdapter 的上层接口
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface LogFunctionI extends IFunction
{

	//控制方法:
	void setLogVisibility(boolean isLogEnable);

	void setLogLevel(int level);

	//WTF         = 7;//Log.wtf
	void wtf(String TAG, String... msg);

	//ERROR       = 6;//Log.e
	void e(String TAG, String... msg);

	//专门打Exception的log 示例：LogProxy.e(TAG, "method", e);
	void e(String TAG, String method, Exception e);

	//WARN        = 5;//Log.w
	void w(String TAG, String... msg);

	//INFO        = 3;//Log.i
	void i(String TAG, String... msg);

	//DEBUG       = 2;//Log.d
	void d(String TAG, String... msg);

	//VERBOSE     = 1;//Log.v
	void v(String TAG, String... msg);
}
