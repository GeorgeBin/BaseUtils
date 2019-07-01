package com.georgebindragon.base.system.hardware.alarm;

import android.app.PendingIntent;
import android.content.Intent;

import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 * 类名称：AlarmMonitor
 * 类概述：系统alarm的监听器
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class AlarmMonitor extends BaseListenerMonitor<MyAlarm>
{
	private PendingIntent pendingIntent;

	PendingIntent getPendingIntent()
	{
		return pendingIntent;
	}

	void setPendingIntent(PendingIntent pendingIntent)
	{
		this.pendingIntent = pendingIntent;
	}

	void notifyListenerOnAlarmStart(long id, int after)
	{
		final Queue<MyAlarm> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarm listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener) && listener.alarmID() == id)
				{
					listener.onAlarmStart(id, after);
				}
			}
		}
	}

	void notifyListenerOnAlarmReceive(long id, Intent intent)
	{
		final Queue<MyAlarm> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarm listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener) && listener.alarmID() == id)
				{
					listener.onAlarmReceive(intent);
				}
			}
		}
	}

	void notifyListenerOnAlarmStop(long id)
	{
		final Queue<MyAlarm> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarm listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener) && listener.alarmID() == id)
				{
					listener.onAlarmStop(id);
				}
			}
		}
	}
}
