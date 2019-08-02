package com.georgebindragon.base.system.socket.base.listener;


import com.georgebindragon.base.system.socket.connection.TCPConnectError;

/**
 * 创建人：George
 * 类名称：ISocketListener
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface ITCPListener extends ISocketListener
{
	void onConnectError(String ID, long socketMark, TCPConnectError connectError);
}
