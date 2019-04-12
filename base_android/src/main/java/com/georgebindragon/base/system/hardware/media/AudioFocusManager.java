package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 * 类名称：AudioFocusManager
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioFocusManager
{
	private static final String TAG = "AudioFocusManager-->";

	private static final AudioFocusManager ourInstance = new AudioFocusManager();

	public static AudioFocusManager getInstance() { return ourInstance; }

	private AudioManager                            audioManager;
	private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
	private AudioManager.OnAudioFocusChangeListener afChangeListener2;

	private AudioFocusManager()
	{
		audioFocusChangeListener = focusChange ->
		{
			if (EmptyUtil.notEmpty(afChangeListener2)) afChangeListener2.onAudioFocusChange(focusChange);

			if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
			{
				if (keepFocus) requestFocus();
				LogProxy.d(TAG, "回调-->临时失去焦点");
			} else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
			{
				LogProxy.d(TAG, "回调-->获取到焦点");
			} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
			{
				if (keepFocus) requestFocus();
				LogProxy.d(TAG, "回调-->失去焦点");
			}
		};
	}

	public boolean init(Context context)
	{
		if (EmptyUtil.notEmpty(context))
		{
			audioManager = (AudioManager) context.getApplicationContext().getSystemService(AUDIO_SERVICE);
		}
		return EmptyUtil.notEmpty(context, audioManager);
	}

	public void addAudioFocusChangeListener(AudioManager.OnAudioFocusChangeListener audioFocusChangeListener)
	{
		if (EmptyUtil.notEmpty(audioFocusChangeListener))
		{
			afChangeListener2 = audioFocusChangeListener;
		}
	}

	private boolean keepFocus = false;

	public void keepFocus()
	{
		keepFocus = true;
		requestFocus();
	}

	public void releaseFocus()
	{
		keepFocus = false;
		if (EmptyUtil.notEmpty(audioManager, audioFocusChangeListener))
		{
			audioManager.abandonAudioFocus(audioFocusChangeListener);
		}
	}

	private boolean requestFocus()
	{
		return requestFocus(stream/* 音频通道*/, durationHint/* 要申请的音频焦点类型 */);
	}

	private int stream       = AudioManager.STREAM_MUSIC;
	private int durationHint = AudioManager.AUDIOFOCUS_GAIN;

	public boolean isKeepFocus()
	{
		return keepFocus;
	}

	public int getStream()
	{
		return stream;
	}

	public int getDurationHint()
	{
		return durationHint;
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
	public boolean requestFocus(int stream, int durationHint)
	{
		boolean request = false;
		if (0 <= stream && stream <= 10) {/* 正常的 */} else {stream = AudioManager.STREAM_MUSIC;}
		if (1 <= durationHint && durationHint <= 4) {/* 正常的音频焦点类型 */} else {durationHint = AudioManager.AUDIOFOCUS_GAIN;}
		if (EmptyUtil.notEmpty(audioManager))
		{
			int result = audioManager.requestAudioFocus(audioFocusChangeListener, stream, durationHint);
			this.stream = stream;
			this.durationHint = durationHint;
			request = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
			LogProxy.d(TAG, "requestFocus-->获取焦点结果:" + request);
		}
		return request;
	}

}
