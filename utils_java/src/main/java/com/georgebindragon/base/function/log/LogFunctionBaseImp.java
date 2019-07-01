package com.georgebindragon.base.function.log;


import com.georgebindragon.base.utils.StringUtil;


/**
 * 用于屏蔽系统的Log，可以进行全局控制，在发版之前，进行屏蔽，发版前，设置为false
 */
public abstract class LogFunctionBaseImp implements LogFunctionI
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	//外部方法:
	@Override
	public void setLogVisibility(boolean isLogEnable)
	{
		defaultEnable = isLogEnable;
	}

	@Override
	public boolean isLogEnable()
	{
		return defaultEnable;
	}

	@Override
	public void setLogLevel(int level)
	{
		LOG_LEVEL = level;
	}

	//WTF         = 7;//Log.wtf
	@Override
	public void wtf(String TAG, String... strings)
	{
		androidLog(LogLevel.WTF, TAG, strings);
	}

	//ERROR       = 6;//Log.e
	@Override
	public void e(String TAG, String... strings)
	{
		androidLog(LogLevel.ERROR, TAG, strings);
	}

	//专门打Exception的log
	@Override
	public void e(String TAG, String method, Exception e)
	{
		e(TAG, method, "-->", (null == e ? "Exception" : e.getMessage()));
	}

	//WARN = 5;  //Log.w
	@Override
	public void w(String TAG, String... strings)
	{
		androidLog(LogLevel.WARN, TAG, strings);
	}

	//INFO = 3;  //Log.i
	@Override
	public void i(String TAG, String... strings)
	{
		androidLog(LogLevel.INFO, TAG, strings);
	}

	//DEBUG = 2;  //Log.d
	@Override
	public void d(String TAG, String... strings)
	{
		androidLog(LogLevel.DEBUG, TAG, strings);
	}

	//VERBOSE = 1;  //Log.v
	@Override
	public void v(String TAG, String... strings)
	{
		androidLog(LogLevel.VERBOSE, TAG, strings);
	}

	//内部方法:
	private boolean isVisible(int level)
	{
		return defaultEnable && level >= LOG_LEVEL;
	}

	//内部使用
	private void androidLog(int level, String tags, String... strings)
	{
		if (isVisible(level))
		{
			String        tag  = LOG_DefaultTAG;
			StringBuilder info = new StringBuilder();
			info.append(LOG_HEAD);//添加头部
			if (null != strings && strings.length > 0)//有效参数
			{
				for (String msg : strings)
				{
					if (null != msg && msg.length() > 0)//有效参数
					{
						info.append(StringUtil.getPrintString(msg));
						info.append(LOG_SEPARATOR);
					}
				}
				tag = StringUtil.getPrintString(tags, LOG_DefaultTAG);
			} else
			{
				info.append(tags);//如果没有内容,则将TAG作为内容,打印出来
			}
			String msg = info.toString();

			if (needLogProcess())
			{
				logProcess(level, tag, msg);//分隔处理
			} else
			{
				logPrint(level, tag, msg);//直接打印
			}
		}
	}

	//log处理
	private void logProcess(int level, String tag, String msg)
	{
		int length = msg.length();
		if (length > LOG_Length)
		{
			int times = 20;
			int start = 0;
			int end   = LOG_Length;

			for (int i = 0; i < times; i++)
			{
				if (length > end)
				{
					String substring = msg.substring(start, end);
					logPrint(level, tag, substring);

					start = end;
					end = end + LOG_Length;
				} else
				{
					end = length;
					String substring = msg.substring(start, end);
					logPrint(level, tag, substring);
					break;
				}
			}
		} else if (length > 0)
		{
			logPrint(level, tag, msg);//短log, 直接打印
		}
	}

	//log打印
	public abstract void logPrint(int level, String tag, String msg);

	public abstract boolean needLogProcess();


	protected boolean defaultEnable  = defaultEnable();         //是否显示log：true显示、false不显示
	protected int     LOG_LEVEL      = defaultLevel();          // 默认log级别=所有
	protected String  LOG_SEPARATOR  = defaultLogSeparator();   // 分割线，默认为一个制表符
	protected String  LOG_HEAD       = defaultLogHead();        // 头，默认为一个制表符
	protected int     LOG_Length     = defaultLogLength();      //单条log的长度限制
	protected String  LOG_DefaultTAG = defaultTAG();            // 默认的tag


	protected abstract boolean defaultEnable();

	protected abstract int defaultLevel();

	protected abstract int defaultLogLength();

	protected abstract String defaultLogSeparator();

	protected abstract String defaultLogHead();

	protected abstract String defaultTAG();
}
