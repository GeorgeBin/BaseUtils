package com.georgebindragon.base.system.file;

import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：FileCheckUtil
 * 类概述：文件检测工具类
 * 创建时间：2017-05-11 15:13
 * 详细描述：文件检测：文件是否存在，文件夹是否存在，文件夹是否为空
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileCheckUtil
{
	public static final String TAG = "FileCheckUtil-->";

	public static boolean isDirectoryEmpty(File file)
	{
		if (null != file && file.isDirectory())
		{
			File[] files = file.listFiles();
			return !(null != files && files.length > 0);
		}
		return true;
	}

	public static boolean isDirectoryExists(String file)
	{
		return EmptyUtil.notEmpty(file) && isDirectoryExists(new File(file));
	}

	public static boolean isDirectoryExists(File file)
	{
		return null != file && file.isDirectory() && file.exists();
	}

	public static boolean isFileExists(String file)
	{
		return EmptyUtil.notEmpty(file) && isFileExists(new File(file));
	}

	public static boolean isFileExists(File file)
	{
		return null != file && file.isFile() && file.exists();
	}
}
