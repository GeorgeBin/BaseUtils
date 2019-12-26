package com.georgebindragon.base.system.hardware.call;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 *
 * 描述：手机信号监听器：手机信号强度各种参数
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class PhoneSignalMonitor extends BaseListenerMonitor<PhoneSignalMonitor.PhoneSignalListener>
{
	private static final String TAG = "PhoneSignalMonitor-->";

	private static final PhoneSignalMonitor ourInstance = new PhoneSignalMonitor();

	public static PhoneSignalMonitor getInstance() { return ourInstance; }

	private PhoneSignalMonitor() { }

	public void init()
	{
		listenToSignalStrengths();
		LogProxy.d(TAG, "init-->注册");
	}

	public void destroy()
	{
		stopListenToSignalStrengths();
		LogProxy.d(TAG, "destroy-->注销完成");
	}

	private void listenToSignalStrengths()
	{
		LogProxy.i(TAG, "listenToSignalStrengths-->");
		TelephonyManager telephonyManager = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (null != telephonyManager) telephonyManager.listen(MyPhoneStateListener.getInstance(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	}

	private void stopListenToSignalStrengths()
	{
		LogProxy.i(TAG, "stopListenToSignalStrengths-->");
		TelephonyManager telephonyManager = (TelephonyManager) BaseUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (null != telephonyManager) telephonyManager.listen(MyPhoneStateListener.getInstance(), PhoneStateListener.LISTEN_NONE);
	}

	private void notifyListeners(int level, int dbm, int asu, int newStrength)
	{
		final Queue<PhoneSignalListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (PhoneSignalListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onPhoneSignalChange(level, dbm, asu, newStrength);
			}
		}
	}

	private int lastLevel    = -1;
	private int lastDbm      = -1;
	private int lastAsu      = -1;
	private int lastStrength = -1;

	void onSignalStrengthChange(int level, int dbm, int asu, int newStrength)
	{
		LogProxy.i(TAG, "onSignalStrengthChange-->lastLevel=" + lastLevel, "level=" + level, "lastDbm=" + lastDbm, "dbm=" + dbm,
				"lastAsu=" + lastAsu, "asu=" + asu, "lastStrength=" + lastStrength, "newStrength=" + newStrength);

		lastLevel = level;
		lastDbm = dbm;
		lastAsu = asu;
		lastStrength = newStrength;
		notifyListeners(level, dbm, asu, newStrength);
	}

	public interface PhoneSignalListener
	{
		void onPhoneSignalChange(int level, int dbm, int asu, int newStrength);
	}
}
