package com.georgebindragon.base.debug;

import android.content.Intent;
import android.os.Bundle;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.Set;

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


public class PrintUtil extends PrintUtil_Java
{
	private static final String TAG = "PrintUtil-->";

	public static void printIntent(String tag, Intent intent)
	{
		boolean logEnable = LogProxy.isLogEnable();
		System.out.println("printIntent-->log是否可见: " + (logEnable ? "可见, 进行打印" : "不可见, 不调用分析打印"));
		if (!logEnable) return;
		if (EmptyUtil.isEmpty(tag)) tag = TAG;

		LogProxy.d(tag, "printIntent-->"+getIntentString(intent));
	}

	public static String getIntentString(Intent intent)
	{
		StringBuilder buffer = new StringBuilder();

		if (EmptyUtil.notEmpty(intent))
		{
			try
			{
				buffer.append("Intent");
				buffer.append(":{");

				String action = intent.getAction();
				buffer.append("\n");
				buffer.append("action=");
				buffer.append(action);
				buffer.append(",");

				Bundle extras = intent.getExtras();
				if (EmptyUtil.notEmpty(extras))
				{
					buffer.append("\n");
					buffer.append("Bundle:{");

					Set<String> keySet = extras.keySet(); //获取所有的Key
					if (EmptyUtil.notEmpty(keySet))
					{
						for (String key : keySet)
						{
							buffer.append("\n");
							buffer.append("\t");
							buffer.append("<");
							if (EmptyUtil.notEmpty(key))
							{
								buffer.append(key.getClass().getSimpleName());
								buffer.append("=");
								buffer.append(StringUtil.getObjectString(key));
								buffer.append(",");

								Object value = extras.get(key);
								if (EmptyUtil.notEmpty(value))
								{
									buffer.append(value.getClass().getSimpleName());
									buffer.append("=");
									buffer.append(StringUtil.getObjectString(value));
								} else
								{
									buffer.append(value);
								}
							} else
							{
								buffer.append("null,null");
							}
							buffer.append(">");
							buffer.append(",");
							buffer.append("\n");
						}
					} else
					{
						buffer.append(keySet);
					}
					buffer.append("\t");
					buffer.append("}");
				}
				buffer.append("\n");
				buffer.append("}");
			} catch (Exception e) { LogProxy.e(TAG, "getIntentString", e); }
		} else
		{
			return null;
		}
		return buffer.toString();
	}
}