package com.georgebindragon.base.debug;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.List;

/**
 * 创建人：George
 * 类名称：PrintUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PrintUtil
{
	private static final String TAG = "PrintUtil-->";

	public static void print(String tag, byte[] bytes)
	{
		if (null != bytes && bytes.length > 0)
		{
			LogProxy.d(TAG, "print-->bytes.length=" + bytes.length);

			StringBuilder buffer = new StringBuilder();

			buffer.append("print: ");
			buffer.append("{");
			for (byte b : bytes)
			{
				buffer.append(b);
				buffer.append(",");
			}
			buffer.append("}");

			LogProxy.i(EmptyUtil.notEmpty(tag) ? tag : TAG, buffer.toString());
		} else
		{
			LogProxy.i(EmptyUtil.notEmpty(tag) ? tag : TAG, "print-->Empty");
		}
	}

	public static void print(String tag, List list)
	{
		if (null != list && list.size() > 0)
		{
			LogProxy.d(TAG, "print-->list.size()=" + list.size());

			StringBuilder buffer = new StringBuilder();

			buffer.append("print: ");
			buffer.append("{");
			for (Object b : list)
			{
				buffer.append(b);
				buffer.append(",");
			}
			buffer.append("}");

			LogProxy.i(EmptyUtil.notEmpty(tag) ? tag : TAG, buffer.toString());
		} else
		{
			LogProxy.i(EmptyUtil.notEmpty(tag) ? tag : TAG, "print-->Empty");
		}
	}

	public static void printMethodChain(String tag)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("printMethodChain: ");

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		int                 max        = stackTrace.length - 1;
		int                 min        = 3;

		for (int index = max; index >= min; index--)
		{
			StackTraceElement element = stackTrace[index];

			sb.append("\n");
			sb.append(index == max ? "  " : "↑ ");

			// (类名:行号)  就会变为可点击跳转的内容。(SipAccount.java:112)
			// ↑ cn.bluetelecom.sdk.sip.inner.imp.SipAccount # onIncomingCall (SipAccount.java:112)

			sb.append(element.getClassName());
			sb.append(" # ");
			sb.append(element.getMethodName());
			sb.append(" (");
			sb.append(element.getFileName());
			sb.append(":");
			sb.append(element.getLineNumber());
			sb.append(") ");
		}

		LogProxy.i(EmptyUtil.notEmpty(tag) ? tag : TAG, sb.toString());
	}

}