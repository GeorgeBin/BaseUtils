package com.georgebindragon.base.system.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.georgebindragon.base.BaseUtils;

/**
 * 创建人：George
 * 类名称：ViewUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ViewUtil
{
	private static final String TAG = "ViewUtil-->";

	public static View getView(Context context, int res, ViewGroup rootView)
	{
		if (null == context) context = BaseUtils.getContext();

		LayoutInflater from = LayoutInflater.from(context);
		return from.inflate(res, rootView);
	}
}