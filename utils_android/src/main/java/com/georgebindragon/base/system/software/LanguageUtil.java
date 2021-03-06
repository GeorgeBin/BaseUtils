package com.georgebindragon.base.system.software;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Locale;

/**
 * 创建人：George
 * 类名称：LanguageUtil
 * 类概述：
 *
 * 调整系统内语言过程：
 * 1. 初始化，读取App语言配置信息（跟随系统、中文、English）
 * 2. 代码设置App内自定义语言
 * （Android-5.1 已经可以转换系统语言了）
 * （Android-9 只有通过代码获取的字符串可以）
 *
 * 3. 更改Context：Application、Activity、Service
 * 4. 系统语言变化回调：Application、Activity 检测当前App内语言，是否符合预期
 * 5. 监听系统语言变化广播：变化时，检查是否需要跟随变更
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

	public static void init(Context context)
	{
		systemContext = context;
	}

	public static Locale getAppLanguage()
	{
		//Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
		// Google源码内，给TextView获取字符串使用的local：Locale locale1 = getSystemRes().getConfiguration().getLocales().get(0);
		return Locale.getDefault();
	}

	public static Locale getSystemLanguage()
	{
		Locale locale;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
		{
			locale = Resources.getSystem().getConfiguration().getLocales().get(0);
		} else
		{
			locale = Resources.getSystem().getConfiguration().locale;
		}
		return locale;
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

		LogProxy.i(TAG, "wrapLanguageContext-->" + StringUtil.getPrintString(newLocale));
		return base;
	}

	public static Configuration wrapLanguageConfiguration(Configuration configuration, Locale newLocale)
	{
		if (null != newLocale)
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				configuration.setLocale(newLocale);
				LocaleList localeList = new LocaleList(newLocale);
				LocaleList.setDefault(localeList);
				configuration.setLocales(localeList);
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				configuration.setLocale(newLocale);
			}
		}

		LogProxy.i(TAG, "wrapLanguageConfiguration-->" + StringUtil.getPrintString(newLocale));
		return configuration;
	}

	public static boolean setLanguage(Locale newLocale)
	{
		Configuration  config = getSystemRes().getConfiguration();
		DisplayMetrics dm     = getSystemRes().getDisplayMetrics();

		if (null == newLocale) newLocale = getSystemLanguage();//null 则取系统语言
		config.locale = newLocale;   //设置res内获取的为
		Locale.setDefault(newLocale);//这个可以让从xml直接获取资源的字段，转换语言
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
		{
			config.setLayoutDirection(newLocale);//布局方向
		}
		getSystemRes().updateConfiguration(config, dm);//更新配置
		//语言变更后要做的事情
		LogProxy.i(TAG, "setLanguage newLocale=" + StringUtil.getPrintString(newLocale));
		return true;
	}

	public static Resources getSystemRes()
	{
		if (null == systemContext) systemContext = BaseUtils.getContext();
		return ResourcesUtil.getSystemRes(systemContext);
	}
}
