package com.georgebindragon.base.system.reminder.sound.alert;

import android.content.Context;

import com.georgebindragon.base.system.reminder.sound.MediaPlayerAsSoundPlayer;

/**
 * 创建人：George
 * 类名称：MediaAlertPlayer
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class MediaAlertPlayer extends MediaPlayerAsSoundPlayer implements IAlertPlayer
{
	@Override
	public void init(Context context)
	{
		loadSounds(context);
	}

	@Override
	public void play(int id)
	{
		playSound(id);
	}

	protected abstract void loadSounds(Context context);
}
