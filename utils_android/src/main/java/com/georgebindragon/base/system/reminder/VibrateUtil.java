package com.georgebindragon.base.system.reminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;

/**
 * 创建人：George
 * 类名称：VibrateUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("MissingPermission")
public class VibrateUtil
{
	private static final String   TAG       = "VibrateUtil-->";
	private static       Vibrator vibrator;
	public static final  int      NO_REPEAT = -1;//no repeat

	public static Vibrator getVibrator(Context context)
	{
		if (vibrator == null) vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		return vibrator;
	}

	/**
	 * Vibrate constantly for the specified period of time.
	 * <p>This method requires the caller to hold the permission
	 * {@link android.Manifest.permission#VIBRATE}.
	 *
	 * @param milliseconds The number of milliseconds to vibrate.
	 */
	public static void vibrate(Context context, long milliseconds)
	{
		if (getVibrator(context) != null) vibrator.vibrate(milliseconds);
	}


	public static void vibrate(Context context, long[] pattern)
	{
		vibrate(context, pattern, NO_REPEAT);//no repeat
	}

	/**
	 * Vibrate with a given pattern.
	 * <p/>
	 * <p>
	 * Pass in an array of ints that are the durations for which to turn on or off
	 * the vibrator in milliseconds.  The first value indicates the number of milliseconds
	 * to wait before turning the vibrator on.  The next value indicates the number of milliseconds
	 * for which to keep the vibrator on before turning it off.  Subsequent values alternate
	 * between durations in milliseconds to turn the vibrator off or to turn the vibrator on.
	 * </p><p>
	 * To cause the pattern to repeat, pass the index into the pattern array at which
	 * to start the repeat, or -1 to disable repeating.
	 * </p>
	 * <p>This method requires the caller to hold the permission
	 * {@link android.Manifest.permission#VIBRATE}.
	 *
	 * @param pattern an array of longs of times for which to turn the vibrator on or off.
	 * @param repeat  the index into pattern at which to repeat, or -1 if
	 *                you don't want to repeat.
	 */
	public static void vibrate(Context context, long[] pattern, int repeat)
	{
		if (getVibrator(context) != null) vibrator.vibrate(pattern, repeat);
	}

	public static void cancel()
	{
		if (vibrator != null) vibrator.cancel();
	}
}