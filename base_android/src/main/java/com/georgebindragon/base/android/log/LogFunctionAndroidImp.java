package com.georgebindragon.base.android.log;


import android.util.Log;

import com.georgebindragon.base.android.BuildConfig;
import com.georgebindragon.base.function.log.LogFunctionBaseImp;
import com.georgebindragon.base.function.log.LogLevel;


/**
 * 用于屏蔽系统的Log，可以进行全局控制，在发版之前，进行屏蔽，发版前，设置为false
 */
public class LogFunctionAndroidImp extends LogFunctionBaseImp
{
	private static final LogFunctionAndroidImp ourInstance = new LogFunctionAndroidImp();

	public static LogFunctionAndroidImp getInstance() { return ourInstance; }

	private LogFunctionAndroidImp()
	{ }

	@Override
	public void logPrint(int level, String tag, String msg)
	{
		switch (level)
		{
			case LogLevel.ALL:
			case LogLevel.VERBOSE:
				Log.v(tag, msg);
				break;
			case LogLevel.DEBUG:
				Log.d(tag, msg);
				break;
			case LogLevel.INFO:
				Log.i(tag, msg);
				break;
			case LogLevel.WARN:
				Log.w(tag, msg);
				break;
			case LogLevel.ERROR:
				Log.e(tag, msg);
				break;
			case LogLevel.ASSERT:
				Log.wtf(tag, msg);
				break;
			default:
				Log.d(tag, msg);
				break;
		}
	}

	@Override
	public boolean needLogProcess()
	{
		return false;
	}

	@Override
	protected boolean defaultEnable()
	{
		return BuildConfig.DEBUG;
	}

	@Override
	protected int defaultLevel()
	{
		return LogLevel.ALL;
	}

	@Override
	protected int defaultLogLength()
	{
		return 100;
	}

	@Override
	protected String defaultLogSeparator()
	{
		return "\t";
	}

	@Override
	protected String defaultLogHead()
	{
		return "";
	}

	@Override
	protected String defaultTAG()
	{
		return "RongChat";
	}
}
