package com.georgebindragon.base.system.file;

import android.os.Environment;

import java.io.File;


public class SDCardUtil
{
	public static final String SDCard_Root        = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String SDCardAppData_Root = SDCard_Root + File.separator + "Android" + File.separator + "data";

	public static boolean isSDCardMounted()
	{
		return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
	}

	public static String getSDCardAppDataPath(String appPackageName)
	{
		return SDCardAppData_Root + File.separator + appPackageName;
	}

	public static String getSDCardAppDataFilesPath(String appPackageName)
	{
		return getSDCardAppDataPath(appPackageName + File.separator + "files");
	}

	public static String getSDCardAppDataFilesDatabasesPath(String appPackageName)
	{
		return getSDCardAppDataPath(appPackageName + File.separator + "files" + File.separator + "databases");
	}
}
