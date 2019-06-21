package com.georgebindragon.base.system.file;

import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;


public class FileDeleteUtil
{
	private static final String TAG = "FileDeleteUtil-->";

	/**
	 * delete directory and all files inside
	 *
	 * @param filePath the absolute path of directory that you want to delete
	 */
	public static boolean deleteDirectory(String filePath)
	{
		return deleteDirectory(new File(filePath));
	}

	/**
	 * delete directory and all files inside
	 *
	 * @param directory the directory File you want to delete
	 */
	public static boolean deleteDirectory(File directory)
	{
		if (directory != null && directory.exists() && directory.isDirectory())
		{
			for (File item : directory.listFiles())
			{
				if (item.isDirectory())
				{
					if (FileCheckUtil.isDirectoryEmpty(item))
					{
						boolean delete = item.delete();
					} else
					{
						deleteDirectory(item);
					}
				} else if (item.isFile())
				{
					boolean delete = item.delete();
				}
			}
			if (FileCheckUtil.isDirectoryEmpty(directory))
			{
				boolean delete = directory.delete();
				LogProxy.e(TAG, "总目录=" + directory.getAbsolutePath(), "删除结果=" + delete);
			}
		} else if (directory != null && directory.exists() && directory.isFile())
		{
			deleteOneFile(directory);
		}
		return (null != directory && !directory.exists());
	}

	/**
	 * delete directory and all files inside
	 *
	 * @param file the File you want to delete
	 */
	public static boolean deleteOneFile(File file)
	{
		if (file != null && file.exists() && file.isFile())
		{
			return file.delete();
		}
		return false;
	}
}
