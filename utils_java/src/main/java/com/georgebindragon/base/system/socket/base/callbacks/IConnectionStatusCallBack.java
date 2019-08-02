package com.georgebindragon.base.system.socket.base.callbacks;

public interface IConnectionStatusCallBack
{
	void onLinkBreak(long mark);
	void onLinkConnected(long mark);
}
