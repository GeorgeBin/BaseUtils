package com.georgebindragon.base.system.file;

import android.annotation.SuppressLint;
import android.content.Context;

import com.georgebindragon.base.function.log.LogProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
			return null;
		}
	}


	public static String getDataDataFilesPath(Context context)
	{
		try
		{
			//指向/data/data/包名/files/
			return context.getFilesDir().getAbsolutePath();
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getDataDataFilesPath", e);
			return null;
		}
	}

	@SuppressLint("WorldWriteableFiles")
	public static OutputStream getDataDataFilesOutputStream(Context context, String sourceAssetsFileName)
	{
		try
		{
			//指向/data/data/包名/files/
			return context.openFileOutput(sourceAssetsFileName, Context.MODE_PRIVATE | Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getDataDataFilesOutputStream", e);
			return null;
		}
	}

	public static InputStream getDataDataFilesInputStream(Context context, String sourceAssetsFileName)
	{
		try
		{
			//指向/data/data/包名/files/
			return context.openFileInput(sourceAssetsFileName);
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "getDataDataFilesInputStream", e);
			return null;
		}
	}
}
