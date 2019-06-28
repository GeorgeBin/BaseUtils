package com.georgebindragon.base.system.hardware.camera;

import android.hardware.Camera;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：CameraFlashUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CameraFlashUtil
{
	private static final String TAG = "CameraFlashUtil-->";

	/**
	 * 关闭手电筒
	 */
	private void closeFlashLight(Camera camera)
	{
		try
		{
			Camera.Parameters parameters = camera.getParameters();
			String            flashMode  = parameters.getFlashMode();
			if (!flashMode.equalsIgnoreCase(Camera.Parameters.FLASH_MODE_OFF))
			{
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				camera.setParameters(parameters);
				camera.stopPreview();
				camera.release();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "调用闪光灯错误closeFlashLight：", e.getMessage());
		}
	}

	/**
	 * 打开手电筒
	 */
	private void openFlashLight(Camera camera)
	{
		try
		{
			Camera.Parameters parameters = camera.getParameters();
			String            flashMode  = parameters.getFlashMode();
			if (!flashMode.equalsIgnoreCase(Camera.Parameters.FLASH_MODE_TORCH))
			{
				camera.startPreview();
				Camera.Parameters parameters2 = camera.getParameters();
				parameters2.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters2);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			LogProxy.e(TAG, "调用闪光灯错误openFlashLight：", e.getMessage());
		}
	}
}
