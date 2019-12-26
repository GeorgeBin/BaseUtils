package com.georgebindragon.base.system.hardware.call;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;

import java.lang.reflect.Method;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class MyPhoneStateListener extends PhoneStateListener
{
	private static final String TAG = "SignalStrengthListener-->";

	private static final MyPhoneStateListener ourInstance = new MyPhoneStateListener();

	public static MyPhoneStateListener getInstance() { return ourInstance; }

	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength)
	{
		super.onSignalStrengthsChanged(signalStrength);
		LogProxy.i(TAG, "onSignalStrengthsChanged-->signalStrength=" + StringUtil.getPrintString(signalStrength) + "\n");

		int level = -1;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
		{
			level = signalStrength.getLevel();
		} else
		{
			try
			{
				Method getLevel = SignalStrength.class.getDeclaredMethod("getLevel");
				getLevel.setAccessible(true);
				level = (int) getLevel.invoke(signalStrength);
			} catch (Exception e)
			{
				LogProxy.e(TAG, "getLevel", e);
			}
		}
		LogProxy.i(TAG, "onSignalStrengthsChanged-->level=" + level);

		int dbm = -1;
		try
		{
			Method getDbm = SignalStrength.class.getDeclaredMethod("getDbm");
			getDbm.setAccessible(true);
			dbm = (int) getDbm.invoke(signalStrength);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getDbm", e);
		}
		LogProxy.i(TAG, "onSignalStrengthsChanged-->dbm=" + dbm);

		int asu = -1;
		try
		{
			Method getAsuLevel = SignalStrength.class.getDeclaredMethod("getAsuLevel");
			getAsuLevel.setAccessible(true);
			asu = (int) getAsuLevel.invoke(signalStrength);
		} catch (Exception e)
		{
			LogProxy.e(TAG, "getAsuLevel", e);
		}
		LogProxy.i(TAG, "onSignalStrengthsChanged-->asu=" + asu);

		int newStrength;
		if (signalStrength.isGsm())
		{
			newStrength = signalStrength.getGsmSignalStrength();
		} else
		{
			newStrength = signalStrength.getCdmaDbm();
		}
		LogProxy.i(TAG, "onSignalStrengthsChanged-->newStrength=" + newStrength);

		PhoneSignalMonitor.getInstance().onSignalStrengthChange(level, dbm, asu, newStrength);
	}
}
