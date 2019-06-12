package com.georgebindragon.base.system.hardware.media;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 * 类名称：AudioManagerUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AudioManagerUtil
{
	private static AudioManager audioManager;

	public static AudioManager getAudioManager()
	{
		return getAudioManager(null);
	}

	public static AudioManager getAudioManager(Context context)
	{
		if (EmptyUtil.isEmpty(audioManager))
		{
			initAudioManager(context);
		}
		return audioManager;
	}

	private static void initAudioManager(Context context)
	{
		if (null == audioManager)
		{
			Context context2 = context;
			if (null == context2)
			{
				context2 = BaseUtils.getContext();
			}

			if (EmptyUtil.notEmpty(context2))
			{
				audioManager = (AudioManager) context.getApplicationContext().getSystemService(AUDIO_SERVICE);
			}
		}
	}
}
