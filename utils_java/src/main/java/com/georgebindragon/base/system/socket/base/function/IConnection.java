package com.georgebindragon.base.system.socket.base.function;


import com.georgebindragon.base.abilities.IAbilities;

public interface IConnection extends IAbilities
{
	void connect();

	void disconnect();
}
