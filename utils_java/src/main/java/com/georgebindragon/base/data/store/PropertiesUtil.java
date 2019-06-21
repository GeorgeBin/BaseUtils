package com.georgebindragon.base.data.store;


import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.file.FileCheckUtil;
import com.georgebindragon.base.system.file.FileCreateUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 创建人：George
 * 类名称：PropertiesUtil
 * 类概述：读取和操作Properties文件的工具类
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PropertiesUtil
{
	private static final String TAG = "PropertiesUtil-->";

	/* <----------------------------------------------------------对外方法：获取Properties对象---------------------------------------------------------->*/
	private static boolean createFilePropertiesIfNotExists(String fileName)
	{
		if (EmptyUtil.notEmpty(fileName))
		{
			try
			{
				//检查文件是否存在，不存在则创建文件
				return FileCheckUtil.isFileExists(fileName) || FileCreateUtil.createFile(fileName);
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "createFileProperties", e.getMessage());
			}
		}
		return false;
	}

	public static Properties loadFilesProperties(String file)
	{
		return loadFilesProperties(null, file);
	}

	public static Properties loadFilesProperties(Properties defaults, String file)
	{
		Properties properties;
		if (null != defaults)
		{
			properties = defaults;//备注：这里的default和Properties内的default用法不同（即上面注释掉的用法）
		} else
		{
			properties = new Properties();
		}
		if (EmptyUtil.notEmpty(file))
		{
			FileInputStream fileInputStream = null;
			try
			{
				//createFilePropertiesIfNotExists(file);//检查文件是否存在，不存在则创建文件-->是否有可能误判, 造成文件的覆盖和丢失?
				boolean fileExists = FileCheckUtil.isFileExists(file);//检查文件是否存在
				LogProxy.d(TAG, "loadFilesProperties, 配置文件是否存在: " + (fileExists ? "存在" : "不存在"));
				if (fileExists)
				{
					fileInputStream = new FileInputStream(file);
					properties.load(fileInputStream);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				LogProxy.e(TAG, "loadFilesProperties, e=", e);
			} finally
			{
				try
				{
					if (fileInputStream != null) fileInputStream.close();
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		return properties;
	}

	public static boolean savePropertiesToFiles(String file, Properties properties, boolean append)
	{
		boolean result = false;
		if (EmptyUtil.notEmpty(file) && null != properties)
		{
			FileOutputStream fileOutputStream = null;
			try
			{
				boolean fileExists = createFilePropertiesIfNotExists(file);//检查文件是否存在，不存在则创建文件
				if (fileExists)
				{
					fileOutputStream = new FileOutputStream(file, append);//false 为：覆盖，true 为：续写
					properties.store(fileOutputStream, "");
					fileOutputStream.flush();
					result = true;
				}
			} catch (Exception e)
			{
				result = false;
				e.printStackTrace();
				LogProxy.e(TAG, "savePropertiesToFiles, e=" + e.getMessage());
			} finally
			{
				try
				{
					if (fileOutputStream != null) fileOutputStream.close();
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		return result;
	}

	/* <----------------------------------------------------------对外方法：获取内容---------------------------------------------------------->*/
	public static int getInt(Properties properties, String key, int defaultValue)
	{

		int    result   = defaultValue;
		String property = getPropertyString(properties, key);
		if (EmptyUtil.notEmpty(property))
		{
			try
			{
				result = Integer.parseInt(property);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "getInt, e=" + e.getMessage());
				parseError(key, "int");
			}
		}
		return result;
	}

	public static int setInt(Properties properties, String key, int value)
	{
		try
		{
			return Integer.parseInt(setString(properties, key, "" + value));
		} catch (Exception e)
		{
			LogProxy.e(TAG, "setInt, e=" + e.getMessage());
			setError(key, "int");
		}
		return 0;
	}

	public static float getFloat(Properties properties, String key, float defaultValue)
	{
		float  result   = defaultValue;
		String property = getPropertyString(properties, key);
		if (EmptyUtil.notEmpty(property))
		{
			try
			{
				result = Float.parseFloat(property);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "getFloat, e=" + e.getMessage());
				parseError(key, "float");
			}
		}
		return result;
	}

	public static float setFloat(Properties properties, String key, float value)
	{
		try
		{
			return Float.parseFloat(setString(properties, key, "" + value));
		} catch (Exception e)
		{
			LogProxy.e(TAG, "setFloat, e=" + e.getMessage());
			setError(key, "float");
		}
		return 0;
	}

	public static double getDouble(Properties properties, String key, double defaultValue)
	{
		double result   = defaultValue;
		String property = getPropertyString(properties, key);
		if (EmptyUtil.notEmpty(property))
		{
			try
			{
				result = Double.parseDouble(property);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "getDouble, e=" + e.getMessage());
				parseError(key, "double");
			}
		}
		return result;
	}

	public static double setDouble(Properties properties, String key, double value)
	{
		try
		{
			return Double.parseDouble(setString(properties, key, "" + value));
		} catch (Exception e)
		{
			LogProxy.e(TAG, "setDouble, e=" + e.getMessage());
			setError(key, "double");
		}
		return 0;
	}

	public static boolean getBoolean(Properties properties, String key, boolean defaultValue)
	{
		boolean result   = defaultValue;
		String  property = getPropertyString(properties, key);
		if (EmptyUtil.notEmpty(property))
		{
			try
			{
				result = Boolean.parseBoolean(property);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "getBoolean", e);
				parseError(key, "boolean");
			}
		}
		return result;
	}

	public static boolean setBoolean(Properties properties, String key, boolean value)
	{
		try
		{
			return Boolean.parseBoolean(setString(properties, key, "" + value));
		} catch (Exception e)
		{
			LogProxy.e(TAG, "setBoolean, e=" + e.getMessage());
			setError(key, "boolean");
		}
		return false;
	}

	public static String getString(Properties properties, String key, String defaultValue)
	{
		String result = defaultValue;
		if (null != properties && EmptyUtil.notEmpty(key))
		{
			String property = getPropertyString(properties, key);
			if (EmptyUtil.notEmpty(property)) result = property;
		}
		return result;
	}

	private static String getPropertyString(Properties properties, String key)
	{
		if (null != properties && EmptyUtil.notEmpty(key))
		{
			return properties.getProperty(key);
		} else
		{
			return null;
		}
	}

	public static String setString(Properties properties, String key, String value)
	{
		if (null != properties && EmptyUtil.notEmpty(key))
		{
			Object exProperty = properties.setProperty(key, value);
			if (null != exProperty)
			{
				try
				{
					return String.valueOf(exProperty);
				} catch (Exception e)
				{
					LogProxy.e(TAG, "setString, e=" + e.getMessage());
					setError(key, "String");
				}
			}
		}
		return "";
	}

	/* <----------------------------------------------------------内部方法，用于报错---------------------------------------------------------->*/
	private static void parseError(String key, String target)
	{
		LogProxy.e(TAG, "can't parse property " + key + " as " + target);
	}

	private static void setError(String key, String target)
	{
		LogProxy.e(TAG, "can't parse property " + key + " as " + target);
	}
}
