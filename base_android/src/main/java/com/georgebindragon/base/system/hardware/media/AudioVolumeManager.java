package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.utils.EmptyUtil;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 * 类名称：AudioVolumeManager
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioVolumeManager
{
	private static final AudioVolumeManager ourInstance = new AudioVolumeManager();

	public static AudioVolumeManager getInstance()
	{
		return ourInstance;
	}

	private AudioVolumeManager()
	{ }

	private AudioManager audioManager;

	public boolean init(Context context)
	{
		if (EmptyUtil.notEmpty(context))
		{
			audioManager = (AudioManager) context.getApplicationContext().getSystemService(AUDIO_SERVICE);
		}
		return EmptyUtil.notEmpty(context, audioManager);
	}

	public void adjustMusicVolume(boolean isUp)
	{
		adjustStreamVolume(AudioManager.STREAM_MUSIC, isUp, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
	}

	public void adjustStreamVolume(int streamType, boolean isUp, int flags)
	{
		int direction = isUp ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
		adjustStreamVolume(streamType, direction, flags);
	}

	public void adjustStreamVolume(int streamType, int direction, int flags)
	{
		if (EmptyUtil.notEmpty(audioManager))
		{
			audioManager.adjustStreamVolume(streamType, direction, flags);
		}
	}
}
