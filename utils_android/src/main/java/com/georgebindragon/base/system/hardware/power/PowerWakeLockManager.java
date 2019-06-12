package com.georgebindragon.base.system.hardware.power;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 类名称：PowerWakeLockManager
 * 类概述：电源管理 基类
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class PowerWakeLockManager
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	protected PowerWakeLockManager()
	{
		powerWakeLock = init();
	}

	protected abstract PowerWakeLock init();

	private boolean notNUll()
	{
		boolean notEmpty = EmptyUtil.notEmpty(powerWakeLock);
		if (!notEmpty) LogProxy.e(TAG, "err: powerWakeLock==null");
		return notEmpty;
	}

	protected PowerWakeLock powerWakeLock;

	public boolean isLockOn() { return notNUll() && powerWakeLock.isLocking(); }

	public boolean isForceKeeping() { return notNUll() && powerWakeLock.isForceKeeping(); }

	public void setForceKeeping(boolean forceKeeping)
	{
		if (notNUll())
		{
			powerWakeLock.setForceKeeping(forceKeeping);
		}
	}

	public void releaseCurrentLock()//当前调用不需要了, 进行释放, 至于最终能否释放, 要看是否还有别人在调用
	{
		if (notNUll()) powerWakeLock.unLock();
	}

	public void makeSureKeepLock()//最终释放
	{
		if (notNUll()) powerWakeLock.setForceKeeping(true);
	}

	public void makeSureReleaseLock()//最终释放
	{
		if (notNUll()) powerWakeLock.makeSureUnLock();
	}

	public void lockForAWhile(long timeInMills)
	{
		if (notNUll())
		{
			if (timeInMills >= 200)
			{
				powerWakeLock.lockForAWhile(timeInMills);
			} else
			{
				powerWakeLock.lockForAWhile(200);
			}
		}
	}
}
