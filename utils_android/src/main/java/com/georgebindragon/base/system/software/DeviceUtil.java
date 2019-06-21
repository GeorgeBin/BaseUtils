package com.georgebindragon.base.system.software;

import android.os.Build;

import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 2017/4/6
 */

public class DeviceUtil
{

	public static final  String Manufacturer_UnKnown_Manufacturer = "UnKnown_Manufacturer";//未知

	public static String getManufacturer()
	{
		String manufacturer = Build.MANUFACTURER;
		if (EmptyUtil.notEmpty(manufacturer))
		{
			return manufacturer;
		} else
		{
			return Manufacturer_UnKnown_Manufacturer;
		}
	}

	/**
	 * 获取机器的 主板
	 *
	 * @return 型号 eg：DJ032
	 */
	public static String getBoard()
	{
		String model = Build.BOARD;
		if (null != model)
		{
			return model;
		} else
		{
			return "";
		}
	}

	/**
	 * 获取机器品牌
	 *
	 * @return 品牌，eg：Apls
	 */
	public static String getBrand()
	{
		String brand = Build.BRAND;
		if (EmptyUtil.notEmpty(brand))
		{
			return brand;
		} else
		{
			return "";
		}
	}

	/**
	 * 获取机器的具体型号
	 *
	 * @return 型号 eg：L100
	 */
	public static String getModel()
	{
		String model = Build.MODEL;
		if (EmptyUtil.notEmpty(model))
		{
			return model;
		} else
		{
			return "";
		}
	}
}
