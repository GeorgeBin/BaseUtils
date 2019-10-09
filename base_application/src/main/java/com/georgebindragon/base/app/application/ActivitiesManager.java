package com.georgebindragon.base.app.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * 创建人：George
 * 类名称：ActivitiesManager
 * 类概述：管理所有Activity页面
 *
 * 详细描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class ActivitiesManager implements Application.ActivityLifecycleCallbacks
{
	private static final String TAG = "ActivitiesManager-->";

	private static ActivitiesManager ourInstance = new ActivitiesManager();

	public static ActivitiesManager getInstance() { return ourInstance; }

	private ActivitiesManager() { }

	@MainThread
	public void init(@NonNull Application application)
	{
		QMUISwipeBackActivityManager.init(application);
		if (ourInstance != null) application.registerActivityLifecycleCallbacks(ourInstance);
	}

	private static Stack<Activity> activityStack = new Stack<>();

	/**
	 * 将activity推入栈内
	 *
	 * @param activity 要放入的
	 */
	public void addActivity(Activity activity)
	{
		if (!activityStack.contains(activity))
		{
			activityStack.push(activity);
			informListenersOnActivityChanged(currentActivity() == null ? null : currentActivity().getClass());
			LogProxy.d(TAG, "addActivity-->activity=" + activity.getClass() + "\tsize=" + activityStack.size());

		} else
		{
			deleteActivity(activity);
			addActivity(activity);
		}
	}

	/**
	 * 将activity移出栈
	 *
	 * @param activity 要清除的
	 */
	public void deleteActivity(Activity activity)
	{
		if (activity != null && activityStack.contains(activity))
		{
			activityStack.remove(activity);
			informListenersOnActivityChanged(currentActivity() == null ? null : currentActivity().getClass());
			LogProxy.d(TAG, "deleteActivity-->activity=" + activity.getClass() + "\tsize=" + activityStack.size());
		}
	}

	/**
	 * 是否包含某个activity
	 *
	 * @return 是否包含
	 */
	public boolean containsActivity(Class<? extends Activity> cls)
	{
		if (!activityStack.empty() && null != cls)
		{
			for (Activity ac : activityStack)
			{
				if (ac.getClass().equals(cls))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获得当前的activity(即最上层)
	 *
	 * @return 最上层
	 */
	public Activity currentActivity()
	{
		Activity activity = null;
		if (!activityStack.empty()) activity = activityStack.peek();
		return activity;
	}

	/**
	 * 结束指定activity
	 *
	 * @param activity finish掉
	 */
	public void finishActivity(Activity activity)
	{
		LogProxy.d(TAG, "finishActivity-->activity=" + (activity == null ? "NULL" : activity.getClass()));
		if (activity != null)
		{
			activity.finish();
			deleteActivity(activity);
			activity = null;
		}
	}

	/**
	 * 弹出除cls外的所有activity
	 *
	 * @param cls 要留下的
	 */
	public void deleteAllActivityExceptOne(Class<? extends Activity> cls)
	{
		LogProxy.d(TAG, "deleteAllActivityExceptOne-->the one=" + (cls == null ? "NULL" : cls.getClass()));
		if (null != cls && !activityStack.isEmpty())
		{
			for (int i = activityStack.size() - 1; i >= 0; i--)
			{
				Activity activity = activityStack.get(i);
				if (null != activity && !cls.equals(activity.getClass()))
				{
					deleteActivity(activity);
				}
			}
		}
	}

	/**
	 * 结束除cls之外的所有activity
	 *
	 * @param cls 要留下的
	 */
	public void finishAllActivityExceptOne(Class<? extends Activity> cls)
	{
		LogProxy.d(TAG, "finishAllActivityExceptOne-->the one=" + (cls == null ? "NULL" : cls.getClass()));
		if (null != cls && !activityStack.isEmpty())
		{
			for (int i = activityStack.size() - 1; i >= 0; i--)
			{
				Activity activity = activityStack.get(i);
				if (null != activity && !cls.equals(activity.getClass()))
				{
					finishActivity(activity);
				}
			}
		}
	}

	/**
	 * 结束所有activity
	 */
	public void finishAllActivity()
	{
		LogProxy.d(TAG, "finishAllActivity");
		while (!activityStack.empty())
		{
			Activity activity = currentActivity();
			finishActivity(activity);
		}
	}

	//监听回调
	private CopyOnWriteArrayList<ActivityChangedListener> activityChangedListenerList;

	public void addActivityChangedListener(ActivityChangedListener activityChangedListener)
	{
		if (EmptyUtil.isEmpty(activityChangedListener)) return;
		if (EmptyUtil.isEmpty(activityChangedListenerList))
		{
			activityChangedListenerList = new CopyOnWriteArrayList<>();
		}
		activityChangedListenerList.add(activityChangedListener);
	}

	public void removeActivityChangedListener(ActivityChangedListener activityChangedListener)
	{
		if (EmptyUtil.isEmpty(activityChangedListenerList)) return;
		activityChangedListenerList.remove(activityChangedListener);
	}

	private void informListenersOnActivityChanged(Class<? extends Activity> activityClass)
	{
		final CopyOnWriteArrayList<ActivityChangedListener> listeners = activityChangedListenerList;
		if (EmptyUtil.notEmpty(listeners))
		{
			for (ActivityChangedListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onActivityListChanged(activityClass);
			}
		}
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState)
	{
		addActivity(activity);
	}

	@Override
	public void onActivityStarted(Activity activity)
	{

	}

	@Override
	public void onActivityResumed(Activity activity)
	{

	}

	@Override
	public void onActivityPaused(Activity activity)
	{

	}

	@Override
	public void onActivityStopped(Activity activity)
	{

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState)
	{

	}

	@Override
	public void onActivityDestroyed(Activity activity)
	{
		deleteActivity(activity);
	}

	public interface ActivityChangedListener
	{
		/**
		 * 当app内已经实例化的activity列表的变动时回调
		 *
		 * @param currentActivityClass 返回的是当前最顶层的页面
		 */
		void onActivityListChanged(Class<? extends Activity> currentActivityClass);
	}
}
