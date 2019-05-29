package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.res.Resources;

import com.georgebindragon.base.BaseUtils;

/**
 * 创建人：George
 * 类名称：ResourcesUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ResourcesUtil
{
	private static Resources systemResources;

	public static Resources getSystemRes(Context systemContext)
	{
		if (null == systemResources)
		{
			if (null == systemContext) systemContext = BaseUtils.getContext();
			systemResources = systemContext.getResources();
		}
		return systemResources;
	}
}
