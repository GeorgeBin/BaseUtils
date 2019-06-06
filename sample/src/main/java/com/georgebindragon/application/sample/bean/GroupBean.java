package com.georgebindragon.application.sample.bean;

/**
 * 创建人：George
 * 类名称：GroupBean
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class GroupBean
{
	private int     gid;
	private boolean isOn;
	private String  name;

	public GroupBean(int gid, boolean isOn, String name)
	{
		this.gid = gid;
		this.isOn = isOn;
		this.name = name;
	}

	public int getGid()
	{
		return gid;
	}

	public boolean isOn()
	{
		return isOn;
	}

	public String getName()
	{
		return name;
	}
}
