package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 * 类名称：VolumeUtil
 * 类概述：音量管理工具
 * 详细描述：
 *
 * 1. 直接设置音量值
 * 2. 调节音量的大小
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class VolumeUtil
{
	private static AudioManager audioManager;

	public static void setRingVolume(int volume)
	{
		setVolume(AudioManager.STREAM_RING, volume);
	}

	public static void setMusicVolume(int volume)
	{
		setVolume(AudioManager.STREAM_MUSIC, volume);
	}

	public static void setVolume(int type, int volume)
	{
		setVolume(BaseUtils.getContext(), type, volume);//ring=2、music=3
	}

	public static void setVolume(Context context, int type, int volume)
	{
		setVolume(context, type, volume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
	}

	private static void setVolume(Context context, int type, int volume, int flags)
	{
		AudioManager localAudioManager = getAudioManager(context);
		if (null != localAudioManager && type >= -1 && type <= 11)
		{
			int streamMaxVolume = localAudioManager.getStreamMaxVolume(type);
			if (volume <= 0)
			{
				volume = 0;
			} else if (volume >= streamMaxVolume)
			{
				volume = streamMaxVolume;
			}
			localAudioManager.setStreamVolume(type, volume, flags);
		}
	}

	private static AudioManager getAudioManager(Context context)
	{
		if (null == audioManager) audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		return audioManager;
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
		adjustStreamVolume(BaseUtils.getContext(), streamType, direction, flags);
	}

	public void adjustStreamVolume(Context context, int streamType, int direction, int flags)
	{
		AudioManager localAudioManager = getAudioManager(context);
		if (null != localAudioManager)
		{
			audioManager.adjustStreamVolume(streamType, direction, flags);
		}
	}
}
