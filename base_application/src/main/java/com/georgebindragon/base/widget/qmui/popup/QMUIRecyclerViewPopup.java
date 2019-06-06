package com.georgebindragon.base.widget.qmui.popup;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;

import com.georgebindragon.base.widget.qmui.QMUIWrapContentRecyclerView;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建人：George
 * 类名称：QMUIRecyclerViewPopup
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class QMUIRecyclerViewPopup extends QMUIPopup
{
	private RecyclerView.Adapter mAdapter;

	/**
	 * 构造方法。
	 *
	 * @param context   传入一个 Context。
	 * @param direction Popup 的方向，为 {@link QMUIPopup#DIRECTION_NONE}, {@link QMUIPopup#DIRECTION_TOP} 和 {@link QMUIPopup#DIRECTION_BOTTOM} 中的其中一个值。
	 * @param adapter   列表的 Adapter
	 */
	public QMUIRecyclerViewPopup(Context context, @Direction int direction, RecyclerView.Adapter adapter)
	{
		super(context, direction);
		mAdapter = adapter;
	}

	/**
	 * @param width     宽度
	 * @param maxHeight 最大高度, 如果使用高度低于最大高度, 则以使用高度为准
	 * @return {@link RecyclerView} 以便于定制它的各种视图属性
	 */
	public RecyclerView create(int width, int maxHeight)
	{
		QMUIWrapContentRecyclerView recyclerView = new QMUIWrapContentRecyclerView(mContext, maxHeight);
		FrameLayout.LayoutParams    lp           = new FrameLayout.LayoutParams(width, maxHeight);
		recyclerView.setLayoutParams(lp);
		recyclerView.setAdapter(mAdapter);
		recyclerView.setVerticalScrollBarEnabled(true);

		LinearLayoutManager manager = new LinearLayoutManager(mContext);
		manager.setOrientation(RecyclerView.VERTICAL);
		recyclerView.setLayoutManager(manager);

		DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation());
		recyclerView.addItemDecoration(mDividerItemDecoration);

		setContentView(recyclerView);
		return recyclerView;
	}

	@Override
	protected Point onShowBegin(View parent, View attachedView)
	{
		Point point = super.onShowBegin(parent, attachedView);
		mArrowUp.setVisibility(needUp ? View.VISIBLE : View.GONE);//设置箭头: 上
		mArrowDown.setVisibility(needDown ? View.VISIBLE : View.GONE);//设置箭头: 下
		return point;
	}

	private boolean needUp   = false;
	private boolean needDown = false;

	protected void setNeedArrow(boolean needUp, boolean needDown)
	{
		this.needUp = needUp;
		this.needDown = needDown;
	}
}
