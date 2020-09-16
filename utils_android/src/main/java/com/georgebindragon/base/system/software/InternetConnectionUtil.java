package com.georgebindragon.base.system.software;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;


/**
 * 2017/5/16
 */

public class InternetConnectionUtil
{
	public static final String TAG = "InternetConnectionUtil-->";

	@SuppressLint("MissingPermission")
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager manager = getConnectivityManager(context);
		if (null != manager)
		{
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			boolean     result      = (EmptyUtil.notEmpty(networkinfo) && networkinfo.isAvailable());
			LogProxy.d(TAG, "isNetworkAvailable=" + result);
			return result;
		}
		return false;
	}

	public static boolean isAirPlaneModeOn(Context context)
	{
		if (Build.VERSION.SDK_INT >= 17)
		{
			int mode = 0;
			try
			{
				mode = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON);
			} catch (Settings.SettingNotFoundException e)
			{
				e.printStackTrace();
			}
			return mode == 1;//为1的时候是飞行模式
		} else
		{
			return false;
		}
	}

	public static final int NON_NETWORK = -1;
	public static final int WIFI        = 0;
	public static final int UNINET      = 1;
	public static final int UNIWAP      = 2;
	public static final int WAP_3G      = 3;
	public static final int NET_3G      = 4;
	public static final int CMWAP       = 5;
	public static final int CMNET       = 6;
	public static final int CTWAP       = 7;
	public static final int CTNET       = 8;
	public static final int LTE         = 10;

	public static final int Ethernet = 9;

	public static final int Unknown = 21;

	@Deprecated
	public static final int MOBILE = Unknown;

	public static boolean isConnected(Context context)
	{
		boolean             connect = false;
		ConnectivityManager conMan  = getConnectivityManager(context);
		if (null != conMan)
		{
			NetworkInfo activeNetInfo = conMan.getActiveNetworkInfo();
			try { connect = activeNetInfo.isConnected(); } catch (Exception ignore) { }
		}
		return connect;
	}

	public static boolean isMobile(Context context)
	{
		try
		{
			return isConnected(context) && !isWifi(context) && !isEthernet(context);
		} catch (Exception e) { LogProxy.e(TAG, "isMobile", e); }

		return false;
	}

	public static boolean isEthernet(Context context)
	{
		try
		{
			int type = getNetType(context);
			return type == Ethernet;
		} catch (Exception e) { LogProxy.e(TAG, "isEthernet", e); }

		return false;
	}

	private static int getMobileSubtype(Context context)
	{
		try
		{
			if (isMobile(context))
			{
				ConnectivityManager manager = getConnectivityManager(context);
				if (null != manager)
				{
					final NetworkInfo netInfo = manager.getActiveNetworkInfo();
					if (null != netInfo)
					{
						return netInfo.getSubtype();
					}
				}
			}
		} catch (Exception e) { LogProxy.e(TAG, "getMobileSubtype", e); }
		return -1;
	}

	public static boolean is2G(Context context)
	{
		try
		{
			int mobileSubtype = getMobileSubtype(context);
			if (mobileSubtype >= 0)
			{
				if (mobileSubtype == TelephonyManager.NETWORK_TYPE_GPRS
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_EDGE
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_CDMA
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_1xRTT
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_IDEN
				)
				{
					return true;
				}
			}
		} catch (Exception e) { LogProxy.e(TAG, "is2G", e); }
		return false;
	}


	public static boolean is3G(Context context)
	{
		try
		{
			int mobileSubtype = getMobileSubtype(context);
			if (mobileSubtype >= 0)
			{
				if (mobileSubtype == TelephonyManager.NETWORK_TYPE_UMTS
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_EVDO_0
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_EVDO_A
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_HSDPA
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_HSUPA
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_HSPA
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_EVDO_B
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_EHRPD
						|| mobileSubtype == TelephonyManager.NETWORK_TYPE_HSPAP
				)
				{
					return true;
				}
			}
		} catch (Exception e) { LogProxy.e(TAG, "is3G", e); }
		return false;
	}

	public static boolean is23G(Context context)
	{
		return is2G(context) || is3G(context);
	}

	public static boolean is4G(Context context)
	{
		try
		{
			int mobileSubtype = getMobileSubtype(context);
			return mobileSubtype == TelephonyManager.NETWORK_TYPE_LTE; // 5G is coming
		} catch (Exception e) { LogProxy.e(TAG, "is4G", e); }
		return false;
	}

	public static boolean is5G(Context context)
	{
		try
		{
			int mobileSubtype = getMobileSubtype(context);
			return mobileSubtype == 20; // 5G
		} catch (Exception e) { LogProxy.e(TAG, "is5G", e); }
		return false;
	}

	public static boolean isWap(Context context)
	{
		int type = getNetType(context);
		return isWap(type);
	}

	public static boolean isWap(int type)
	{
		return type == UNIWAP || type == CMWAP || type == CTWAP || type == WAP_3G;
	}

	public static boolean isWifi(Context context)
	{
		int type = getNetType(context);
		return WIFI == type;
	}

	public static int getNetType(Context context)
	{
		ConnectivityManager connectivityManager = getConnectivityManager(context);
		if (connectivityManager == null) { return NON_NETWORK; }

		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null) { return NON_NETWORK; }

		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
		{
			return WIFI;
		} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_ETHERNET)
		{
			return Ethernet;
		} else
		{
			String extraInfo = activeNetInfo.getExtraInfo();
			if (EmptyUtil.notEmpty(extraInfo))
			{
				String info = extraInfo.toUpperCase();
				switch (info)
				{
					default:
						return Unknown;
					case "UNINET":
						return UNINET;
					case "UNIWAP":
						return UNIWAP;
					case "3GWAP":
						return WAP_3G;
					case "3GNET":
						return NET_3G;
					case "CMWAP":
						return CMWAP;
					case "CMNET":
						return CMNET;
					case "CTWAP":
						return CTWAP;
					case "CTNET":
						return CTNET;
					case "LTE":
						return LTE;
				}
			}
			return Unknown;
		}
	}

	public static ConnectivityManager getConnectivityManager(Context context)
	{
		if (null == context) context = BaseUtils.getContext();
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
}
