package com.georgebindragon.base.system.file;

import android.content.Context;

import com.georgebindragon.base.abilities.callbacks.ResultCallBack;
import com.georgebindragon.base.algorithm.MD5Util;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建人：George
 * 类名称：FileMoveUtil
 * 类概述：
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileMoveUtil
{

	private static final String TAG = "FileMoveUtil-->";

	/**
	 * 将APK的资源文件拷贝到SD卡中
	 *
	 * @param needCover            是否覆盖已存在的目标文件(MD5值不相同的情况下)
	 * @param sourceMD5            源文件的MD5 不需要校验，则可以给空
	 * @param sourceAssetsFileName Assets内文件名
	 * @param destPath             要复制的位置
	 */
	public static void copyFromAssets(Context context, boolean needCover, String sourceMD5, String sourceAssetsFileName, String destPath, ResultCallBack resultCallBack)
	{
		if (EmptyUtil.isEmpty(destPath) || EmptyUtil.isEmpty(sourceMD5))
		{
			if (null != resultCallBack) resultCallBack.onResult(false);
			return;
		}

		final Thread copyThread = new Thread(() -> checkFileExitsAndMD5(sourceMD5, destPath, isSuccess ->
		{
			if (isSuccess)
			{
				if (null != resultCallBack) resultCallBack.onResult(true);
			} else
			{
				boolean createParent = FileCreateUtil.createParentDirectory(destPath);
				boolean fileExists   = FileCheckUtil.isFileExists(destPath);

				if (createParent && (!fileExists || needCover))//父目录创建成果 + 文件不存在 或 需要覆盖
				{
					try
					{
						InputStream      in  = AndroidFileUtil.getAssetsInputStream(context, sourceAssetsFileName);
						FileOutputStream out = new FileOutputStream(destPath);
						FileCopyUtil.copyWithStream(in, out, resultCallBack);
					} catch (IOException e)
					{
						e.printStackTrace();
						LogProxy.e(TAG, "copyFromAssets", e);
						if (null != resultCallBack) resultCallBack.onResult(false);
					}
				} else
				{
					if (null != resultCallBack) resultCallBack.onResult(false);
				}
			}
		}));
		copyThread.start();
	}

	public static void checkFileExitsAndMD5(String md5, String filePath, ResultCallBack copyResultCallBack)
	{
		if (EmptyUtil.isEmpty(filePath) || EmptyUtil.isEmpty(md5))
		{
			if (null != copyResultCallBack) copyResultCallBack.onResult(false);
		} else
		{
			boolean fileExists = FileCheckUtil.isFileExists(filePath);
			if (fileExists)
			{
				String fileMd5 = MD5Util.getFileMd5(filePath);
				if (EmptyUtil.notEmpty(fileMd5) && md5.equalsIgnoreCase(fileMd5))
				{
					if (null != copyResultCallBack) copyResultCallBack.onResult(true);
					return;
				}
			}
			if (null != copyResultCallBack) copyResultCallBack.onResult(false);
		}
	}

	public static void checkFileExitsAndMD5InThread(String md5, String filePath, ResultCallBack copyResultCallBack)
	{
		if (EmptyUtil.isEmpty(filePath) || EmptyUtil.isEmpty(md5))
		{
			if (null != copyResultCallBack) copyResultCallBack.onResult(false);
			return;
		}

		final Thread md5Thread = new Thread(() -> checkFileExitsAndMD5(md5, filePath, copyResultCallBack));
		md5Thread.start();
	}

}


