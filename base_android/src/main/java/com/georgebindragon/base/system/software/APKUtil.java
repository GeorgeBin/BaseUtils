package com.georgebindragon.base.system.software;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 创建人：George
 * 类名称：APKUtil
 * 类概述：
 * 详细描述：
 *
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class APKUtil
{
	private static final String TAG = "APKUtil-->";
//
//	public static void installAPKByAndroid()
//	{
//
//	}

	public static boolean installAPKByAndroid(Context context, File apkFile)
	{
		try
		{
			if (apkFile != null && apkFile.exists() && apkFile.getName().endsWith(".apk"))
			{
				String var4 = "chmod 777 " + apkFile.getAbsolutePath();
				Runtime.getRuntime().exec(var4);

				Intent var5 = new Intent("android.intent.action.VIEW");
				String var6 = "application/vnd.android.package-archive";

				if (Build.VERSION.SDK_INT >= 24)
				{
					String var7 = "android.support.v4.content.FileProvider";
					var5.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					Class var8 = Class.forName(var7);
					if (var8 == null)
					{
						LogProxy.e(TAG, "can't find class android.support.v4.content.FileProvider");
						return false;
					}

					Uri var9 = (Uri) invoke(var7, "getUriForFile", null, new Class[]{Context.class, String.class, File.class},
							new Object[]{context, context.getPackageName() + ".fileProvider", apkFile});
					if (var9 == null)
					{
						LogProxy.e(TAG, "file location is " + apkFile.toString());
						LogProxy.e(TAG, "install failed, contentUri is null!");
						return false;
					}
					LogProxy.e(TAG, "contentUri is " + var9);

					var5.setDataAndType(var9, var6);
				} else
				{
					var5.setDataAndType(Uri.fromFile(apkFile), var6);
				}

				var5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(var5);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "installAPKByAndroid", e);
		}

		return false;
	}


	public static Object invoke(String var0, String var1, Object var2, Class<?>[] var3, Object[] var4)
	{
		try
		{
			Class  var5 = Class.forName(var0);
			Method var6 = var5.getDeclaredMethod(var1, var3);
			var6.setAccessible(true);
			return var6.invoke(var2, var4);
		} catch (Exception var7)
		{
			return null;
		}
	}
}
