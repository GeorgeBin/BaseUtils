package com.georgebindragon.base.system.hardware.screen;

import android.content.Context;
import android.content.pm.PackageManager;

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
	public static boolean hasTouchScreen(Context context)
	{
		if (EmptyUtil.notEmpty(context))
		{
			return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
					|| context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
		}
		return false;
	}

//	protected static int screenWidth  = -1;//屏幕的宽
//	protected static int screenHeight = -1;//屏幕的高
//
//	public static void setScreenWidthAndHeight(int width, int height)
//	{
//		screenWidth = width;
//		screenHeight = height;
//	}
//
////	public static void setScreenWidthAndHeightChangeListener(int width, int height)
////	{
////		screenWidth = width;
////		screenHeight = height;
////	}
//
//	public static int getScreenWidth()
//	{
//		return screenWidth;
//	}
//
//	public static int getScreenHeight()
//	{
//		return screenHeight;
//	}
//
//	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//	public static double getScreenInchOfDeviceFromActivity(Activity context)
//	{
//		Point point = new Point();
//		context.getWindowManager().getDefaultDisplay().getRealSize(point);
//		DisplayMetrics dm           = context.getResources().getDisplayMetrics();
//		double         x            = Math.pow(point.x / dm.xdpi, 2);
//		double         y            = Math.pow(point.y / dm.ydpi, 2);
//		double         screenInches = Math.sqrt(x + y);
//		LogProxy.d("ScreenUtil", "Screen inches : " + screenInches);
//		return screenInches;
//	}
//
//
//	public static void getScreenHAndW(Activity context)
//	{
//		DisplayMetrics dm = new DisplayMetrics();
//		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		float width  = dm.widthPixels * dm.density;
//		float height = dm.heightPixels * dm.density;
//	}
//
//	public static void getScreenHAndWWithPixels(Activity context)
//	{
//		WindowManager windowManager = context.getWindowManager();
//		Display       display       = windowManager.getDefaultDisplay();
//		int           width         = display.getWidth();
//		int           height        = display.getHeight();
//	}
//
//	public static float getScreenDensity(Resources resources)
//	{
//		return getDisplayMetrics(resources).density;
//	}
//
//	public static int getScreenDPI(Resources resources)
//	{
//		return getDisplayMetrics(resources).densityDpi;
//	}
//
//	public static DisplayMetrics getDisplayMetrics(Resources resources)
//	{
//		return resources.getDisplayMetrics();
//	}
//
//	public static float dpToPx(Resources resources,float dp)
//	{
//		return dp * getScreenDensity(resources);
//	}
//
//	public static float pxToDp(Resources resources,float px)
//	{
//		if(px>0) {
//			return px / getScreenDensity(resources);
//		}else {
//			return 0;
//		}
//	}
//
//	public static int dpToPxInt(float dp)
//	{
//		return (int) (dpToPx(dp) + 0.5f);
//	}
//
//	public static int pxToDpCeilInt(Context context, float px)
//	{
//		return (int) (pxToDp( px) + 0.5f);
//	}
}
