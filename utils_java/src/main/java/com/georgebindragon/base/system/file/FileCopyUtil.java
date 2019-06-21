package com.georgebindragon.base.system.file;

import com.georgebindragon.base.algorithm.MD5Util;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建人：George
 * 描述：复制文件
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class FileCopyUtil
{
	private static final String TAG = "FileCopyUtil-->";

	/**
	 * 将APK的资源文件拷贝到SD卡中
	 *
	 * @param isCover 是否覆盖已存在的目标文件
	 * @param source  源文件路径
	 * @param dest    目的文件路径
	 */
	public static boolean copyFile(boolean isCover, String source, String dest)
	{
		boolean result = false;

		boolean sourceFileExists = FileCheckUtil.isFileExists(source);
		if (sourceFileExists)
		{
			boolean destFileExists = FileCheckUtil.isFileExists(dest);
			if (destFileExists)
			{
				String sourceMd5 = MD5Util.getFileMd5(source);
				String destMd5   = MD5Util.getFileMd5(dest);
				if (EmptyUtil.notEmpty(sourceMd5, destMd5) && sourceMd5.equalsIgnoreCase(destMd5)) return true;
			}

			boolean createDir = FileCreateUtil.createFile(dest);
			if (createDir)
			{
				File file = new File(dest);
				if (isCover || !file.exists())
				{
					FileInputStream  in  = null;
					FileOutputStream out = null;
					try
					{
						in = new FileInputStream(source);
						out = new FileOutputStream(dest);
						byte[] buffer = new byte[1024];
						int    size   = 0;
						while ((size = in.read(buffer, 0, 1024)) >= 0)
						{
							out.write(buffer, 0, size);
						}
						out.flush();
						result = true;
					} catch (IOException e)
					{
						e.printStackTrace();
						result = false;
					} finally
					{
						try
						{
							if (out != null) out.close();
						} catch (IOException e) { e.printStackTrace(); }
						try
						{
							if (in != null) in.close();

						} catch (IOException e) { e.printStackTrace(); }
					}
				}
			}
		}
		return result;
	}
}
