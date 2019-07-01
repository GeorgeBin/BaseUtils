package com.georgebindragon.base.system.hardware.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitorManager;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;
import com.georgebindragon.base.utils.TimeUtil;

/**
 * 创建人：George
 * 类名称：AlarmMonitorManager
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AlarmMonitorManager extends BaseListenerMonitorManager<Long, AlarmMonitor, AlarmMonitor.MyAlarm>
{

	private static final AlarmMonitorManager ourInstance = new AlarmMonitorManager();

	public static AlarmMonitorManager getInstance() { return ourInstance; }

	private AlarmMonitorManager()
	{
		UtilsActions.getInstance().listenSomeKey(AlarmUtil.alarmAction, this::onBroadcastReceived);
	}

	@Override
	protected AlarmMonitor onMonitorFirstCreate(Long key)
	{
		return new AlarmMonitor();
	}

	private void onBroadcastReceived(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (EmptyUtil.notEmpty(action) && AlarmUtil.alarmAction.equalsIgnoreCase(action))
		{
			LogProxy.d(TAG, "onBroadcastReceived-->Alarm");

			final long id  = intent.getLongExtra(AlarmUtil.EXTRA_ID, 0);
			final int  pid = intent.getIntExtra(AlarmUtil.EXTRA_PID, 0);

			if (0 == id || 0 == pid) return;

			if (pid != Process.myPid())
			{
				LogProxy.e(TAG, "onReceive 但 pid和当前线程不一致 id:%d, pid:%d, myPid:%d" + id + pid + Process.myPid());
				return;
			}

			AlarmMonitor monitor = getMonitor(id);
			if (null != monitor) monitor.notifyListenerOnAlarmReceive(id, intent);// 回调
		} else
		{
			LogProxy.d(TAG, "onBroadcastReceived-->action=" + StringUtil.getPrintString(action));
		}
	}

	public boolean startAlarm(long id, int after, final Context context, AlarmMonitor.MyAlarm myAlarmListener)
	{
		LogProxy.i(TAG, "startAlarm: start a new one. id=" + id, "after=" + after);

		if (after < 0) return false;

		synchronized (AlarmUtil.alarmAction)
		{
			long afterMilliSeconds = TimeUtil.getMilliSecondsSinceBoot() + after;

			PendingIntent pendingIntent = AlarmUtil.setAlarm(id, afterMilliSeconds, context);
			if (null == pendingIntent) return false;

			listenSomeKey(id, myAlarmListener);

			AlarmMonitor monitor = getMonitor(id);
			if (null != monitor)
			{
				monitor.setPendingIntent(pendingIntent);
				monitor.notifyListenerOnAlarmStart(id, after);// 回调
			}
		}
		return true;
	}


	public boolean stopAlarm(long id, final Context context)
	{
		synchronized (AlarmUtil.alarmAction)
		{
			AlarmMonitor monitor = getMonitor(id);
			if (null != monitor)
			{
				PendingIntent pendingIntent = monitor.getPendingIntent();
				AlarmUtil.cancelAlarm(context, pendingIntent);
				monitor.notifyListenerOnAlarmStop(id);// 回调
			}

			stopSomeKey(id);
		}

		return false;
	}

	public void clearAllAlarm(Context context)
	{
		UtilsActions.getInstance().stopSomeKey(AlarmUtil.alarmAction);
		clearMap();
	}
}
