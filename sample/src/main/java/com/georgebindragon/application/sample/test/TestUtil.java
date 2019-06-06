package com.georgebindragon.application.sample.test;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;

import com.georgebindragon.base.function.log.LogProxy;

import java.util.Locale;

import androidx.multidex.MultiDex;

/**
 * 创建人：George
 * 类名称：TestUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class TestUtil
{
	private static final String TAG = "TestUtil-->";

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

		LogProxy.d(TAG, "wrapLanguageContext");
		return base;
	}
}
