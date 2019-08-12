package com.georgebindragon.base.crash;

import com.georgebindragon.base.abilities.callbacks.ICallBack;

/**
 * 创建人：George
 * 类名称：CrashCallBack
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface CrashCallBack extends ICallBack
{
	void OnCrash(Throwable crash);
}
