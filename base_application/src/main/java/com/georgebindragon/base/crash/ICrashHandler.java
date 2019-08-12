package com.georgebindragon.base.crash;

import android.content.Context;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * 创建人：George
 * 类名称：ICrashHandler
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface ICrashHandler extends IFunction
{
	void init(final Context context);

	void setCallBack(CrashCallBack crashCallBack);
}
