package com.georgebindragon.base.system.file;

import android.content.Context;

import com.georgebindragon.base.abilities.callbacks.ResultCallBack;
import com.georgebindragon.base.algorithm.MD5Util;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 创建人：George
 * 类名称：CommonFileUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CommonFileUtil
{
	private static final String TAG = "CommonFileUtil-->";

	//拷贝Assets文件到某处
	public static void copyAssetsFileToPathInThread(Context context, String sourceMD5, String sourceAssetsFileName, String savePath, final ResultCallBack copyResultCallBack)
	{
		final Thread copyThread = new Thread(() ->
		{
			LogProxy.d(TAG, "copyAssetsFileToPathInThread");
			copyAssetsFileToPath(context, sourceMD5, sourceAssetsFileName, savePath, copyResultCallBack);
		});
		copyThread.start();
	}

	//拷贝Assets文件到某处
	public static void copyAssetsFileToPath(Context context, String sourceMD5, String sourceAssetsFileName, String savePath, final ResultCallBack copyResultCallBack)
	{
		LogProxy.d(TAG, "copyAssetsFileToPath");

		boolean destFileExists = FileCheckUtil.isFileExists(savePath);
		LogProxy.d(TAG, "copyAssetsFileToPath-->目标是否已经存在? =" + (destFileExists ? "存在" : "不存在"));

		if (destFileExists)
		{
			String destMd5 = MD5Util.getFileMd5(savePath);
			if (EmptyUtil.notEmpty(sourceMD5, destMd5))
			{
				if (sourceMD5.equalsIgnoreCase(destMd5))
				{
					if (null != copyResultCallBack) copyResultCallBack.onResult(true);
					LogProxy.d(TAG, "copyAssetsFileToPath-->MD5值符合-->直接返回");
					return;
				}
			}
		}

		LogProxy.d(TAG, "copyAssetsFileToPath-->需要复制");

		boolean createDir = FileCreateUtil.createFile(savePath);
		if (createDir)
		{
			File file = new File(savePath);
			if (!file.exists())
			{
				try
				{
					InputStream      assetsInputStream = AndroidFileUtil.getAssetsInputStream(context, sourceAssetsFileName);
					FileOutputStream out               = new FileOutputStream(savePath);
					FileCopyUtil.copyWithStream(assetsInputStream, out, copyResultCallBack);
				} catch (Exception e)
				{
					e.printStackTrace();
					LogProxy.d(TAG, "copyAssetsFileToPath-->close-->" + e.getMessage());
				}
			}
		}
	}
}
