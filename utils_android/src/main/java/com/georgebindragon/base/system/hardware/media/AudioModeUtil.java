package com.georgebindragon.base.system.hardware.media;

import android.media.AudioManager;
import android.os.Build;

/**
 * 创建人：George
 *
 * 描述：音频模式工具
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioModeUtil
{
	private static final String TAG = "AudioModeUtil-->";

	public static final int SpeakerMode = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? AudioManager.MODE_IN_COMMUNICATION : AudioManager.MODE_IN_CALL;

	public static void setMicrophoneMute(boolean isMute) { getAudioManager().setMicrophoneMute(isMute); }

	public static void setSpeakerphoneOn(boolean isOn) { getAudioManager().setSpeakerphoneOn(isOn); }

	public static void setCallMode() { getAudioManager().setMode(SpeakerMode); }

	public static void setNormalMode() { getAudioManager().setMode(AudioManager.MODE_NORMAL); }

	public static AudioManager getAudioManager() { return AudioManagerUtil.getAudioManager(); }

	public static boolean isSpeakerphoneOn()
	{
		return getAudioManager().isSpeakerphoneOn();
	}

}
