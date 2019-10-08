package com.georgebindragon.base.system.reminder.sound.alert;

import android.content.Context;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.system.reminder.sound.SoundPoolAsSoundPlayer;

/**
 * 创建人：George
 * 类名称：SoundPoolAlertPlayer
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class SoundPoolAlertPlayer extends SoundPoolAsSoundPlayer implements IAlertPlayer
{
	@Override
	protected void onReload()
	{
		init(BaseUtils.getContext());
	}

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
