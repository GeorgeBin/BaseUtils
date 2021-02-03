package com.georgebindragon.base.utils;

import com.georgebindragon.base.function.log.LogProxy;

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


public class PrintUtil_Java
{
	private static final String TAG = "PrintUtil_Java-->";

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

			// (类名:行号)  就会变为可点击跳转的内容。例如：(SipAccount.java:112)
			// 打印示例：
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