package com.georgebindragon.base.system.hardware.gps;

import static java.lang.Math.PI;

/**
 * 创建人：【引用】
 * 类名称：GPSCoordinateUtil
 * 类概述：GPS坐标系转换工具类
 * 详细描述：
 *
 * 引用来源：https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/CoordinateUtils.java
 * 引用版本：2018/04/08
 * 其他资料：中国地图坐标(GCJ-02)偏移算法破解小史：https://blog.csdn.net/zerokkqq/article/details/52920281
 *
 * 坐标系说明：
 *
 * 1. WGS84：地球坐标-->GPS、北斗
 * 2. GCJ02：国测局坐标（火星坐标）-->腾讯地图、阿里云地图、高德地图
 * 3. BD09：百度坐标-->百度地图
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class GPSCoordinateUtil
{
	private final static double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
	private final static double A    = 6378245.0;
	private final static double EE   = 0.00669342162296594323;

	/**
	 * BD09 坐标转 GCJ02 坐标
	 *
	 * @param lng BD09 坐标纬度
	 * @param lat BD09 坐标经度
	 * @return GCJ02 坐标：[经度，纬度]
	 */
	public static double[] bd09ToGcj02(double lng, double lat)
	{
		double x      = lng - 0.0065;
		double y      = lat - 0.006;
		double z      = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta  = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		double gg_lng = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new double[]{gg_lng, gg_lat};
	}

	/**
	 * GCJ02 坐标转 BD09 坐标
	 *
	 * @param lng GCJ02 坐标经度
	 * @param lat GCJ02 坐标纬度
	 * @return BD09 坐标：[经度，纬度]
	 */
	public static double[] gcj02ToBd09(double lng, double lat)
	{
		double z      = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
		double theta  = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
		double bd_lng = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new double[]{bd_lng, bd_lat};
	}

	/**
	 * GCJ02 坐标转 WGS84 坐标
	 *
	 * @param lng GCJ02 坐标经度
	 * @param lat GCJ02 坐标纬度
	 * @return WGS84 坐标：[经度，纬度]
	 */
	public static double[] gcj02ToWGS84(double lng, double lat)
	{
		if (outOfChina(lng, lat))
		{
			return new double[]{lng, lat};
		}
		double dlat   = transformLat(lng - 105.0, lat - 35.0);
		double dlng   = transformLng(lng - 105.0, lat - 35.0);
		double radlat = lat / 180.0 * PI;
		double magic  = Math.sin(radlat);
		magic = 1 - EE * magic * magic;
		double sqrtmagic = Math.sqrt(magic);
		dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
		dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
		double mglat = lat + dlat;
		double mglng = lng + dlng;
		return new double[]{lng * 2 - mglng, lat * 2 - mglat};
	}

	/**
	 * WGS84 坐标转 GCJ02 坐标
	 *
	 * @param lng WGS84 坐标经度
	 * @param lat WGS84 坐标纬度
	 * @return GCJ02 坐标：[经度，纬度]
	 */
	public static double[] wgs84ToGcj02(double lng, double lat)
	{
		if (outOfChina(lng, lat))
		{
			return new double[]{lng, lat};
		}
		double dlat   = transformLat(lng - 105.0, lat - 35.0);
		double dlng   = transformLng(lng - 105.0, lat - 35.0);
		double radlat = lat / 180.0 * PI;
		double magic  = Math.sin(radlat);
		magic = 1 - EE * magic * magic;
		double sqrtmagic = Math.sqrt(magic);
		dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
		dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
		double mglat = lat + dlat;
		double mglng = lng + dlng;
		return new double[]{mglng, mglat};
	}

	/**
	 * BD09 坐标转 WGS84 坐标
	 *
	 * @param lng BD09 坐标经度
	 * @param lat BD09 坐标纬度
	 * @return WGS84 坐标：[经度，纬度]
	 */
	public static double[] bd09ToWGS84(double lng, double lat)
	{
		double[] gcj = bd09ToGcj02(lng, lat);
		return gcj02ToWGS84(gcj[0], gcj[1]);
	}


	/**
	 * WGS84 坐标转 BD09 坐标
	 *
	 * @param lng WGS84 坐标经度
	 * @param lat WGS84 坐标纬度
	 * @return BD09 坐标：[经度，纬度]
	 */
	public static double[] wgs84ToBd09(double lng, double lat)
	{
		double[] gcj = wgs84ToGcj02(lng, lat);
		return gcj02ToBd09(gcj[0], gcj[1]);
	}

	private static double transformLat(double lng, double lat)
	{
		double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLng(double lng, double lat)
	{
		double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	public static boolean outOfChina(double lng, double lat)
	{
		return lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271;
	}

	public static boolean inChina(double lng, double lat)
	{
		return !outOfChina(lng, lat);
	}
}
