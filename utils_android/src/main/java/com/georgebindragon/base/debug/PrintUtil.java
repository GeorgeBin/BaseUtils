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


public class PrintUtil // extends PrintUtil_Java
{
	private static final String TAG = "PrintUtil-->";

	public static void printIntent(String tag, Intent intent)
	{
		boolean logEnable = LogProxy.isLogEnable();
		System.out.println("getBroadcastIntentDetail-->log是否可见: " + (logEnable ? "可见, 进行打印" : "不可见, 不调用分析打印"));
		if (!logEnable) return;
		if (EmptyUtil.isEmpty(tag)) tag = TAG;

		try
		{
			if (EmptyUtil.isEmpty(intent)) return;

			StringBuffer sb = new StringBuffer();

			String action = intent.getAction();
			sb.append("action:");
			sb.append(action);

			Bundle extras = intent.getExtras();
			if (null == extras) return;

			Set<String> keySet = extras.keySet();  //获取所有的Key
			if (null == keySet) return;

			for (String key : keySet)//bundle.get(key);来获取对应的value
			{
				Object value     = extras.get(key);
				String keyInfo   = "key" + (EmptyUtil.isEmpty(key) ? "" : "(" + key.getClass().getSimpleName() + ")") + "=" + StringUtil.getPrintString(key);
				String valueInfo = "value" + (EmptyUtil.isEmpty(value) ? "" : "(" + value.getClass().getSimpleName() + ")") + "=" + StringUtil.getPrintString(value);

				LogProxy.d(tag, "getBroadcastIntentDetail-->extras：Bundle：" + keyInfo + "\t\t" + valueInfo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(tag, "getBroadcastIntentDetail", e);
		}
	}
}