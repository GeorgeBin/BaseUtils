package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.georgebindragon.base.BaseUtils;

/**
 * 创建人：George
 * 类名称：ResourcesUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ResourcesUtil
{
	private static Resources systemResources;

	public static Bitmap getBitmapFromRes(int drawable)
	{
		Resources res = getSystemRes();
		try
		{
			return BitmapFactory.decodeResource(res, drawable);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		return null;
	}

	public static float getDimensFromRes(int dimens)
	{
		return getDimensFromRes(dimens, 0);
	}

	public static float getDimensFromRes(int dimens, float defaultFloat)
	{
		Resources res       = getSystemRes();
		float     dimensInt = defaultFloat;
		try
		{
			dimensInt = res.getDimension(dimens);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		return dimensInt;
	}


	public static int getColorFromRes(int color)
	{
		Resources res      = getSystemRes();
		int       colorInt = 0;
		try
		{
			colorInt = res.getColor(color);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		return colorInt;
	}

	public static String getStringFromRes(int strings)
	{
		return getStringFromRes(strings, "");
	}

	public static String getStringFromRes(int stringId, String defaultStr)
	{
		Resources res    = getSystemRes();
		String    string = "";
		try
		{
			string = res.getString(stringId);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		if (TextUtils.isEmpty(string))
		{
			return TextUtils.isEmpty(defaultStr) ? "" : defaultStr;
		} else
		{
			return string;
		}
	}

	public static String getStringFromResWithFormat(int stringId, String defaultStr, Object... formatArgs)
	{
		Resources res    = getSystemRes();
		String    string = "";
		try
		{
			string = res.getString(stringId, formatArgs);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		if (TextUtils.isEmpty(string))
		{
			return TextUtils.isEmpty(defaultStr) ? "" : defaultStr;
		} else
		{
			return string;
		}
	}

	public static boolean getBoolFromRes(int resId)
	{
		return getBoolFromRes(resId, false);
	}

	public static boolean getBoolFromRes(int resId, boolean defaultBool)
	{
		boolean result = defaultBool;
		try
		{
			Resources res = getSystemRes();
			result = res.getBoolean(resId);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		return result;
	}

	public static int getIntFromRes(int ids)
	{
		return getIntFromRes(ids, 0);
	}

	public static int getIntFromRes(int ids, int defaultInt)
	{
		int result = defaultInt;
		try
		{
			result = getSystemRes().getInteger(ids);
		} catch (Exception e)
		{
			e.printStackTrace();//有可能会找不到
		}
		return result;
	}


	public static Resources getSystemRes()
	{
		return getSystemRes(BaseUtils.getContext());
	}

	public static Resources getSystemRes(Context systemContext)
	{
		if (null == systemResources)
		{
			if (null == systemContext) systemContext = BaseUtils.getContext();
			systemResources = systemContext.getResources();
		}
		return systemResources;
	}

}
