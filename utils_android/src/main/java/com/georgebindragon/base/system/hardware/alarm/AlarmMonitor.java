package com.georgebindragon.base.system.hardware.alarm;

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


class AlarmMonitor extends BaseListenerMonitor<MyAlarmListener>
{

	void notifyListenerOnAlarmStart(long id, int after)
	{
		final Queue<MyAlarmListener> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarmListener listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener))
				{
					listener.onAlarmStart(id, after);
				}
			}
		}
	}

	void notifyListenerOnAlarmReceive(long id, Intent intent)
	{
		final Queue<MyAlarmListener> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarmListener listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener))
				{
					listener.onAlarmReceive(intent);
				}
			}
		}
	}

	void notifyListenerOnAlarmStop(long id)
	{
		final Queue<MyAlarmListener> tempListenerList = getListenerList();
		if (EmptyUtil.notEmpty(tempListenerList))
		{
			for (MyAlarmListener listener : tempListenerList)
			{
				if (EmptyUtil.notEmpty(listener) )
				{
					listener.onAlarmStop(id);
				}
			}
		}
	}
}
