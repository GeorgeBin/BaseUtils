package com.georgebindragon.base.system.reminder;

import android.content.Context;
import android.media.SoundPool;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：SoundPoolAsSoundPlayer
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SoundPoolAsSoundPlayer extends SoundPlayer<Integer, Integer>
{
	private SoundPool pool;

	private SoundPool.OnLoadCompleteListener listener;

	public void setListener(SoundPool.OnLoadCompleteListener listener)
	{
		this.listener = listener;
	}

	@Override
	public void load(Context context, int streamType, Integer key, int resID)
	{
		if (null == context) context = BaseUtils.getContext();
		//初始化, 并设置参数: 最大数量, 播放通道, 加载监听
		if (null == pool)
		{
			pool = new SoundPool(10, streamType, 0);
			if (null != listener) pool.setOnLoadCompleteListener(listener);
		}

		pool.setVolume(streamType, volume, volume);// 设置音量大小

		int loadID = pool.load(context, resID, 10000);
		setKeyAndValue(key, loadID);//添加到集合
	}

	@Override
	protected void playerPlay(Integer loadID, boolean needLoop)
	{
		try
		{
			LogProxy.i(TAG, "playerPlay:", "loadID=" + loadID, "needLoop=" + needLoop);

			int play = pool.play(loadID, volume, volume, 0, needLoop ? -1 : 0, 1);

			LogProxy.i(TAG, "playerPlay: play结果=" + play);
		} catch (Exception e) {LogProxy.e(TAG, "playerPlay", e);}
	}

	@Override
	protected void playerStop(Integer loadID)
	{
		try { pool.stop(loadID); } catch (Exception e) {LogProxy.e(TAG, "playerStop", e);}
	}
}
