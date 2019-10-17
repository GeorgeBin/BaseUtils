package com.georgebindragon.application.sample.media;

import com.georgebindragon.application.sample.constants.Raws;
import com.georgebindragon.base.system.reminder.sound.alert.BaseAlertPlayer;

/**
 * 创建人：George
 * 类名称：AlertPlayer
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AlertPlayer extends BaseAlertPlayer
{
	private static final AlertPlayer ourInstance = new AlertPlayer();

	public static AlertPlayer getInstance() { return ourInstance; }

	private static final int[] Rawes = {Raws.ID_Error, Raws.ID_Msg_Send, Raws.ID_PTI, Raws.ID_Take_Photo};

	@Override
	protected int[] getSounds() { return Rawes; }

	public void play_Error()
	{
		play(Raws.ID_Error);
	}

	public void play_Msg_Send()
	{
		play(Raws.ID_Msg_Send);
	}

	public void play_PTI()
	{
		play(Raws.ID_PTI);
	}

	public void play_Take_Photo()
	{
		play(Raws.ID_Take_Photo);
	}

	public void playNext(int count)
	{
		if (count >= Raws.POOL_MAX)
		{
			count = count % Raws.POOL_MAX;
		}
		play(count);
	}
}
