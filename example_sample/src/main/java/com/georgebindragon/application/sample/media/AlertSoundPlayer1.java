package com.georgebindragon.application.sample.media;

import android.content.Context;

import com.georgebindragon.application.sample.constants.Raws;
import com.georgebindragon.base.system.reminder.MediaPlayerAsSoundPlayer;

/**
 * 创建人：George
 * 类名称：AlertSoundPlayer1
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AlertSoundPlayer1 extends MediaPlayerAsSoundPlayer
{
	private static final AlertSoundPlayer1 ourInstance = new AlertSoundPlayer1();

	public static AlertSoundPlayer1 getInstance() { return ourInstance; }

	private AlertSoundPlayer1() { }

	public void init(Context context, int streamType)
	{
		load(context, streamType,  Raws.ID_Error, Raws.Raw_Error);
		load(context, streamType,  Raws.ID_Msg_Send, Raws.Raw_Msg_Send);
		load(context, streamType,  Raws.ID_PTI, Raws.Raw_PTI);
		load(context, streamType,  Raws.ID_Take_Photo, Raws.Raw_Take_Photo);
	}

	public void play_Error()
	{
		playSound(Raws.ID_Error);
	}

	public void play_Msg_Send()
	{
		playSound(Raws.ID_Msg_Send);
	}

	public void play_PTI()
	{
		playSound(Raws.ID_PTI);
	}

	public void play_Take_Photo()
	{
		playSound(Raws.ID_Take_Photo);
	}

	public void playNext(int count)
	{
		if (count >= Raws.POOL_MAX)
		{
			count = count % Raws.POOL_MAX;
		}
		playSound(count);
	}
}
