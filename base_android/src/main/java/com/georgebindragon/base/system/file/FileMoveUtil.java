package com.georgebindragon.base.system.file;

import android.content.Context;
import android.text.TextUtils;

import com.georgebindragon.base.algorithm.MD5Util;
import com.georgebindragon.base.function.log.LogProxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 项目名称：BaiduVoiceLibDemo
 * 创建人：GeorgeBin
 * 类名称：FileMoveUtil
 * 类概述：
 * 创建时间：2018-04-17 17:08
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FileMoveUtil
{

	public static final String TAG = "FileMoveUtil-->";

	/**
	 * 将APK的资源文件拷贝到SD卡中
	 *
	 * @param isCover   是否覆盖已存在的目标文件
	 * @param sourceMD5 源文件的MD5 不需要校验，则可以给空
	 * @param source    源文件路径
	 * @param dest      目的文件路径
	 */
	public static void copyFromAssetsToSdcard(final Context context, final boolean isCover, final String sourceMD5, final String source, final
	String dest, final CopyResultCallBack copyResultCallBack)
	{
		final Thread copyThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				boolean createDir = FileCreateUtil.createParentDirectory(dest);
				if (createDir)
				{
					File    file       = new File(dest);
					boolean isMd5Right = true;//默认：需要复制
					if (!TextUtils.isEmpty(sourceMD5) && file.exists() && !isCover)//有源MD5 + 文件已经存在 + 不需要覆盖。
					{
						//则检测MD5，确认文件相同且完好
						String fileMd5 = MD5Util.getFileMd5(dest);
						LogProxy.d(TAG, "test-->MD5-->sourceMD5=" + sourceMD5);
						LogProxy.d(TAG, "test-->MD5-->     dest=" + fileMd5);
						if (!TextUtils.isEmpty(fileMd5) && sourceMD5.equals(fileMd5))
						{
							isMd5Right = true;//校验正确，不复制
							if (null != copyResultCallBack) copyResultCallBack.onResult(true);
						} else
						{
							isMd5Right = false;
						}
					}
					//文件不存在 或 需要覆盖 或 MD5检测不通过。则需要复制
					if (!file.exists() || isCover || !isMd5Right)
					{
						InputStream      is  = null;
						FileOutputStream fos = null;
						try
						{
							is = context.getResources().getAssets().open(source);
							fos = new FileOutputStream(dest);
							byte[] buffer = new byte[1024];
							int    size   = 0;
							while ((size = is.read(buffer, 0, 1024)) >= 0)
							{
								fos.write(buffer, 0, size);
							}
							if (null != copyResultCallBack) copyResultCallBack.onResult(true);
						} catch (IOException e)
						{
							e.printStackTrace();
							if (null != copyResultCallBack) copyResultCallBack.onResult(false);
						} finally
						{
							try
							{
								if (fos != null) fos.close();
							} catch (IOException e) { e.printStackTrace(); }
							try
							{
								if (is != null) is.close();
							} catch (IOException e) { e.printStackTrace(); }
						}
					}
				} else
				{
					if (null != copyResultCallBack) copyResultCallBack.onResult(false);
				}
			}
		});
		copyThread.start();
	}

	public interface CopyResultCallBack
	{
		void onResult(boolean isSuccess);
	}
}


