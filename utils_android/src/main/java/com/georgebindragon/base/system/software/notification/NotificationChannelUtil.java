package com.georgebindragon.base.system.software.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.georgebindragon.base.BaseUtils;

/**
 * author：George
 *
 * description：
 *
 * modification：
 */
public class NotificationChannelUtil
{
    /**
     * @param context                     Context
     * @param channelId                   一个不重复的Id
     * @param channelName                 名称（会显示在通知设置页面）
     * @param channelDescription          描述（会显示在通知设置页面）
     * @param channelImportance           重要程度：LOW=中，则可以显示，但不弹出。HIGH=紧急，可以显示+弹出
     * @param enableVibrate               震动
     * @param enableLights                灯
     * @param channelLockScreenVisibility 锁屏可见
     * @return channelId
     */
    public static NotificationChannel makeNotificationChannel(Context context
            , String channelId, CharSequence channelName, String channelDescription
            , int channelImportance, boolean enableVibrate, boolean enableLights, int channelLockScreenVisibility)
    {
        if (null == context) context = BaseUtils.getContext();

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Initializes NotificationChannel.
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(enableVibrate);
            notificationChannel.enableLights(enableLights);
            notificationChannel.setLockscreenVisibility(channelLockScreenVisibility);
            return notificationChannel;
        } else
        {
            return null;// Returns null for pre-O (26) devices.
        }
    }

    public static boolean createNotificationChannel(Context context, NotificationChannel notificationChannel)
    {
        if (null == context) context = BaseUtils.getContext();
        if (null != notificationChannel && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) notificationManager.createNotificationChannel(notificationChannel);
            return true;
        } else
        {
            return false; // Returns null for pre-O (26) devices.
        }
    }
}
