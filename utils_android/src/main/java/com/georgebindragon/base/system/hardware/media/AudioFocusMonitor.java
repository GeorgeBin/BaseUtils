package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 * 类名称：AudioFocusMonitor
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioFocusMonitor extends BaseListenerMonitor<AudioManager.OnAudioFocusChangeListener>
{
	private static final AudioFocusMonitor ourInstance = new AudioFocusMonitor();

	public static AudioFocusMonitor getInstance() { return ourInstance; }

	private AudioManager                            audioManager;
	private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

	private AudioFocusMonitor()
	{
		audioFocusChangeListener = this::onAudioFocusChange;
	}

	/**
	 * 注册监听器
	 *
	 * @param context 注册Broadcast需要的context
	 * @return 是否注册成功, 如果返回false, 请申请权限
	 */
	public boolean initMonitor(Context context)
	{
		audioManager = AudioManagerUtil.getAudioManager(context);
		return EmptyUtil.notEmpty(audioManager);
	}

	/**
	 * @param stream       音频通道
	 * @param durationHint 需要申请的类型:AudioManager.AUDIOFOCUS_GAIN
	 *                     {@link AudioManager#AUDIOFOCUS_GAIN}   //超长
	 *                     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT}   //临时
	 *                     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK}   //临时
	 *                     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE}   //临时
	 * @return 是否得到所需要的结果
	 */
	public boolean requestAudioFocus(int stream, int durationHint)
	{
		boolean request = false;
		if (0 <= stream && stream <= 10) {/* 正常的 */} else {stream = AudioManager.STREAM_MUSIC;}
		if (1 <= durationHint && durationHint <= 4) {/* 正常的音频焦点类型 */} else {durationHint = AudioManager.AUDIOFOCUS_GAIN;}
		if (EmptyUtil.notEmpty(audioManager))
		{
			int result = audioManager.requestAudioFocus(audioFocusChangeListener, stream, durationHint);
			request = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
			LogProxy.d(TAG, "requestFocus-->获取焦点结果:" + request);
		}
		return request;
	}

	private void notifyListeners(int xxx)
	{
		final Queue<AudioManager.OnAudioFocusChangeListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (AudioManager.OnAudioFocusChangeListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onAudioFocusChange(xxx);
			}
		}
	}

	private void onAudioFocusChange(int focusChange)
	{
		notifyListeners(focusChange);

		if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
		{
			LogProxy.d(TAG, "回调-->临时失去焦点");
		} else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
		{
			LogProxy.d(TAG, "回调-->获取到焦点");
		} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
		{
			LogProxy.d(TAG, "回调-->失去焦点");
		}
	}
}
