package com.georgebindragon.base.system.hardware.gps;

import android.content.Context;
import android.location.LocationManager;

/**
 * 2017/4/28
 */

public class GPSUtil
{
	/**
	 * 判断定位是否可用
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isGPSOn(Context context)
	{
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean         providerEnabled = false;
		if (locationManager != null)
		{
			providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		return providerEnabled;
	}
}
