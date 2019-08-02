package com.georgebindragon.base.system.socket.base.listener;

import com.georgebindragon.base.abilities.IAbilities;
import com.georgebindragon.base.system.socket.connection.ConnectionStatus;

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


public interface ISocketListener extends IAbilities
{
	void onDataReceive(String ID, long socketMark, byte[] dataBytes);

	void connectInfo(String ID, long socketMark, boolean isConnected, ConnectionStatus linkStatus);

	void onSoTimeOut(String ID, long socketMark);

}
