package com.georgebindragon.base.system.file.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * 创建人：George
 * 类名称：FileNameComparator
 * 类概述：比较器-文件名
 * 详细描述：比较文件名，以便于排序
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileNameComparator implements Comparator<File>
{
	public static final String TAG = "FileNameComparator-->";

	@Override
	public int compare(File file1, File file2)
	{
		return file1.getName().compareTo(file2.getName());//由小到大排列
	}
}
