package com.georgebindragon.base.system.hardware.power;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.TimeUtil;

public class PowerWakeLock
{
	protected String TAG = "" + getClass().getName() + "-->";

	protected PowerManager.WakeLock wakeLock = null;
	protected Handler               mHandler = null;
	protected Runnable              releaser = this::unLock;

	private boolean isForceKeeping = false;//是否 强制保持

	public boolean isForceKeeping() { return isForceKeeping; }

	public void setForceKeeping(boolean forceKeeping)
	{
		if (isForceKeeping != forceKeeping)
		{
			LogProxy.d(TAG, "setForceKeeping-->", "forceKeeping=" + forceKeeping);
			isForceKeeping = forceKeeping;
			if (isForceKeeping)
			{
				lock();
			}
		}
	}

	protected PowerWakeLock(final Context context, int levelAndFlag)
	{
		LogProxy.d(TAG, "构造函数");
		final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if (null != pm)
		{
			wakeLock = pm.newWakeLock(levelAndFlag, TAG);
			wakeLock.setReferenceCounted(false);
			mHandler = new Handler(context.getMainLooper());
		}
	}

	@Override
	protected void finalize()
	{
		LogProxy.d(TAG, "finalize");
		unLock();
	}

	private long longest = TimeUtil.getMilliSecondsSinceBoot();

	public void lockForAWhile(final long timeInMills)
	{
		if (timeInMills > 0)
		{
			long current = TimeUtil.getMilliSecondsSinceBoot();
			long next    = current + timeInMills;
			if (next > longest) longest = next;
			lock();
			mHandler.postDelayed(releaser, timeInMills);
			LogProxy.d(TAG, "lockForAWhile=" + timeInMills, "longest=" + longest);
		}
	}

	@SuppressLint("WakelockTimeout")
	private void lock()
	{
		LogProxy.d(TAG, "lock");
		wakeLock.acquire();
	}

	protected void unLock()
	{
		LogProxy.d(TAG, "unLock");
		if (isLocking())//当前锁着
		{
			LogProxy.d(TAG, "unLock isForceKeeping=" + isForceKeeping);
			long current = TimeUtil.getMilliSecondsSinceBoot();
			LogProxy.d(TAG, "unLock current=" + current, "longest=" + longest);
			if (!isForceKeeping && (current >= (longest - 500)))//没有强制保持 && 超过最远的保持时间(减去 500ms 避免时间误差)
			{
				wakeLock.release();// 释放
				LogProxy.d(TAG, "unLock release掉了");
			}
		}
	}

	protected void makeSureUnLock()
	{
		LogProxy.d(TAG, "make Sure UnLock ");
		mHandler.removeCallbacks(releaser);
		isForceKeeping = false;
		longest = TimeUtil.getMilliSecondsSinceBoot();

		unLock();
	}

	public boolean isLocking()
	{
		LogProxy.d(TAG, "isLocking() isLocking=" + wakeLock.isHeld());
		return wakeLock.isHeld();
	}
}
