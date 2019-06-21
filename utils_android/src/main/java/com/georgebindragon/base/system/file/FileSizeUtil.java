package com.georgebindragon.base.system.file;

import android.os.StatFs;
import android.text.TextUtils;

import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;

/**
 * 创建人：George
 * 类概述：文件夹大小工具
 * 详细描述：文件夹所在的文件系统：大小、空余；文件或文件夹的大小
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileSizeUtil
{
	private static final String TAG = "FileSizeUtil-->";

	public static long getFileSystemSizeLong(File targetFile)
	{

		if (null == targetFile) return -1;
		return getFileSystemSizeLong(targetFile.getPath());
	}

	public static long getFileSystemSizeLong(String targetFilePath)
	{

		if (TextUtils.isEmpty(targetFilePath)) return -1;
		StatFs stat        = new StatFs(targetFilePath);
		long   blockSize   = stat.getBlockSize();
		long   totalBlocks = stat.getBlockCount();
		return blockSize * totalBlocks;
	}

	public static long getFileSystemAvailableSizeLong(File targetFile)
	{

		if (null == targetFile) return -1;
		return getFileSystemAvailableSizeLong(targetFile.getPath());
	}

	public static long getFileSystemAvailableSizeLong(String targetFilePath)
	{

		if (TextUtils.isEmpty(targetFilePath)) return -1;
		StatFs stat            = new StatFs(targetFilePath);
		long   blockSize       = stat.getBlockSize();
		long   availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	/**
	 * 获取文件夹大小
	 *
	 * @param filePath File路径
	 * @return long 文件大小：单位为B
	 */
	public static long getFileOrDirectorySize(String filePath)
	{
		if (!TextUtils.isEmpty(filePath))
		{
			return getFileOrDirectorySize(new File(filePath));
		} else
		{
			return -1;
		}
	}

	public static long getFileOrDirectorySize(File file)
	{
		long size = 0;
		if (null == file)
		{
			return -1;
		} else if (file.isFile())
		{
			return file.length();
		} else if (file.isDirectory())
		{
			try
			{
				File[] fileList = file.listFiles();
				for (File aFileList : fileList)
				{
					if (aFileList.isDirectory())
					{
						size = size + getFileOrDirectorySize(aFileList);
					} else
					{
						size = size + aFileList.length();
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "getFileOrDirectorySize", e);
			}
		}
		return size;
	}
}
