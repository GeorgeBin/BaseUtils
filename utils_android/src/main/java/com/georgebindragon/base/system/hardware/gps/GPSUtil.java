package com.georgebindragon.base.system.hardware.gps;

import android.content.Context;
import android.location.LocationManager;

import java.util.List;

/**
 * 创建人：George
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class GPSUtil
{

	/**
	 * 判断是否有GPS硬件
	 *
	 * @return {@code true}: 是 {@code false}: 否
	 */
	public static boolean hasGPSHardware(Context context)
	{
		final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (mgr == null) return false;
		final List<String> providers = mgr.getAllProviders();
		if (providers == null) return false;
		return providers.contains(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 判断定位是否可用
	 *
	 * @return {@code true}: 是 {@code false}: 否
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
