package com.georgebindragon.application.sample.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.georgebindragon.application.sample.R;
import com.georgebindragon.application.sample.bean.GroupBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 创建人：George
 * 类名称：PopListAdapter
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PopListAdapter extends BaseQuickAdapter<GroupBean, BaseViewHolder>
{
	public PopListAdapter(int layoutResId, @Nullable List<GroupBean> data)
	{
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, GroupBean item)
	{
		helper.setText(R.id.text222, item.getName());
	}
}
