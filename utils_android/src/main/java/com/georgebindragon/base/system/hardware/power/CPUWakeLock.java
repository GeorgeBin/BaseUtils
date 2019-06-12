package com.georgebindragon.base.system.hardware.power;

import android.content.Context;
import android.os.PowerManager;

public class CPUWakeLock extends PowerWakeLock
{
	public CPUWakeLock(final Context context)
	{
		super(context, PowerManager.PARTIAL_WAKE_LOCK);
	}
}
