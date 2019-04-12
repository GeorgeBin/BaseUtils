package com.georgebindragon.base.debug.receiver;

/**
 * 创建人：George
 * 类名称：IDebugCallBack
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IDebugCallBack
{
	void onLogSwitch(boolean isOn);

	void onInternet(boolean needSave);

	void onStreamSwitch(int stream);

	void init();

	void onWavSave(boolean needSave);
}
