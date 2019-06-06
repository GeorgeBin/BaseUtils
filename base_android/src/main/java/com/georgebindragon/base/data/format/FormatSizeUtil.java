package com.georgebindragon.base.data.format;

import android.content.Context;
import android.text.format.Formatter;

/**
 * 创建人：George
 * 类名称：FormatSizeUtil
 * 类概述：格式化工具--格式化大小数据
 * 详细描述：计算文件数据大小
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FormatSizeUtil
{
	private static final String TAG = "FormatSizeUtil-->";

	//447KB 350B
	public static final String B   = "B ";
	public static final String KB  = "KB ";
	public static final String MB  = "MB ";
	public static final String GB  = "GB ";
	public static final String TB  = "TB ";
	//
	public static final int    K   = 1024;
	public static final int    K2  = K * K;
	public static final int    K3  = K2 * K;
	public static final double K4  = 1024D * 1204 * 1204 * 1024;
	public static final double K5  = 1024D * 1204 * 1204 * 1024 * 1024;

	public static String formatSizeInToStringByEveryUnit(double size)
	{
		if (size <= 0) return "0" + B;
		StringBuilder result = new StringBuilder();
		int           bb     = (int) (size % K);
		int           kb     = (int) ((size % K2) / K);
		int           mb     = (int) ((size % K3) / K2);
		int           gb     = (int) ((size % K4) / K3);
		int           tb     = (int) ((size % K5) / K4);
		if (tb > 0) result.append(tb).append(TB);
		if (gb > 0) result.append(gb).append(GB);
		if (mb > 0) result.append(mb).append(MB);
		if (kb > 0) result.append(kb).append(KB);
		if (bb > 0) result.append(bb).append(B);
		return result.toString().trim();
	}

	/**
	 * 格式化数据大小为字符串
	 *
	 * @param size 大小(单位 B)
	 * @return 最适合的单位下的数值 eg:447.34KB
	 */
	public static String formatSizeIntoStringByOneUnit(Context context, long size)
	{
		return Formatter.formatFileSize(context, size);
	}
}
