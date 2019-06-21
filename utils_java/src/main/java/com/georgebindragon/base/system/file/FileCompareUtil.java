package com.georgebindragon.base.system.file;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.file.comparator.FileNameComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建人：George
 * 类名称：FileCompareUtil
 * 类概述：文件比较类
 * 详细描述：文件比较：通过比较器，通过文件名
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileCompareUtil
{
	private static final String TAG = "FileCompareUtil-->";

	public static List<File> sortFileByName(String path)
	{
		File file = new File(path);
		return sortFileByName(file);
	}

	public static List<File> sortFileByName(File file)
	{
		return sortFileByComparator(file, new FileNameComparator());
	}

	private static List<File> sortFileByComparator(File file, Comparator<File> comparator)
	{
		List<File> fileList = new ArrayList<>();
		if (null != file && null != comparator && file.exists())
		{
			if (file.isDirectory())
			{
				File[] fileListFromSystem = file.listFiles();
				if (null != fileListFromSystem && fileListFromSystem.length > 0)
				{
					for (File file2 : fileListFromSystem)
					{
						if (null != file2) fileList.add(file2);
					}
				}
				Collections.sort(fileList, comparator);
			} else if (file.isFile())
			{
				LogProxy.e(TAG, "this file is a file not a directory!");
			}
		} else
		{
			LogProxy.e(TAG, "this file is not exist or readable!");
		}
		return fileList;
	}
}
