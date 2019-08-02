package com.georgebindragon.base.system.socket.base.function;


import com.georgebindragon.base.system.socket.connection.ConnectionConfig;

public interface ISocketConnection extends IConnection
{
	void init(ConnectionConfig config);

	void write(byte[] dataBytes);

//	void receive(byte[] dataBytes);

	void destroy();
}
