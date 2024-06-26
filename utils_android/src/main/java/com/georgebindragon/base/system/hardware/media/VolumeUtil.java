package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.BaseUtils;

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
		AudioManager localAudioManager = AudioManagerUtil.getAudioManager(context);
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

	public static void adjustMusicVolume(boolean isUp)
	{
		adjustStreamVolume(AudioManager.STREAM_MUSIC, isUp, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
	}

	public static void adjustStreamVolume(int streamType, boolean isUp, int flags)
	{
		int direction = isUp ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
		adjustStreamVolume(streamType, direction, flags);
	}

	public static void adjustStreamVolume(int streamType, int direction, int flags)
	{
		adjustStreamVolume(BaseUtils.getContext(), streamType, direction, flags);
	}

	public static void adjustStreamVolume(Context context, int streamType, int direction, int flags)
	{
		AudioManager localAudioManager = AudioManagerUtil.getAudioManager(context);
		if (null != localAudioManager)
		{
			localAudioManager.adjustStreamVolume(streamType, direction, flags);
		}
	}

	public static int getStreamVolume(Context context, int type)
	{
		AudioManager localAudioManager = AudioManagerUtil.getAudioManager(context);
		if (null != localAudioManager && type >= -1 && type <= 11)
		{
			return localAudioManager.getStreamVolume(type);
		}
		return -1;
	}

	public static int getStreamMaxVolume(Context context, int type)
	{
		AudioManager localAudioManager = AudioManagerUtil.getAudioManager(context);
		if (null != localAudioManager && type >= -1 && type <= 11)
		{
			return localAudioManager.getStreamMaxVolume(type);
		}
		return -1;
	}
}
