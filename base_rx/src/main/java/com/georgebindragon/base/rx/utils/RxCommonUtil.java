package com.georgebindragon.base.rx.utils;


import com.georgebindragon.base.function.log.LogProxy;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 * 类名称：RxCommonUtil
 * 类概述：RxJava 的通用工具
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RxCommonUtil
{
	private static final String TAG = "RxCommonUtil-->";

	public static void dispose(Disposable disposable)
	{
		LogProxy.d(TAG , "dispose-->Disposable");
		if (null != disposable && !disposable.isDisposed()) disposable.dispose();
	}

	public static void dispose(Disposable ...disposables)
	{
		LogProxy.d(TAG , "dispose-->Disposable[]");
		for (Disposable de : disposables)
		{
			dispose(de);
		}
	}

	public static void dispose(List<Disposable> disposableList)
	{
		LogProxy.d(TAG , "dispose-->List<Disposable>");
		if (null != disposableList && disposableList.size() > 0)
		{
			for (Disposable de : disposableList)
			{
				dispose(de);
			}
		}
	}
}
