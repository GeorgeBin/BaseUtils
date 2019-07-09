package com.georgebindragon.base.system.file;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.system.software.PermissionUtil;

import java.io.File;

/**
 * 创建人：George
 * 类名称：ExternalFileUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ExternalFileUtil
{
	private static final String TAG = "ExternalFileUtil-->";

	public static final String SDCard_Root        = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String SDCardAppData_Root = SDCard_Root + File.separator + "Android" + File.separator + "data";

	public static boolean isExternalStorageMounted()
	{
		return (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable());
	}

	@SuppressLint("InlinedApi")
	public static boolean isExternalReadPermissionOK()
	{
		return PermissionUtil.checkPermission(BaseUtils.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
	}

	public static boolean isExternalWritePermissionOK()
	{
		return PermissionUtil.checkPermission(BaseUtils.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}

	/**
	 * @param context 应用的上下文
	 * @return /SD卡/Android/data/程序的包名/cache
	 */
	public static String getMyExternalAppDataCachePath(Context context)
	{
		if (null != context)
		{
			File externalCacheDir = context.getExternalCacheDir();
			if (null != externalCacheDir)
			{
				return externalCacheDir.getPath();
			}
		}
		return null;
	}

	/**
	 * @param context 应用的上下文
	 * @return /SD卡/Android/data/程序的包名/files
	 */
	public static String getMyExternalAppDataFilesPath(Context context)
	{
		if (null != context)
		{
			File externalFilesDir = context.getExternalFilesDir(null);
			if (null != externalFilesDir)
			{
				return externalFilesDir.getPath();
			}
		}
		return null;
	}

	public static String getExternalXXXAppDataPath(String appPackageName)
	{
		return SDCardAppData_Root + File.separator + appPackageName;
	}

	public static String getExternalXXXAppDataFilesPath(String appPackageName)
	{
		return getExternalXXXAppDataPath(appPackageName + File.separator + "files");
	}

	public static String getExternalXXXAppDataFilesDatabasesPath(String appPackageName)
	{
		return getExternalXXXAppDataPath(appPackageName + File.separator + "files" + File.separator + "databases");
	}

	//外部存储的公共文件夹

	public static File getExternalPublicPath(String type)
	{
		return Environment.getExternalStoragePublicDirectory(type);
	}

	public static String getExternalPublicDownloadPath()
	{
		return getExternalPublicPath(Environment.DIRECTORY_DOWNLOADS).getPath();
	}

	public static String getExternalPublicDCIMPath()
	{
		return getExternalPublicPath(Environment.DIRECTORY_DCIM).getPath();
	}
}
