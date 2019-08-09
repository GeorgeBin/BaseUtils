package com.georgebindragon.base.system.hardware.screen;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：ScreenUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ScreenUtil
{
	private static final String TAG = "ScreenUtil-->";

	public static boolean hasTouchScreen(Context context)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
				|| context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
	}

	public static int getRealScreenWidth(Context context)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		return doGetRealScreenSize(context)[0];
	}

	public static int getRealScreenHeight(Context context)
	{
		if (EmptyUtil.isEmpty(context)) context = BaseUtils.getContext();
		return doGetRealScreenSize(context)[1];
	}

	private static int[] doGetRealScreenSize(Context context)
	{
		int[]          size    = new int[2];
		int            widthPixels, heightPixels;
		WindowManager  w       = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display        d       = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		// since SDK_INT = 1;
		widthPixels = metrics.widthPixels;
		heightPixels = metrics.heightPixels;
		try
		{
			// used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
			widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
			heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "doGetRealScreenSize1", e);
		}
		if (Build.VERSION.SDK_INT >= 17)
		{
			try
			{
				// used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
				Point realSize = new Point();
				d.getRealSize(realSize);

				Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
				widthPixels = realSize.x;
				heightPixels = realSize.y;
			} catch (Exception e)
			{
				LogProxy.e(TAG, "doGetRealScreenSize2", e);
			}
		}

		size[0] = widthPixels;
		size[1] = heightPixels;
		return size;
	}
}
