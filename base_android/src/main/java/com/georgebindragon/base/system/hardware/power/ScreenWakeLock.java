package com.georgebindragon.base.system.hardware.power;

import android.content.Context;
import android.os.PowerManager;

public class ScreenWakeLock extends PowerWakeLock
{
	public ScreenWakeLock(final Context context)
	{
		super(context, PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP);
	}
}
