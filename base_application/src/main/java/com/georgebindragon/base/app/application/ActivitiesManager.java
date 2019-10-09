package com.georgebindragon.base.app.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.georgebindragon.base.function.log.LogProxy;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.util.Stack;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import io.reactivex.Observable;

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

			onActivityChanged();
			LogProxy.d(TAG, "addActivity-->activity=" + activity.getClass().getSimpleName(), "size=" + activityStack.size());

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
			onActivityChanged();
			LogProxy.d(TAG, "deleteActivity-->activity=" + activity.getClass().getSimpleName(), "size=" + activityStack.size());
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
	public Activity getCurrentActivity()
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
		}
	}

	/**
	 * 弹出除cls外的所有activity
	 *
	 * @param cls 要留下的
	 */
	public void deleteAllActivityExceptOne(Class<? extends Activity> cls)
	{
		LogProxy.d(TAG, "deleteAllActivityExceptOne-->the one=" + (cls == null ? "NULL" : cls.getSimpleName()));
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
		LogProxy.d(TAG, "finishAllActivityExceptOne-->the one=" + (cls == null ? "NULL" : cls.getSimpleName()));

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
			Activity activity = getCurrentActivity();
			finishActivity(activity);
		}
	}

	/*------------------------------------------------------------------- Activity 列表变化 回调 -------------------------------------------------------------------*/

	private BehaviorRelay<Activity> topActivity = BehaviorRelay.create();

	private void onActivityChanged()
	{
		Activity currentActivity = getCurrentActivity();
		if (null != currentActivity && currentActivity != lastActivity)
		{
			lastActivity = currentActivity;
			topActivity.accept(lastActivity);
		}
	}

	private Activity lastActivity = null;

	/**
	 * 获得当前的activity(即最上层)
	 *
	 * @return 最上层
	 */
	public Observable<Activity> getCurrentActivityObservable()
	{
		return topActivity;
	}

	/*------------------------------------------------------------------- Activity 生命周期 回调 -------------------------------------------------------------------*/


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

}
