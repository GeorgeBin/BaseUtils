package com.georgebindragon.base.data.format;


import com.georgebindragon.base.data.basic.StringUtil;

/**
 * 项目名称：YChat
 * 创建人：GeorgeBin
 * 类名称：FormatStringUtil
 * 类概述：格式化工具--格式化String
 * 创建时间：2017-06-27 15:17
 * 详细描述：从尾部去除某些固定字符啊、拼接字符串啊
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FormatStringUtil
{
	public static final String TAG = "FormatStringUtil-->";

	public static String cutLastStringFromTarget(String target, String needCut)
	{
		if (!StringUtil.checkNotEmpty(target)) return "";       //源为空，返回“”
		if (!StringUtil.checkNotEmpty(needCut)) return target;  //格式为空，返回源
		int needCutLength = needCut.length();
		int targetLength  = target.length();
		if (needCutLength > targetLength) return target;//格式长度大于源，返回源
		//根据长度，从结尾，截取格式长度的字符
		String lastWord = target.substring(targetLength - needCutLength);
		if (needCut.equals(lastWord))
		{
			return target.substring(0, targetLength - needCutLength);
		} else
		{
			return target;
		}
	}

	/**
	 * @param MaxLength 拼接后的最大长度
	 * @param strings   要拼接的字符串
	 * @return 拼接后的字符串，样式：
	 * 没有超过最大长度：字符串1、字符串2
	 * 已经超过最大长度：字符串1、字符串2、字符……
	 */
	public static String addStringsTogetherWithLimit(int MaxLength, String... strings)
	{
		StringBuilder result = new StringBuilder();
		if (null != strings && strings.length > 0)
		{
			for (String s : strings)
			{
				if (result.length() <= MaxLength)
				{
					result.append(s);
					result.append("、");
				} else { break;}
			}
		}
		if (result.length() > MaxLength)
		{
			result.substring(0, MaxLength);
			result.append("...");
		}
		return result.toString();
	}
}
