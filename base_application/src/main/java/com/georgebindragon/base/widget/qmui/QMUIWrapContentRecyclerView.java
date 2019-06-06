package com.georgebindragon.base.widget.qmui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建人：George
 * 类名称：QMUIWrapContentRecyclerView
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class QMUIWrapContentRecyclerView extends RecyclerView
{
	private int mMaxHeight = Integer.MAX_VALUE >> 2;

	public QMUIWrapContentRecyclerView(Context context)
	{
		super(context);
	}

	public QMUIWrapContentRecyclerView(Context context, int maxHeight)
	{
		super(context);
		mMaxHeight = maxHeight;
	}

	public QMUIWrapContentRecyclerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public QMUIWrapContentRecyclerView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public void setMaxHeight(int maxHeight)
	{
		if (mMaxHeight != maxHeight)
		{
			mMaxHeight = maxHeight;
			requestLayout();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = View.MeasureSpec.makeMeasureSpec(mMaxHeight, View.MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
