package com.georgebindragon.base.system.reminder;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.function.MapManager;
import com.georgebindragon.base.function.log.LogProxy;

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


abstract class SoundPlayer<K, V> extends MapManager<K, V>
{
	protected String TAG = "SoundPlayer: " + getClass().getSimpleName() + "-->";

	public static final int StreamType_Default = AudioManager.STREAM_RING;

	protected float volume = 1.0f;

	public void setVolume(float volume)
	{
		LogProxy.i(TAG, "setVolume: 设置值:" + volume);
		if (volume > 1.0f)
		{
			volume = 1.0f;
		} else if (volume < 0f)
		{
			volume = 0f;
		}

		if (this.volume != volume)
		{
			this.volume = volume;
			LogProxy.i(TAG, "setVolume: 最终值:" + this.volume);
		}
	}

	protected int streamType = StreamType_Default;

	public void setStreamType(int streamType)
	{
		LogProxy.i(TAG, "setStreamType: 设置值:" + streamType);
		if (streamType < -1 || streamType > 11) streamType = AudioManager.STREAM_RING;

		if (this.streamType != streamType)
		{
			this.streamType = streamType;
			LogProxy.i(TAG, "setStreamType: 最终值:" + this.streamType);
			onStreamTypeChange(this.streamType);
		}
	}

	protected abstract void onStreamTypeChange(int streamType);

	public abstract void load(Context context, K key, int resID);

	public void unload(K key)
	{
		deleteKeyAndValue(key);
	}

	public boolean playSound(K key)
	{
		return playSound(key, false);
	}

	/**
	 * 播放音频
	 *
	 * @param key      音频资源Id
	 * @param needLoop 是否循环
	 */
	public boolean playSound(K key, boolean needLoop)
	{
		boolean result = false;
		try
		{
			LogProxy.i(TAG, "playSound:", "key=" + key, "needLoop=" + needLoop);

			V player = getValueByKey(key);
			if (player != null)
			{
				playerPlay(player, needLoop);
				result = true;
			} else
			{
				LogProxy.i(TAG, "playSound: 没有获取到这个值");
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "playSound", e);
			return false;
		}
		return result;
	}

	protected abstract void playerPlay(V player, boolean needLoop);

	protected abstract void playerStop(V player);


	/**
	 * 停止播放
	 *
	 * @param key 音频资源Id
	 */
	public void stopSound(K key)
	{
		try
		{
			LogProxy.i(TAG, "stopSound:", "key=" + key);

			V player = getValueByKey(key);
			if (player != null)
			{
				playerStop(player);
			} else
			{
				LogProxy.i(TAG, "stopSound: 没有获取到这个值");
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "stopSound", e);
		}
	}
}
