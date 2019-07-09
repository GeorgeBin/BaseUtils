package com.georgebindragon.base.system.hardware;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 创建人：George
 * 类名称：HardwareInfoUtil
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
public class HardwareInfoUtil
{
	private static final String TAG = "HardwareInfoUtil-->";

	public static String getIMEI7()
	{
		List<String> imeIList = getIMEIs();
		if (null != imeIList && imeIList.size() > 0)
		{
			for (String imei : imeIList)
			{
				if (EmptyUtil.notEmpty(imei) && imei.length() >= 14)
				{
					return imei.substring(7, 14);
				}
			}
		}
		Random rand = new Random();
		// randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数
		int randNumber = rand.nextInt(9999999 - 1111111 + 1) + 1111111;
		return String.valueOf(randNumber);
	}

	/**
	 * @return 返回SIM卡1的 IMEI或MEID
	 */
	public static String getIMEI1()
	{
		String defaultDeviceId = getDefaultDeviceId();

		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getDeviceIdByInvoke(-1);
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getDeviceIdByInvoke(0);
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getDeviceIdByInvoke(1);
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getDeviceIdByInvoke(2);
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getImeIByInvoke();
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getMeiDByInvoke();
		}
		return defaultDeviceId;
	}

	/**
	 * @return 返回IMEI和MEID 列表: 默认: SIM卡1 的在前
	 */
	public static List<String> getIMEIs()
	{
		List<String> IMEIList = new ArrayList<>();

		String defaultDeviceId = getDefaultDeviceId();
		if (EmptyUtil.notEmpty(defaultDeviceId) && !IMEIList.contains(defaultDeviceId))
		{
			IMEIList.add(defaultDeviceId);
		}

		List<String> List1 = getDeviceIdList();
		if (null != List1 && List1.size() > 0)
		{
			if (List1.size() == 1)
			{
				String deviceId = List1.get(0);
				if (EmptyUtil.notEmpty(deviceId) && !IMEIList.contains(deviceId)) IMEIList.add(deviceId);
			} else
			{
				for (String iccid : List1)
				{
					if (EmptyUtil.notEmpty(iccid) && !IMEIList.contains(iccid)) IMEIList.add(iccid);
				}
			}
		}

		return IMEIList;
	}

	/**
	 * @return 返回SIM卡1的 IMEI或MEID
	 */
	@SuppressLint("HardwareIds")
	public static String getDefaultDeviceId()
	{
		String defaultDeviceId = null;
		try
		{
			TelephonyManager telephonyManager = (TelephonyManager) BaseUtils.getContext().getSystemService(TELEPHONY_SERVICE);
			if (null != telephonyManager)
			{
				defaultDeviceId = telephonyManager.getDeviceId();
				LogProxy.i(TAG, "getDefaultDeviceId=" + StringUtil.getPrintString(defaultDeviceId));
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getDefaultDeviceId", e);
			e.printStackTrace();
		}
		if (EmptyUtil.isEmpty(defaultDeviceId))
		{
			defaultDeviceId = getDeviceIdByInvoke();
		}
		return defaultDeviceId;
	}

	/**
	 * Requires Permission: <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 *
	 * @return List<String> IMEI号码集合
	 */
	@SuppressLint("HardwareIds")
	public static List<String> getDeviceIdList()
	{
		List<String> ImeIList = new ArrayList<>();
		try
		{
			String defaultDeviceId = getDefaultDeviceId();
			if (!TextUtils.isEmpty(defaultDeviceId) && !ImeIList.contains(defaultDeviceId)) ImeIList.add(defaultDeviceId);

			TelephonyManager telephonyManager = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			if (null != telephonyManager)
			{
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
				{
					try
					{
						String deviceId_1 = telephonyManager.getDeviceId(-1);
						if (!TextUtils.isEmpty(deviceId_1) && !ImeIList.contains(deviceId_1)) ImeIList.add(deviceId_1);
						LogProxy.i(TAG, "getDeviceIdList getDeviceId(-1)=" + StringUtil.getPrintString(deviceId_1));

						String deviceId0 = telephonyManager.getDeviceId(0);
						if (!TextUtils.isEmpty(deviceId0) && !ImeIList.contains(deviceId0)) ImeIList.add(deviceId0);
						LogProxy.i(TAG, "getDeviceIdList getDeviceId(0)=" + StringUtil.getPrintString(deviceId0));

						String deviceId1 = telephonyManager.getDeviceId(1);
						if (!TextUtils.isEmpty(deviceId1) && !ImeIList.contains(deviceId1)) ImeIList.add(deviceId1);
						LogProxy.i(TAG, "getDeviceIdList getDeviceId(1)=" + StringUtil.getPrintString(deviceId1));

						String deviceId2 = telephonyManager.getDeviceId(2);
						if (!TextUtils.isEmpty(deviceId2) && !ImeIList.contains(deviceId2)) ImeIList.add(deviceId2);
						LogProxy.i(TAG, "getDeviceIdList getDeviceId(2)=" + StringUtil.getPrintString(deviceId2));
					} catch (Exception e)
					{
						LogProxy.e(TAG, "getDeviceIdList", e);
						e.printStackTrace();
					}
				}

				//利用反射读取
				String deviceId5 = getImeIByInvoke();
				if (!TextUtils.isEmpty(deviceId5) && !ImeIList.contains(deviceId5)) ImeIList.add(deviceId5);
				LogProxy.i(TAG, "getDeviceIdList -invoke(getImei/getMeid)=" + StringUtil.getPrintString(deviceId5));

				String deviceId6 = getDeviceIdByInvoke(-1);
				if (!TextUtils.isEmpty(deviceId6) && !ImeIList.contains(deviceId6)) ImeIList.add(deviceId6);
				LogProxy.i(TAG, "getDeviceIdList -invoke(getDeviceId(-1))=" + deviceId6);

				String deviceId7 = getDeviceIdByInvoke(0);
				if (!TextUtils.isEmpty(deviceId7) && !ImeIList.contains(deviceId7)) ImeIList.add(deviceId7);
				LogProxy.i(TAG, "getDeviceIdList -invoke(getDeviceId(0))=" + deviceId7);

				String deviceId8 = getDeviceIdByInvoke(1);
				if (!TextUtils.isEmpty(deviceId8) && !ImeIList.contains(deviceId8)) ImeIList.add(deviceId8);
				LogProxy.i(TAG, "getDeviceIdList -invoke(getDeviceId(1))=" + deviceId7);

				String deviceId9 = getDeviceIdByInvoke(2);
				if (!TextUtils.isEmpty(deviceId9) && !ImeIList.contains(deviceId9)) ImeIList.add(deviceId9);
				LogProxy.i(TAG, "getDeviceIdList -invoke(getDeviceId(2))=" + deviceId7);
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getDeviceIdList", e);
			e.printStackTrace();
		}
		return ImeIList;
	}

	private static String getDeviceIdByInvoke()
	{
		try
		{
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;
			Method method = telephonyManagerClass.getMethod("getDeviceId");
			Object object = method.invoke(telephonyManager);
			return (String) object;
		}catch (Exception e)
		{
			LogProxy.e(TAG, "getDeviceIdByInvoke", e);
			e.printStackTrace();
		}
		return "";
	}

	private static String getDeviceIdByInvoke(int slotIndex)
	{
		try
		{
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;
			Method                  method                = telephonyManagerClass.getMethod("getDeviceId", int.class);
			return (String) method.invoke(telephonyManager, slotIndex);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getDeviceIdByInvoke", e);
			e.printStackTrace();
		}
		return "";
	}

	private static String getImeIByInvoke()
	{
		try
		{
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;
			Method                  method                = telephonyManagerClass.getMethod("getImei");
			String                  imei                  = (String) method.invoke(telephonyManager);
			if (EmptyUtil.notEmpty(imei))
			{
				if (imei.length() >= 14)
				{
					return imei;
				} else
				{
					return getMeiDByInvoke();
				}
			}
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getImeIByInvoke", e);
			e.printStackTrace();
		}
		return "";
	}

	private static String getMeiDByInvoke()
	{
		try
		{
			TelephonyManager        telephonyManager      = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
			Class<TelephonyManager> telephonyManagerClass = TelephonyManager.class;
			Method                  method                = telephonyManagerClass.getMethod("getMeid");
			return (String) method.invoke(telephonyManager);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getMeiDByInvoke", e);
			e.printStackTrace();
		}
		return "";
	}
}
