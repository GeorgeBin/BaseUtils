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

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * 创建人：George
 *
 * 描述：Android系统alarm的管理，根据ID进行区分，可以设置不同的参数
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class AlarmMonitorManager extends BaseListenerMonitorManager<Long, AlarmMonitor, MyAlarmListener>
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

	private static TreeSet<Object[]> alarm_waiting_set = new TreeSet<>(new ComparatorAlarm());

	private enum TSetData
	{
		ID,
		WAITTIME,
		PENDINGINTENT,
	}

	private static class ComparatorAlarm implements Comparator<Object[]>
	{
		@Override
		public int compare(Object[] lhs, Object[] rhs)
		{
			return (int) ((Long) (lhs[TSetData.ID.ordinal()]) - (Long) (rhs[TSetData.ID.ordinal()]));
		}
	}

	private void onBroadcastReceived(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (EmptyUtil.notEmpty(action) && AlarmUtil.alarmAction.equalsIgnoreCase(action))
		{
			LogProxy.d(TAG, "onBroadcastReceived-->Alarm");
			final long id  = intent.getLongExtra(AlarmUtil.EXTRA_ID, 0);
			final int  pid = intent.getIntExtra(AlarmUtil.EXTRA_PID, 0);

			synchronized (AlarmUtil.alarmAction)
			{
				if (0 == id || 0 == pid) return;

				if (pid != Process.myPid())
				{
					LogProxy.e(TAG, "onReceive 但 pid和当前线程不一致 id:%d, pid:%d, myPid:%d" + id + pid + Process.myPid());
					return;
				}

				Iterator<Object[]> it = alarm_waiting_set.iterator();
				while (it.hasNext())
				{
					Object[] next  = it.next();
					Long     curId = (Long) next[TSetData.ID.ordinal()];
					if (curId.equals(id))
					{
						it.remove();
						break;
					}
				}
			}
			AlarmMonitor monitor = getMonitor(id);
			if (null != monitor) monitor.notifyListenerOnAlarmReceive(id, intent);// 回调
		} else
		{
			LogProxy.d(TAG, "onBroadcastReceived-->action=" + StringUtil.getPrintString(action));
		}
	}

	public boolean startAlarm(long id, int after, final Context context)
	{
		LogProxy.i(TAG, "startAlarm: start a new one. id=" + id, "after=" + after);

		if (after < 0) return false;

		synchronized (AlarmUtil.alarmAction)
		{
			for (Object[] anAlarm_waiting_set : alarm_waiting_set)
			{
				if ((Long) (anAlarm_waiting_set[TSetData.ID.ordinal()]) == id)
				{
					long waitTime = (Long) anAlarm_waiting_set[TSetData.WAITTIME.ordinal()];

					LogProxy.e(TAG, "id exist=" + id, "waitTime=" + waitTime);
					return false;
				}
			}

			long          afterMilliSeconds = TimeUtil.getMilliSecondsSinceBoot() + after;
			PendingIntent pendingIntent     = AlarmUtil.setAlarm(id, afterMilliSeconds, context);
			if (null == pendingIntent) return false;

			alarm_waiting_set.add(new Object[]{id, afterMilliSeconds, pendingIntent});
		}

		AlarmMonitor monitor = getMonitor(id);
		if (null != monitor)
		{
			monitor.notifyListenerOnAlarmStart(id, after);// 回调
		}
		return true;
	}


	public boolean stopAlarm(long id, final Context context)
	{
		synchronized (AlarmUtil.alarmAction)
		{
			Iterator<Object[]> it = alarm_waiting_set.iterator();
			while (it.hasNext())
			{
				Object[] next = it.next();
				if ((Long) (next[TSetData.ID.ordinal()]) == id)
				{
					AlarmUtil.cancelAlarm(context, (PendingIntent) (next[TSetData.PENDINGINTENT.ordinal()]));
					it.remove();

					LogProxy.i(TAG, "it.hasNext()");
					AlarmMonitor monitor = getMonitor(id);
					if (null != monitor) monitor.notifyListenerOnAlarmStop(id);// 回调
					stopSomeKey(id);
					return true;
				}
			}
		}

		return false;
	}

	public void clearAllAlarm(Context context)
	{
		synchronized (AlarmUtil.alarmAction)
		{
			clearMap();

			for (Object[] anAlarm_waiting_set : alarm_waiting_set)
			{
				AlarmUtil.cancelAlarm(context, (PendingIntent) (anAlarm_waiting_set[TSetData.PENDINGINTENT.ordinal()]));
			}
			alarm_waiting_set.clear();
		}
	}
}
