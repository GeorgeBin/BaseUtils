package com.georgebindragon.base.system.linux;

import com.georgebindragon.base.abilities.callbacks.ResultCallBack;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 创建人：George
 * 类名称：LinuxCommandUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LinuxCommandUtil
{
	private static final String TAG = "LinuxCommandUtil-->";

	public static void ping(String address, ResultCallBack resultCallBack)
	{
		final Thread pingThread = new Thread(() ->
		{
			try
			{
				String[] ping    = {"ping -c 3 -w 100 ", address};
				Process  process = runLinuxCommandsWithRuntime(ping);// ping网址3次

				if (null != process)
				{
					// 读取ping的内容，可以不加
					InputStream    input        = process.getInputStream();
					BufferedReader in           = new BufferedReader(new InputStreamReader(input));
					StringBuilder  stringBuffer = new StringBuilder();
					String         content;
					while ((content = in.readLine()) != null)
					{
						stringBuffer.append(content);
					}

					LogProxy.d(TAG, "ping-->ping log: \n\n" + stringBuffer.toString() + "\n\n");

					// ping的状态
					int status = process.waitFor();
					LogProxy.d(TAG, "ping-->status=" + (status == 0 ? "成功" : "不成功"));

					if (status == 0)
					{
						if (null != resultCallBack) resultCallBack.onResult(true);
						return;
					}
					if (null != resultCallBack) resultCallBack.onResult(false);
				}
			} catch (Exception e)
			{
				if (null != resultCallBack) resultCallBack.onResult(false);
				LogProxy.e(TAG, "ping", e);
			}
		});
		pingThread.start();
	}

	public static void chmod777Commond(String filePath)
	{
		if (EmptyUtil.isEmpty(filePath)) return;

		String[] command = {"chmod", "777", filePath};
		runLinuxCommandsWithBuilder(command);
	}

	public static Process runLinuxCommandsWithBuilder(String[] commands)
	{
		return runLinuxCommandsWithBuilder(commands, true);
	}

	public static Process runLinuxCommandsWithBuilder(String[] commands, boolean redirectErrorStream)
	{
		try
		{
			if (null != commands && commands.length > 0)
			{
				ProcessBuilder builder = new ProcessBuilder(commands).redirectErrorStream(redirectErrorStream);
				return builder.start();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, " LinuxCommands with ProcessBuilder.start()", e);
			return null;
		}
		return null;
	}

	public static Process runLinuxCommandsWithRuntime(String[] commands)
	{
		try
		{
			if (null != commands && commands.length > 0)
			{
				return Runtime.getRuntime().exec(commands);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, " LinuxCommands with Runtime.getRuntime().exec", e);
			return null;
		}
		return null;
	}
}
