package com.georgebindragon.base.system.reminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.thread.HandlerUtil;
import com.georgebindragon.base.thread.ThreadUtil;
import com.georgebindragon.base.utils.EmptyUtil;


/**
 * 创建人：George
 * 类名称：ToastUtil
 * 类概述：toast提示工具
 * 详细描述：
 *
 * on Android API 25，there is a risk that toast may throw a BadTokenException out
 * 灵感来源：项目 https://github.com/drakeet/ToastCompat
 * 版本：2018/07/22  79c629c2ca8b2d7803f6dc4a458971b5fa19e7f7
 *
 * 1. 取消当前提示，再提示下一个
 * 2. 可以在非主线程弹出
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class ToastUtil
{
	private static final String TAG   = "ToastUtil-->";
	private static       Toast  toast = null;

	public static void toast(Context context, int resId)
	{
		toastShow(context, resId, Toast.LENGTH_LONG, true);
	}

	public static void toast(Context context, String msg)
	{
		toastShow(context, msg, Toast.LENGTH_LONG, true);
	}

	@SuppressLint("ShowToast")
	public static void toastShow(Context context, CharSequence msg, int duration, boolean needCancelOld)
	{
		toastShow(context, null, 0, msg, duration, needCancelOld);
	}

	@SuppressLint("ShowToast")
	public static void toastShow(Context context,  int resId, int duration, boolean needCancelOld)
	{
		toastShow(context, null, resId, null, duration, needCancelOld);
	}

	@SuppressLint("ShowToast")
	public static void toastShow(Context context, View view, int resId, CharSequence msg, int duration, boolean needCancelOld)
	{
		try
		{
			if (ThreadUtil.isMainThread())
			{
				LogProxy.d(TAG, "主线程");
				cancelToast(needCancelOld);
				makeText(context, duration);
				if (EmptyUtil.notEmpty(view)) toast.setView(view);
				if (EmptyUtil.notEmpty(resId)) toast.setText(resId);
				if (EmptyUtil.notEmpty(msg)) toast.setText(msg);
				showToast(duration);
			} else
			{
				LogProxy.d(TAG, "非主线程，需要post出去");
				HandlerUtil.postRunnableOnMainThread(() ->
				{
					cancelToast(needCancelOld);
					makeText(context, duration);
					if (EmptyUtil.notEmpty(view)) toast.setView(view);
					if (EmptyUtil.notEmpty(resId)) toast.setText(resId);
					if (EmptyUtil.notEmpty(msg)) toast.setText(msg);
					showToast(duration);
				});
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "toastShow -all", e);
		}
	}

	@SuppressLint("ShowToast")
	private static void makeText(Context context, int duration)
	{
		toast = Toast.makeText(context, "", duration);
	}

	private static void cancelToast(boolean needCancelOld)
	{
		if (EmptyUtil.notEmpty(toast) && needCancelOld) toast.cancel();
	}

	private static void showToast(int duration)
	{
		if (EmptyUtil.notEmpty(toast))
		{
			duration = (duration == Toast.LENGTH_LONG ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			toast.setDuration(duration);
			LogProxy.d(TAG, "toast, duration=" + (Toast.LENGTH_SHORT == duration ? "SHORT" : "LONG"));
			toast.show();
		}
	}
}
