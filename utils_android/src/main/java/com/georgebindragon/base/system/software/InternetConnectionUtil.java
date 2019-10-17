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
		ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (EmptyUtil.notEmpty(manager))
		{
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			boolean     result      = (EmptyUtil.notEmpty(networkinfo) && networkinfo.isAvailable());
			LogProxy.d(TAG , "isNetworkAvailable=" + result);
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
	public static final int MOBILE      = 9;
	public static final int LTE         = 10;

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
			ConnectivityManager manager = getConnectivityManager(context);
			if (null != manager)
			{
				final NetworkInfo netInfo = manager.getActiveNetworkInfo();
				return netInfo.getType() != ConnectivityManager.TYPE_WIFI;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static boolean is2G(Context context)
	{
		try
		{
			ConnectivityManager manager = getConnectivityManager(context);
			if (null != manager)
			{
				final NetworkInfo netInfo = manager.getActiveNetworkInfo();
				if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
				{
					return false;
				}
				if (netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE || netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS || netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA)
				{
					return true;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}


	public static boolean is3G(Context context)
	{

		try
		{
			ConnectivityManager manager = getConnectivityManager(context);
			if (null != manager)
			{
				final NetworkInfo netInfo = manager.getActiveNetworkInfo();
				if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
				{
					return false;
				}
				if (netInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_EVDO_0 && netInfo.getSubtype() < TelephonyManager.NETWORK_TYPE_LTE)
				{
					return true;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static boolean is23G(Context context)
	{
		try
		{
			ConnectivityManager manager = getConnectivityManager(context);
			if (null != manager)
			{
				final NetworkInfo netInfo = manager.getActiveNetworkInfo();

				LogProxy.i(TAG, "is23G-->netInfo.getType()=" + netInfo.getType());
				LogProxy.i(TAG, "is23G-->netInfo.getSubtype()=" + netInfo.getSubtype());

				if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
				{
					return false;
				}
				if (netInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_UNKNOWN && netInfo.getSubtype() < TelephonyManager.NETWORK_TYPE_LTE)
				{
					return true;
				}
			}
		} catch (Exception ignore) { }
		return false;
	}

	public static boolean is4G(Context context)
	{
		try
		{
			ConnectivityManager manager = getConnectivityManager(context);
			if (null != manager)
			{
				final NetworkInfo netInfo = manager.getActiveNetworkInfo();

				if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
				{
					return false;
				}
				// TODO:may be 5G in the future
				if (netInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_LTE)
				{
					return true;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

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
		return isWifi(type);
	}

	public static boolean isWifi(int type)
	{
		return type == WIFI;
	}

	public static int getNetType(Context context)
	{

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null)
		{
			return NON_NETWORK;
		}
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null)
		{
			return NON_NETWORK;
		}

		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
		{
			return WIFI;

		} else
		{
			if (activeNetInfo.getExtraInfo() != null)
			{
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("uninet"))
				{
					return UNINET;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("uniwap"))
				{
					return UNIWAP;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("3gwap"))
				{
					return WAP_3G;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("3gnet"))
				{
					return NET_3G;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("cmwap"))
				{
					return CMWAP;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("cmnet"))
				{
					return CMNET;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("ctwap"))
				{
					return CTWAP;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("ctnet"))
				{
					return CTNET;
				}
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("LTE"))
				{
					return LTE;
				}
			}
			return MOBILE;
		}
	}

	public static ConnectivityManager getConnectivityManager(Context context)
	{
		if (null == context) context = BaseUtils.getContext();
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
}
