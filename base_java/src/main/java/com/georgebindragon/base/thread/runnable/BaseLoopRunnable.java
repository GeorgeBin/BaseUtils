package com.georgebindragon.base.thread.runnable;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 *
 * 类名称：BaseLoopRunnable
 * 类概述：
 * 创建时间：2018-08-03 14:45
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseLoopRunnable extends BaseRunnable
{
	protected volatile boolean needLooping;

	public boolean isNeedLooping()
	{
		return needLooping;
	}

	public void setNeedLooping(boolean needLooping)
	{
		this.needLooping = needLooping;
	}

	@Override
	public void onRun()
	{
		try
		{
			beforeLoop();
			while (needLooping)
			{
				onLoop();
			}
			afterLoop();
		} catch (Exception e)
		{
			afterLoop();
			needLooping = false;
			LogProxy.e(TAG, "onRun", e);
		}
	}

	public abstract void beforeLoop();

	public abstract void onLoop();

	public abstract void afterLoop();
}
