package com.georgebindragon.base.system.software;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.georgebindragon.base.function.log.LogProxy;

import java.util.Locale;

/**
 * 创建人：George
 * 类名称：LanguageUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LanguageUtil
{
	private static final String TAG = "LanguageUtil-->";

	@SuppressLint("StaticFieldLeak")
	private static Context systemContext;
	private static Locale  systemLocale;//记录系统当前语言

	public static void init(Context context)
	{
		systemContext = context;
		systemLocale = Locale.getDefault();//记录系统当前语言
	}

	public static Locale getAppLanguage()
	{
		//Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
		// Google源码内，给TextView获取字符串使用的local：Locale locale1 = getSystemRes().getConfiguration().getLocales().get(0);
		return Locale.getDefault();
	}

	public static Locale getSystemLanguage()
	{
		return systemLocale;
	}

	public static Context wrapLanguageContext(Context base, Locale newLocale)
	{
		if (null != newLocale)
		{
			Resources     res           = base.getResources();
			Configuration configuration = res.getConfiguration();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				configuration.setLocale(newLocale);
				LocaleList localeList = new LocaleList(newLocale);
				LocaleList.setDefault(localeList);
				configuration.setLocales(localeList);
				base = base.createConfigurationContext(configuration);
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				configuration.setLocale(newLocale);
				base = base.createConfigurationContext(configuration);
			}
		}

		LogProxy.i(TAG, "wrapLanguageContext");
		return base;
	}

	public static  void onSystemLocalChanged(Context context, Intent intent)
	{
		if (null != intent && Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction()))
		{
			LogProxy.i(TAG, "onSystemLocalChanged");

			if (null != context)
			{
				Configuration config = context.getResources().getConfiguration();
				if (null != config)
				{
					LogProxy.i(TAG, "System Language change，config.toString()=" + config.toString());
					LogProxy.i(TAG, "System Language change，config.locale=" + config.locale);

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
					{
						systemLocale = config.getLocales().get(0);
					} else
					{
						systemLocale = config.locale;
					}
				}
			}
		}
	}

	public boolean setLanguage(Locale newLocale)
	{
		Configuration  config = getSystemRes().getConfiguration();
		DisplayMetrics dm     = getSystemRes().getDisplayMetrics();

		Locale oldLocale = getAppLanguage();//app当前语言

		if (null == newLocale) newLocale = getSystemLanguage();//null 则取系统语言

		if (!newLocale.equals(oldLocale))//有变化
		{
			config.locale = newLocale;   //设置res内获取的为
			Locale.setDefault(newLocale);//这个可以让从xml直接获取资源的字段，转换语言
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				config.setLayoutDirection(newLocale);//布局方向
			}
			getSystemRes().updateConfiguration(config, dm);//更新配置
			//语言变更后要做的事情

			LogProxy.i(TAG, "setLanguage local=" + systemContext.getResources().getConfiguration().locale.toString());
			return true;
		}
		return false;
	}

	private Resources getSystemRes()
	{
		return ResourcesUtil.getSystemRes(systemContext);
	}
}
