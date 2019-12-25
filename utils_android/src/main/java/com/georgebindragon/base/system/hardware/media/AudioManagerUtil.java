package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.BaseUtils;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 *
 * 描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioManagerUtil
{
	private static final String TAG = "AudioManagerUtil-->";

	private static AudioManager audioManager;

	public static AudioManager getAudioManager() { return getAudioManager(BaseUtils.getContext()); }

	public synchronized static AudioManager getAudioManager(Context context)
	{
		if (null == audioManager) initAudioManager(context);
		return audioManager;
	}

	private static void initAudioManager(Context context)
	{
		if (null == context) context = BaseUtils.getContext();
		if (null != context) audioManager = (AudioManager) context.getApplicationContext().getSystemService(AUDIO_SERVICE);
	}
}
