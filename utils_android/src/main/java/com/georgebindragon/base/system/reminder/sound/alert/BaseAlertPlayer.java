package com.georgebindragon.base.system.reminder.sound.alert;

import android.content.Context;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.data.basic.FloatUtil;
import com.georgebindragon.base.data.constants.Manufacturers;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.reminder.sound.SoundPoolAsSoundPlayer;

/**
 * 创建人：George
 * 类名称：BaseAlertPlayer
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseAlertPlayer
{
	protected String TAG = "BaseAlertPlayer: " + getClass().getSimpleName() + "-->";

	private SoundPoolAlertPlayer soundPoolAlertPlayer;
	private MediaAlertPlayer     mediaAlertPlayer;
	private IAlertPlayer         function;

	protected BaseAlertPlayer()
	{
		soundPoolAlertPlayer = new SoundPoolAlertPlayer()
		{
			@Override
			protected void loadSounds(Context context)
			{
				int[] soundList = getSounds();
				for (Integer resId : soundList)
				{
					load(context, resId, resId);
				}
			}
		};

		mediaAlertPlayer = new MediaAlertPlayer()
		{
			@Override
			protected void loadSounds(Context context)
			{
				int[] soundList = getSounds();
				for (Integer resId : soundList)
				{
					load(context, resId, resId);
				}
			}
		};
		initPlayer_Auto();
	}

	protected abstract int[] getSounds();

	protected boolean enable     = true;
	protected int     streamType = SoundPoolAsSoundPlayer.StreamType_Default;
	protected float   volume     = 1.0f;
	protected int     progress   = 100;

	private int delayTime = delayTime_Low;

	public void initPlayer_Auto()
	{
		boolean soundPoolDisable = Manufacturers.isSoundPoolDisable();
		if (Manufacturers.isMediaPlayerDisable() || Manufacturers.isNormalCellPhone())//Media不可用 || 普通手机品牌 -->使用soundPool
		{
			initPlayer_SoundPool();
		} else if (soundPoolDisable)//soundPool不可用 -->使用Media
		{
			initPlayer_Media();
		} else//默认 -->使用Media
		{
			initPlayer_Media();
		}
	}

	public void initPlayer_Media()
	{
		function = mediaAlertPlayer;
		initPlayer();
	}

	public void initPlayer_SoundPool()
	{
		function = soundPoolAlertPlayer;
		initPlayer();
	}

	private void initPlayer()
	{
		new Thread(() ->
		{
			function.init(BaseUtils.getContext());

			setStreamType(streamType);
			setEnable(enable);
			setVolume(volume);
			setProgress(progress);
		}).start();

	}

	public void play(int id)
	{
		LogProxy.i(TAG, "play-->id=" + id);
		if (enable)
		{
			if (null != function)
			{
				function.play(id);
				LogProxy.i(TAG, "play-->实际播放");
			}
		}
	}

	public void setEnable(boolean soundEnable)
	{
		LogProxy.i(TAG, "setEnable-->soundEnable=" + soundEnable);
		this.enable = soundEnable;
	}

	public void setStreamType(int streamType)
	{
		LogProxy.i(TAG, "setStreamType-->streamType=" + streamType);
		this.streamType = streamType;
		if (null != function) function.setStreamType(streamType);
	}

	public void setVolume(float volume)
	{
		LogProxy.i(TAG, "setVolume-->volume=" + volume);
		this.volume = volume;
		if (null != function) function.setVolume(volume);
	}

	public void setProgress(int progress)
	{
		LogProxy.i(TAG, "setProgress-->progress=" + progress);
		this.progress = progress;
		if (progress <= 0)
		{
			setVolume(0);
			setEnable(false);
		} else
		{
			setEnable(true);
			float volume = FloatUtil.getFloat(progress);
			setVolume(volume);
			calculateDelayTime(progress);
		}
	}

	private static final int delayTime_MiddleAndHigh = 200;
	private static final int delayTime_Low           = 150;

	public int getDelayTime()
	{
		return delayTime;
	}

	protected void calculateDelayTime(int progress)// 测试经验值：200ms完全不会有杂音, 150ms勉强合格, 100ms比较明显
	{
		if (progress >= 50)
		{
			this.delayTime = delayTime_MiddleAndHigh;
		} else
		{
			this.delayTime = delayTime_Low;
		}
	}
}