package com.georgebindragon.base.system.reminder.sound.alert;

import android.content.Context;

import com.georgebindragon.base.abilities.IAbilities;

/**
 * 创建人：George
 * 类名称：IAlertPlayer
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IAlertPlayer extends IAbilities
{
	void init(Context context);

	void setVolume(float volume);

	void setStreamType(int streamType);

	void play(int id);
}
