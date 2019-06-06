package com.georgebindragon.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 创建人：George
 * 类名称：MarqueeTextView
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MarqueeTextView extends AppCompatTextView
{

	public MarqueeTextView(Context context)
	{
		super(context);
	}

	public MarqueeTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isSelected()
	{
		return true;// always selected will make it marquee forever
	}
}
