package com.georgebindragon.base.system.file;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;


public class FileCreateUtil
{
	public static final String TAG = "FileCreateUtil-->";

	public static boolean createFile(String file)
	{
		return EmptyUtil.notEmpty(file) && createFile(new File(file));
	}

	public static boolean createFile(File file)
	{
		if (null != file)
		{
			if (FileCheckUtil.isFileExists(file))
			{
				return true;
			} else
			{
				try
				{
					createParentDirectory(file);
					return file.createNewFile();
				} catch (Exception e)
				{
					e.printStackTrace();
					LogProxy.e(TAG, "createFile", e);
				}
			}
		}
		return false;
	}


	public static boolean createParentDirectory(String file)
	{
		return EmptyUtil.notEmpty(file) && createParentDirectory(new File(file));
	}

	public static boolean createParentDirectory(File file)
	{
		if (null != file)
		{
			try
			{
				File fileParent = file.getParentFile();
				if (null != fileParent)
				{
					return fileParent.exists() || fileParent.mkdirs();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "createParentDirectory", e);
			}
		}
		return false;
	}
}
