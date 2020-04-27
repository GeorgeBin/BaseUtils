package com.georgebindragon.base.data.constants;

import com.georgebindragon.base.system.software.DeviceUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：Manufacturers
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class Manufacturers
{
	//
	public static final String Manufacturer = DeviceUtil.getManufacturer();
	public static final String Model        = DeviceUtil.getModel();

	//知名手机厂商
	public static final String Manufacturer_Xiaomi  = "Xiaomi";//小米
	public static final String Manufacturer_Huawei  = "Huawei";//华为
	public static final String Manufacturer_samsung = "samsung";//三星
	public static final String MANUFACTURER_Meizu   = "Meizu";//魅族
	public static final String MANUFACTURER_OPPO    = "OPPO";
	public static final String MANUFACTURER_vivo    = "vivo";
	public static final String MANUFACTURER_ZTE     = "ZTE";//中兴
	public static final String MANUFACTURER_YuLong  = "YuLong";//酷派
	public static final String MANUFACTURER_LG      = "LG";
	public static final String MANUFACTURER_Sony    = "Sony";//索尼
	public static final String MANUFACTURER_Letv    = "Letv";//乐视
	public static final String MANUFACTURER_LENOVO  = "LENOVO";//联想
	public static final String MANUFACTURER_OnePlus = "OnePlus";//一加

	//其他厂商
	//常见的分辨不出来的厂商
	public static final String Manufacturer_alps = "alps";
	public static final String Model_TELO        = "TELO";//意云的机型-->不能用Media播放提示音

	//行业内厂商
	//模块商
	public static final String Manufacturer_UNIPRO       = "UNIPRO";//宇佑
	public static final String Manufacturer_ELINK        = "ELINK";//易联科技
	public static final String Manufacturer_ALK          = "ALK";//阿乐卡
	public static final String Manufacturer_Simware      = "Simware";//信位
	public static final String Manufacturer_HF           = "HF";//华飞
	//
	public static final String Manufacturer_YiMingShiDai = "YiMingShiDai";//世纪天元
	public static final String Manufacturer_BoPTT        = "BoPTT";//泉盛
	public static final String Manufacturer_Quiswise     = "Quiswise";//信源达

	public static boolean isMediaPlayerDisable()
	{
		if (isSomeManufacturer(Manufacturer_alps))
		{
			if (Model.contains(Model_TELO)) return true;
		}
		return isSomeManufacturer(Manufacturer_samsung);
	}

	public static boolean isSoundPoolDisable()
	{
		return isSomeManufacturer(Manufacturer_UNIPRO, Manufacturer_ELINK, Manufacturer_ALK, Manufacturer_HF, Manufacturer_Quiswise);
	}

	public static boolean isNormalCellPhone()
	{
		return isSomeManufacturer(Manufacturer_Xiaomi, Manufacturer_Huawei, Manufacturer_samsung, MANUFACTURER_Meizu, MANUFACTURER_OPPO, MANUFACTURER_vivo, MANUFACTURER_ZTE,
				MANUFACTURER_YuLong, MANUFACTURER_LG, MANUFACTURER_Sony, MANUFACTURER_Letv, MANUFACTURER_LENOVO,MANUFACTURER_OnePlus);
	}

	public static boolean isXiaomi()
	{
		return isSomeManufacturer(Manufacturer_Xiaomi);
	}

	public static boolean isHuawei()
	{
		return isSomeManufacturer(Manufacturer_Huawei);
	}

	public static boolean isSomeManufacturer(String... targets)
	{
		if (null != targets && targets.length > 0)
		{
			for (String manufacturer : targets)
			{
				if (EmptyUtil.notEmpty(manufacturer))
				{
					boolean equals = Manufacturer.equalsIgnoreCase(manufacturer);
					if (equals) return true;
				}
			}
		}
		return false;
	}
}
