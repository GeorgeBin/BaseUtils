package com.georgebindragon.base.crash.release;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.georgebindragon.base.crash.CrashCallBack;
import com.georgebindragon.base.crash.CrashUtil;
import com.georgebindragon.base.crash.ICrashHandler;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.file.FileCreateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 全局错误捕捉
 */
@SuppressLint("StaticFieldLeak")
public class ReleaseCrashHandler implements Thread.UncaughtExceptionHandler, ICrashHandler
{
	private static final String TAG = "ReleaseCrashHandler-->";

	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private Context                         mContext;

	private static ReleaseCrashHandler INSTANCE = new ReleaseCrashHandler();

	public static ReleaseCrashHandler getInstance() { return INSTANCE; }

	private ReleaseCrashHandler() { }

	@Override
	public void init(Context context)
	{
		Log.d(TAG, "init");
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(INSTANCE);
	}

	private CrashCallBack crashCallBack;

	@Override
	public void setCallBack(CrashCallBack crashCallBack)
	{
		this.crashCallBack = crashCallBack;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		Log.d(TAG, "uncaughtException");
		if (mDefaultHandler != null && (!handleException(ex)))//主动处理
		{
			mDefaultHandler.uncaughtException(thread, ex);
			Log.d(TAG, "uncaughtException-->不主动处理-->交给系统的默认去处理");
		} else//不主动处理
		{
			Log.d(TAG, "uncaughtException-->主动处理");
			if (crashCallBack != null)
			{
				crashCallBack.OnCrash(ex);
			} else
			{
				//结束当前应用
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}
	}

	private boolean handleException(Throwable crash)
	{
		Log.d(TAG, "handleException");
		if (crash == null) { return false; }
		crash.printStackTrace();
		new Thread()
		{
			@Override
			public void run()
			{
				Log.d(TAG, "handleException-->程序出现异常！！");
				writeCrashLog(mContext, crash);
			}
		}.start();
		new Thread()
		{
			@Override
			public void run()
			{
				Log.d(TAG, "handleException-->重启App！！");
				CrashUtil.restartApp(mContext);
			}
		}.start();
		return true;
	}

	private void writeCrashLog(Context context, Throwable crash)
	{
		File externalFilesDir = context.getExternalFilesDir("crashLog");
		if (externalFilesDir != null)
		{
			String logFileFullPath = createTextFile(externalFilesDir.getAbsolutePath());
			String crashInfo       = getCrashInfo(crash);
			toFile(logFileFullPath, crashInfo);
		}
	}

	private String createTextFile(String parentPath)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd_hh-mm-ss-SSS", Locale.CHINESE);
		String           date             = simpleDateFormat.format(new Date());

		//文件
		String logFileFullPath = parentPath + File.separator + date + ".txt";
		LogProxy.i(TAG, "创建 crash log 文件，路径：" + logFileFullPath);

		boolean success = FileCreateUtil.createFile(logFileFullPath);
		LogProxy.i(TAG, "crash log 路径=" + logFileFullPath, "log文件创建：" + (success ? "成功" : "不成功"));

		return logFileFullPath;
	}

	private void toFile(String fullPath, String info)
	{
		try
		{
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fullPath, true)));//true为续写
			bufferedWriter.write(info);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			LogProxy.e("toFile", "文件写入txt失败=" + e.getMessage());
		}

	}

	private String getCrashInfo(Throwable crash)
	{
		StringBuilder sb = new StringBuilder();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd_hh-mm-ss-SSS", Locale.CHINESE);
		String           date             = simpleDateFormat.format(new Date());
		sb.append(date);
		sb.append("\n");

		try
		{
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null)
			{
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				sb.append("versionName:");
				sb.append(versionName);
				sb.append("\n");

				String versionCode = pi.versionCode + "";
				sb.append("versionCode:");
				sb.append(versionCode);
				sb.append("\n");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Writer      writer = new StringWriter();
		PrintWriter pw     = new PrintWriter(writer);
		crash.printStackTrace(pw);
		Throwable cause = crash.getCause();

		//循环着把所有的异常信息写入writer中
		while (cause != null)
		{
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();//记得关闭

		String result = writer.toString();
		sb.append(result);

		return sb.toString();
	}
}
