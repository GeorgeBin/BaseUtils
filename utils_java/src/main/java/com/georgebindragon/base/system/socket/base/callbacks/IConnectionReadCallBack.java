package com.georgebindragon.base.system.socket.base.callbacks;

/**
 * 创建人：George
 * 类名称：IConnectionReadCallBack
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IConnectionReadCallBack extends IConnectionCallBack
{
	void onDataReceive(byte[] dataBytes);

	void onSoTimeOut(long socketMark);
}
