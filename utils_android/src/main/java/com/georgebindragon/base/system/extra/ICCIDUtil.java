package com.georgebindragon.base.system.extra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 创建人：George
 * 类名称：ICCIDUtil
 * 类概述：
 * 详细描述：
 *
 * Requires Permission: <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

@SuppressLint("MissingPermission")
public class ICCIDUtil
{
	private static final String TAG = "ICCIDUtil-->";

	/**
	 * @return 返回卡槽: SIM卡1 的ICCID
	 */
	public static String getICCID1()
	{
		String ICCID1 = null;

		List<String> ICCIDList = getICCIDsByAPIOver22();
		if (null != ICCIDList && ICCIDList.size() >= 1)
		{
			ICCID1 = ICCIDList.get(0);
		}

		if (EmptyUtil.isEmpty(ICCID1))
		{
			ICCID1 = getCallerICCID();
		}

		return ICCID1;
	}

	/**
	 * @return 返回ICCID列表: 默认: SIM卡1 的ICCID在前
	 */
	public static List<String> getICCIDs()
	{
		List<String> ICCIDList = new ArrayList<>();

		List<String> List1 = getICCIDsByAPIOver22();
		if (null != List1 && List1.size() > 0)
		{
			ICCIDList.addAll(List1);
		}

		String callerICCID = getCallerICCID();
		if (EmptyUtil.notEmpty(callerICCID) && !ICCIDList.contains(callerICCID))
		{
			ICCIDList.add(callerICCID);
		}

		List<String> iccidListByInvoke = getICCIDListByInvoke();
		if (null != iccidListByInvoke && iccidListByInvoke.size() > 0)
		{
			if (iccidListByInvoke.size() == 1)
			{
				String iccid = iccidListByInvoke.get(0);
				if (EmptyUtil.notEmpty(iccid) && !ICCIDList.contains(iccid))
				{
					ICCIDList.add(iccid);
				}
			} else
			{
				for (String iccid : iccidListByInvoke)
				{
					if (EmptyUtil.notEmpty(iccid) && !ICCIDList.contains(iccid))
					{
						ICCIDList.add(iccid);
					}
				}
			}
		}
		return ICCIDList;
	}

	/**
	 * 没有设置默认拨号卡,则返回SIM卡1. 如果设置了默认拨号卡, 则返回默认拨号卡
	 *
	 * @return 系统拨号卡的ICCID
	 */
	@SuppressLint("HardwareIds")
	public static String getCallerICCID()
	{
		String callerICCID = null;
		try
		{
			TelephonyManager telephonyManager = (TelephonyManager) BaseUtils.getContext().getSystemService(TELEPHONY_SERVICE);
			if (null != telephonyManager)
			{
				callerICCID = telephonyManager.getSimSerialNumber();
				LogProxy.i(TAG, "getCallerICCID=" + StringUtil.getPrintString(callerICCID));
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getCallerICCID", e);
			e.printStackTrace();
		}
		if (EmptyUtil.isEmpty(callerICCID))
		{
			callerICCID = getCallerICCIDByInvoke();
		}
		return callerICCID;
	}

	/**
	 * @return 返回ICCID列表: 默认: SIM卡1 的ICCID在前
	 */
	public static List<String> getICCIDsByAPIOver22()
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
		{
			List<SubscriptionInfo> subList = getSubscriptionInfoListByAPIOver22();
			if (null != subList)
			{
				List<String> ICCIDList = new ArrayList<>();
				for (SubscriptionInfo subscriptionInfo : subList)
				{
					if (null != subscriptionInfo)
					{
						LogProxy.i(TAG, "getICCIDsByAPIOver22()-->subscriptionInfo=" + StringUtil.getPrintString(subscriptionInfo));

						String ICCID = subscriptionInfo.getIccId();
						if (EmptyUtil.notEmpty(ICCID) && !ICCIDList.contains(ICCID))
						{
							ICCIDList.add(ICCID);
							LogProxy.i(TAG, "getICCIDsByAPIOver22()-->ICCID=" + StringUtil.getPrintString(ICCID));
						}

						String number = subscriptionInfo.getNumber();
						LogProxy.i(TAG, "getICCIDsByAPIOver22()-->number=" + StringUtil.getPrintString(number));
					}
				}
				return ICCIDList;
			}
		}
		return null;
	}

	private static List<SubscriptionInfo> getSubscriptionInfoListByAPIOver22()
	{
		try
		{
			SubscriptionManager sm;
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
			{
				sm = SubscriptionManager.from(BaseUtils.getContext());
				if (null != sm) return sm.getActiveSubscriptionInfoList();
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getSubscriptionInfoListByAPIOver22", e);
			e.printStackTrace();
		}
		return null;
	}

	private static List<String> getICCIDListByInvoke()
	{
		try
		{
			List<String>            ICCIDList             = new ArrayList<>();
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;

			String ICCID_0 = getCallerICCIDByInvoke();
			LogProxy.d(TAG, "getICCIDListByInvoke default=" + StringUtil.getPrintString(ICCID_0));
			if (EmptyUtil.notEmpty(ICCID_0) && !ICCIDList.contains(ICCID_0)) ICCIDList.add(ICCID_0);

			Method method = telephonyManagerClass.getMethod("getSimSerialNumber", int.class);

			String ICCID_1 = (String) method.invoke(telephonyManager, -1);
			LogProxy.d(TAG, "getICCIDListByInvoke -1=" + StringUtil.getPrintString(ICCID_1));
			if (EmptyUtil.notEmpty(ICCID_1) && !ICCIDList.contains(ICCID_1)) ICCIDList.add(ICCID_1);

			String ICCID0 = (String) method.invoke(telephonyManager, 0);
			LogProxy.d(TAG, "getICCIDListByInvoke 0=" + StringUtil.getPrintString(ICCID0));
			if (EmptyUtil.notEmpty(ICCID0) && !ICCIDList.contains(ICCID0)) ICCIDList.add(ICCID0);

			String ICCID1 = (String) method.invoke(telephonyManager, 1);
			LogProxy.d(TAG, "getICCIDListByInvoke 1=" + StringUtil.getPrintString(ICCID1));
			if (EmptyUtil.notEmpty(ICCID1) && !ICCIDList.contains(ICCID1)) ICCIDList.add(ICCID1);

			String ICCID2 = (String) method.invoke(telephonyManager, 2);
			LogProxy.d(TAG, "getICCIDListByInvoke 2=" + StringUtil.getPrintString(ICCID2));
			if (EmptyUtil.notEmpty(ICCID2) && !ICCIDList.contains(ICCID2)) ICCIDList.add(ICCID2);

			return ICCIDList;
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getICCIDListByInvoke", e);
			e.printStackTrace();
		}
		return null;
	}


	private static String getCallerICCIDByInvoke()
	{
		try
		{
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;

			Method method0 = telephonyManagerClass.getMethod("getSimSerialNumber");

			String ICCID_Default = (String) method0.invoke(telephonyManager);

			LogProxy.i(TAG, "getCallerICCIDByInvoke=" + StringUtil.getPrintString(ICCID_Default));

			return ICCID_Default;
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getCallerICCIDByInvoke", e);
			e.printStackTrace();
		}
		return null;
	}

}
