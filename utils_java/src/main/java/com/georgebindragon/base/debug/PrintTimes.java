package com.georgebindragon.base.debug;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建人：George
 * 类名称：PrintOnce
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PrintTimes
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	protected AtomicInteger counter = new AtomicInteger(1);
	protected int           times;

	public PrintTimes() { times = 1; }

	public PrintTimes(int times) { this.times = times; }

	public int getTimes() { return times; }

	public int getCurrentCount() { return counter.intValue(); }

	public boolean canPrint() { return counter.intValue() <= times; }

	public void print(String tag, String... msg)
	{
		if (EmptyUtil.isEmpty(tag)) tag = TAG;

		if (canPrint())
		{
			counter.getAndIncrement();
			LogProxy.d(tag, msg);
		} else
		{
			alreadyPrint(tag, msg);
		}
	}

	protected void alreadyPrint(String tag, String... msg)
	{
		// 可以重写，已经打印过一次的
	}
}
