package com.georgebindragon.base.manager;


/**
 * 类名称：BaseManagerImp
 * 类概述：管理者，基类
 * 详细描述：
 *
 * 管理者负责：
 * 1. 初始化
 * 2. 设置+获取 Adapter
 * 3. 开+关 控制
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseManagerImp<T> extends BaseManager<T>
{

	protected BaseManagerImp()
	{
		super();
		adapter = getAdapterBaseImp();
		System.out.println(TAG + "构造函数被调用");
	}

	protected abstract T getAdapterBaseImp();

	@Override
	public boolean init()
	{
		//进行初始化
		return true;
	}

	protected volatile T adapter;

	@Override
	public T getAdapter()
	{
		if (null == adapter) adapter = getAdapterBaseImp();
		return adapter;
	}

	@Override
	public void setAdapter(T adapter)
	{
		if (null != adapter) this.adapter = adapter;
	}

}
