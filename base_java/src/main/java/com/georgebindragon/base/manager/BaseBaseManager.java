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


public abstract class BaseBaseManager
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	public abstract boolean init();
}
