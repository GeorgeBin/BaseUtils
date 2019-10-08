package com.georgebindragon.base.system.reminder.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;

import java.util.Collection;

/**
 * 创建人：George
 * 类名称：MediaPlayerAsSoundPlayer
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MediaPlayerAsSoundPlayer extends SoundPlayer<Integer, MediaPlayer>
{
	private MediaPlayer.OnCompletionListener listener;

	public void setListener(MediaPlayer.OnCompletionListener listener)
	{
		this.listener = listener;
	}

	@Override
	protected void onStreamTypeChange(int streamType)
	{
		LogProxy.i(TAG, "onStreamTypeChange: 设置值:" + streamType);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Collection<MediaPlayer> values = map.values();
			for (MediaPlayer player : values)
			{
				setPlayerStreamType(player, streamType);
			}
		}
	}

	@Override
	public void load(Context context, Integer key, int resID)
	{
		if (null == context) context = BaseUtils.getContext();
		MediaPlayer player = createPlayer(context, resID);
		if (null != player)
		{
			setPlayerStreamType(player, streamType);// 设置播放通道
			setKeyAndValue(key, player);//添加到集合
		}
	}

	public boolean playSound(int key, MediaPlayer.OnCompletionListener listener)
	{
		return playSound(key, false, listener);
	}

	public boolean playSound(int key, boolean needLoop, MediaPlayer.OnCompletionListener listener)
	{
		setListener(listener);
		return playSound(key, needLoop);
	}

	private MediaPlayer createPlayer(Context context, int resID)
	{
		if (null != context && resID > 0)
		{
			return MediaPlayer.create(context, resID);
		} else
		{
			return null;
		}
	}

	private void setPlayerStreamType(MediaPlayer player, int streamType)
	{
		//1. 设置播放通道
		try
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			{
				AudioAttributes attributes = new AudioAttributes.Builder().setLegacyStreamType(streamType).build();
				try
				{
					player.setAudioAttributes(attributes);
				} catch (Exception e) { LogProxy.e(TAG, "setPlayerStreamType", e); }
			}
			//Android 4.x 不需要手动 设置或切换 播放通道, 否则会没有声音出来
		} catch (Exception e) {LogProxy.e(TAG, "setPlayerStreamType", e);}
	}

	@Override
	protected void playerPlay(MediaPlayer player, boolean needLoop)
	{
		try
		{
			if (null != player && !player.isPlaying())
			{
				player.setVolume(volume, volume);
				player.setLooping(needLoop);
				if (null != this.listener) player.setOnCompletionListener(listener);
				player.start();
			} else
			{
				LogProxy.i(TAG, "playSound: player is playing!");
			}
		} catch (Exception e) {LogProxy.e(TAG, "playerPlay", e);}
	}

	@Override
	protected void playerStop(MediaPlayer player)
	{
		try
		{
			if (null != player && player.isPlaying())
			{
				player.stop();
			} else
			{
				LogProxy.i(TAG, "stopSound: player is already stop");
			}
		} catch (Exception e) {LogProxy.e(TAG, "playerStop", e);}
	}

	/**
	 * 音频是否在播放
	 *
	 * @param key 音频资源Id
	 * @return 是否正在播放
	 */
	public boolean soundIsPlaying(Integer key)
	{
		try
		{
			LogProxy.i(TAG, "soundIsPlaying:", "key=" + key);

			MediaPlayer player = getValueByKey(key);
			return player != null && player.isPlaying();
		} catch (Exception e)
		{
			LogProxy.e(TAG, "soundIsPlaying", e);
			return false;
		}
	}
}
