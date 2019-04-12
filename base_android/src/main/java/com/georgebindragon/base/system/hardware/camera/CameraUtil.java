package com.georgebindragon.base.system.hardware.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 类名称：CameraUtil
 * 类概述：相机工具类
 * 详细描述：
 *
 *
 * 1、判断是否有相机
 * 2、判断相机数量
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CameraUtil
{
	private static final String TAG               = "MainActivity-->";
	private static final int    FACING_WRONG      = -2;
	private static final int    ORIENTATION_WRONG = -2;

	// 判断是否存在摄像头属性
	public static boolean hasCameraSupport(Context context)
	{
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	// 获取摄像头个数
	public static int getNumberOfCameras()
	{
		return Camera.getNumberOfCameras();
	}

	public static int getCameraFacing(int cameraId)
	{
		Camera.CameraInfo cameraInfo = getCameraInfo(cameraId);
		if (EmptyUtil.notEmpty(cameraInfo) && cameraInfo.facing != FACING_WRONG)
		{
			return cameraInfo.facing;
		}
		return FACING_WRONG;
	}

	public static int getCameraOrientation(int cameraId)
	{
		Camera.CameraInfo cameraInfo = getCameraInfo(cameraId);
		if (EmptyUtil.notEmpty(cameraInfo) && cameraInfo.facing != FACING_WRONG)
		{
			return cameraInfo.orientation;
		}
		return ORIENTATION_WRONG;
	}

	public static boolean isCameraIdExist(int cameraId)
	{
		Camera.CameraInfo cameraInfo = getCameraInfo(cameraId);
		if (EmptyUtil.notEmpty(cameraInfo))
		{
			return cameraInfo.facing != FACING_WRONG;
		}
		return false;
	}

	public static Camera.CameraInfo getCameraInfo(int cameraId)
	{
		Camera.CameraInfo info = new Camera.CameraInfo();//默认：0、0、false
		info.facing = FACING_WRONG;
		try
		{
			Camera.getCameraInfo(cameraId, info);
			return info;
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getCameraInfo", e);
			return null;
		}
	}
}
