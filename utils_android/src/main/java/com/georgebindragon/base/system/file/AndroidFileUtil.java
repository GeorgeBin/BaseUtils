package com.georgebindragon.base.system.file;

import android.content.Context;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建人：George
 * 类名称：AndroidFileUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AndroidFileUtil
{
	private static final String TAG = "AndroidFileUtil-->";

	public static InputStream getAssetsInputStream(Context context, String sourceAssetsFileName)
	{
		try
		{
			return context.getResources().getAssets().open(sourceAssetsFileName);
		} catch (IOException e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getAssetsInputStream", e);
		}
		return null;
	}

	public static String getCachePath(Context context)
	{
		if (null == context) context = BaseUtils.getContext();
		if (null != context)
		{
			String cachePath = InternalFileUtil.getDataDataCachePath(context);// /data/data/程序的包名/cache
			if (ExternalFileUtil.isExternalStorageMounted())
			{
				File externalCacheDir = context.getExternalCacheDir();
				if (null != externalCacheDir)
				{
					cachePath = externalCacheDir.getPath();// /SD卡/Android/data/程序的包名/cache
				}
			}
			return cachePath;
		}
		return null;
	}
}
