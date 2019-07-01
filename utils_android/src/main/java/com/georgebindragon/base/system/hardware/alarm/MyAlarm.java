package com.georgebindragon.base.system.hardware.alarm;

import android.content.Intent;

/**
 * 创建人：George
 * 类名称：MyAlarm
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface MyAlarm
{
	long alarmID();//要监听的Alarm的ID

	void onAlarmStart(long id, int after);

	void onAlarmReceive(Intent intent);

	void onAlarmStop(long id);
}
