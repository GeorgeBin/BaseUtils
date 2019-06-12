package com.georgebindragon.base.java;


import com.georgebindragon.base.data.format.FormatTimeUtil;
import com.georgebindragon.base.function.log.LogFunctionBaseImp;
import com.georgebindragon.base.function.log.LogLevel;
import com.georgebindragon.base.utils.TimeUtil_Java;

/**
 * 创建人：George
 * 类名称：LogFunctionJavaImp
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LogFunctionJavaImp extends LogFunctionBaseImp
{
	private static final LogFunctionJavaImp ourInstance = new LogFunctionJavaImp();

	public static LogFunctionJavaImp getInstance() { return ourInstance; }

	private LogFunctionJavaImp()
	{ }

	@Override
	public void logPrint(int level, String tag, String log)
	{
		StringBuilder stringBuilder = new StringBuilder();

		String timeString = FormatTimeUtil.formatTimeToString("MM-dd HH:mm:ss.SSS", TimeUtil_Java.getMilliSeconds());
		stringBuilder.append(timeString);
		stringBuilder.append(":\t");

		if (null != tag && tag.length() > 0)
		{
			stringBuilder.append(tag);
		}

		if (null != log && log.length() > 0)
		{
			stringBuilder.append(log);
		}

		String logs = stringBuilder.toString();
		System.out.println(logs);
	}

	@Override
	public boolean needLogProcess() { return false; }

	@Override
	protected boolean defaultEnable() { return true; }

	@Override
	protected int defaultLevel() { return LogLevel.ALL; }

	@Override
	protected int defaultLogLength() { return 100; }

	@Override
	protected String defaultLogSeparator() { return "\t"; }

	@Override
	protected String defaultLogHead() { return ""; }

	@Override
	protected String defaultTAG() { return "RongChat"; }
}
