package com.georgebindragon.base.system.timer;

import java.util.Arrays;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class TimerConfig
{
	private static final String TAG = "TimerConfig-->";

	private String  key;
	private long    repeatTimeMillis;
	private boolean needRepeat;

	public TimerConfig(String key, long repeatTimeMillis, boolean needRepeat)
	{
		this.key = key;
		this.repeatTimeMillis = repeatTimeMillis;
		this.needRepeat = needRepeat;
	}

	public String getKey()
	{
		return key;
	}

	public long getRepeatTimeMillis()
	{
		return repeatTimeMillis;
	}

	public boolean isNeedRepeat()
	{
		return needRepeat;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof TimerConfig)) return false;
		TimerConfig that = (TimerConfig) o;
		return repeatTimeMillis == that.repeatTimeMillis && needRepeat == that.needRepeat && key.equals(that.key);
	}

	@Override
	public int hashCode()
	{
		Object[] objects = new Object[]{key, repeatTimeMillis, needRepeat};
		return Arrays.hashCode(objects);
	}

	@Override
	public String toString()
	{
		return "TimerConfig{" +
				"key='" + key + '\'' +
				", repeatTimeMillis=" + repeatTimeMillis +
				", needRepeat=" + needRepeat +
				'}';
	}
}