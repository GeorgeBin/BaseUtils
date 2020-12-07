package com.georgebindragon.base.system.software.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.List;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("InlinedApi")
public class AccessibilityUtil
{
	private static final String TAG = "AccessibilityUtil-->";

	// 判断特定的服务是否打开
	public static boolean isApplicationAccessibilityOn(Context context, String packageName, String className)
	{
		LogProxy.v(TAG, "isPackageAccessibilitySettingsOn-->packageName=" + StringUtil.getPrintString(packageName)
				, "service=" + StringUtil.getPrintString(className));

		if (null == context) context = BaseUtils.getContext();
		// 判断方法1
		String string = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
		if (EmptyUtil.notEmpty(string) && string.contains(packageName) && string.contains(className)) return true;

		// 判断方法2
		AccessibilityManager           am              = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
		List<AccessibilityServiceInfo> serviceInfoList = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
		for (AccessibilityServiceInfo info : serviceInfoList)
		{
			if (null != info)
			{
				String id = info.getId();
				if (EmptyUtil.notEmpty(id) && id.contains(packageName) && id.contains(className)) return true;
			}
		}
		return false;
	}

	// 模拟系统行为：

	public static boolean performGlobalAction_Home(AccessibilityService accessibilityService)
	{
		return performGlobalAction(accessibilityService, AccessibilityService.GLOBAL_ACTION_HOME);
	}

	public static boolean performGlobalAction_Recents(AccessibilityService accessibilityService)
	{
		return performGlobalAction(accessibilityService, AccessibilityService.GLOBAL_ACTION_RECENTS);
	}

	public static boolean performGlobalAction_Back(AccessibilityService accessibilityService)
	{
		return performGlobalAction(accessibilityService, AccessibilityService.GLOBAL_ACTION_BACK);
	}

	public static boolean performGlobalAction(AccessibilityService accessibilityService, int action)
	{
		if (null != accessibilityService && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			return accessibilityService.performGlobalAction(action);
		} else
		{
			LogProxy.i(TAG, "performGlobalAction-->false");
		}
		return false;
	}
}
