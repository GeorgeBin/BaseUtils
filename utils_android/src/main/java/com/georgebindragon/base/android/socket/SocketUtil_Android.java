package com.georgebindragon.base.android.socket;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.system.hardware.power.CPUWakeLock;
import com.georgebindragon.base.system.hardware.power.PowerWakeLock;
import com.georgebindragon.base.system.hardware.power.PowerWakeLockManager;
import com.georgebindragon.base.system.socket.SocketUtilInterface;
import com.georgebindragon.base.utils.TimeUtil;

/**
 * 创建人：George
 * 类名称：SocketUtil_Android
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SocketUtil_Android extends PowerWakeLockManager implements SocketUtilInterface
{
	private static final String TAG = "SocketUtil_Android-->";

	private static SocketUtil_Android ourInstance = new SocketUtil_Android();

	public static SocketUtil_Android getInstance() { return ourInstance; }

	@Override
	protected PowerWakeLock init()
	{
		return new CPUWakeLock(BaseUtils.getContext());
	}

	@Override
	public long getTimeStamp()
	{
		return TimeUtil.getMilliSecondsSinceBoot();
	}

	@Override
	public void keepCPUAwake()
	{
		lockForAWhile(1000);// 1s
	}
}
