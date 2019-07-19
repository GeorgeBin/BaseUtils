package com.georgebindragon.base.system.file;

import com.georgebindragon.base.abilities.callbacks.ResultCallBack;
import com.georgebindragon.base.algorithm.MD5Util;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 创建人：George
 * 描述：复制文件
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class FileCopyUtil
{
	private static final String TAG = "FileCopyUtil-->";

	private static final int Read_Length = 1024;

	/**
	 * 将APK的资源文件拷贝到SD卡中
	 *
	 * @param needCover 是否覆盖已存在的目标文件
	 * @param source    源文件路径
	 * @param dest      目的文件路径
	 */
	public static void copyFile(boolean needCover, String source, String dest, final ResultCallBack copyResultCallBack)
	{
		boolean sourceFileExists = FileCheckUtil.isFileExists(source);
		if (sourceFileExists)
		{
			boolean destFileExists = FileCheckUtil.isFileExists(dest);
			if (destFileExists)
			{
				String sourceMd5 = MD5Util.getFileMd5(source);
				String destMd5   = MD5Util.getFileMd5(dest);
				if (null != sourceMd5 && EmptyUtil.notEmpty(sourceMd5, destMd5))
				{
					if (sourceMd5.equalsIgnoreCase(destMd5))
					{
						if (null != copyResultCallBack) copyResultCallBack.onResult(true);
						return;
					}
				}
			}

			boolean createDir = FileCreateUtil.createFile(dest);
			if (createDir)
			{
				File file = new File(dest);
				if (needCover || !file.exists())
				{
					try
					{
						FileInputStream  in  = new FileInputStream(source);
						FileOutputStream out = new FileOutputStream(dest);
						copyWithStreamInThread(in, out, copyResultCallBack);
					} catch (Exception e)
					{
						e.printStackTrace();
						LogProxy.d(TAG, "copyFile-->close-->" + e.getMessage());
					}
				}
			}
		}
	}

	public static void copyWithStreamInThread(InputStream in, OutputStream out, final ResultCallBack copyResultCallBack)
	{
		if (null == in || null == out) return;
		final Thread copyThread = new Thread(() -> copyWithStream(in, out, copyResultCallBack));
		copyThread.start();
	}

	public static void copyWithStream(InputStream in, OutputStream out, final ResultCallBack copyResultCallBack)
	{
		if (null == in || null == out) return;

		try
		{
			byte[] buffer = new byte[Read_Length];
			int    size;

			while ((size = in.read(buffer, 0, Read_Length)) >= 0)
			{
				out.write(buffer, 0, size);
			}

			if (null != copyResultCallBack) copyResultCallBack.onResult(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "copyWithStream", e);
			if (null != copyResultCallBack) copyResultCallBack.onResult(false);
		} finally
		{
			try
			{
				out.close();
			} catch (Exception e) { LogProxy.e(TAG, "copyWithStream-->out.close()", e); }
			try
			{
				in.close();
			} catch (Exception e) { LogProxy.e(TAG, "copyWithStream-->in.close()", e);}
		}
	}
}
