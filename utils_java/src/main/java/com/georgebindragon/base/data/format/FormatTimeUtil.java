package com.georgebindragon.base.data.format;

import com.georgebindragon.base.utils.EmptyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 2017/4/26
 */

public class FormatTimeUtil
{
	public static String formatTimeToFullString(long timeStamp)
	{
		return formatTimeToString("yyyy-MM-dd_HH-mm-ss-SSS", timeStamp);
	}

	public static String formatTimeToString(long timeStamp)
	{
		return formatTimeToString("yyyy-MM-dd HH:mm:ss", timeStamp);
	}

	public static String formatTimeToDayString(long timeStamp)
	{
		return formatTimeToString("yyyy-MM-dd", timeStamp);
	}

	public static String formatTimeToString(String pattern, long timeStamp)
	{
		String result;
		if (EmptyUtil.isEmpty(pattern)) pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
		if (timeStamp < 0)
		{
			result = simpleDateFormat.format(new Date());
		} else
		{
			result = simpleDateFormat.format(new Date(timeStamp));
		}
		return null == result ? "" : result;//不返回null值
	}

	public static long convertDayStringToLong(String dayString)
	{
		return convertStringToLong("yyyy-MM-dd", dayString);
	}

	public static long convertStringToLong(String pattern, String date)
	{
		try
		{
			if (EmptyUtil.notEmpty(date))
			{
				if (EmptyUtil.isEmpty(pattern)) pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sf = new SimpleDateFormat(pattern, Locale.CHINA);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public static String formatSecondToString(int second)
	{
		String strHour   = "H";
		String strMin    = "M";
		String strSecond = "S";
		String result;
		if (second > 0)
		{
			int hours = second / (60 * 60);
			int min   = second / 60;
			int s     = second % 60;

			result = "" + (hours > 0 ? hours + strHour : "") + (min > 0 ? min + strMin : "") + (s > 0 ? s + strSecond : "");
		} else
		{
			result = "0 " + strSecond;
		}
		return result;
	}
}
