package com.georgebindragon.base.app.ui.activity.pro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.georgebindragon.base.app.R;
import com.georgebindragon.base.app.ui.activity.base.BaseActivity;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.system.software.ActivityUtil;
import com.georgebindragon.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 * 类名称：BaseActivityPro
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseActivityPro extends BaseActivity
{

	/*--------------------------------------------------------------------- 生命周期 ---------------------------------------------------------------------*/

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		LogProxy.d(TAG, "onCreate");

		super.onCreate(savedInstanceState);

		setContentView(getContentView());

		initView();
		initData();
	}

	@Override
	public void onStart()
	{
		LogProxy.d(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		LogProxy.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		LogProxy.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop()
	{
		LogProxy.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		LogProxy.d(TAG, "onDestroy");

		RxCommonUtil.dispose(disposableList);//注销

		super.onDestroy();
	}

	/*---------------------------------------------------------------------系统方法---------------------------------------------------------------------*/

	@Override
	public void finish()
	{
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);//页面切换方式

		super.finish();
	}

	/*--------------------------------------------------------------------- 自定义生命周期 ---------------------------------------------------------------------*/

	//返回layout视图
	protected abstract int getContentView();

	//初始化视图
	protected void initView() { LogProxy.d(TAG, "initView()"); }

	//初始化数据
	protected void initData() { LogProxy.d(TAG, "initData()"); }

	/*---------------------------------------------------------------------自定义方法---------------------------------------------------------------------*/

	protected List<Disposable> disposableList = new ArrayList<>();

	protected void addDisposable(Disposable disposable)
	{
		disposableList.add(disposable);
	}

	/**
	 * 注销RxJava
	 *
	 * @param disposable 需要注销的 Disposable
	 */
	public void dispose(Disposable disposable)
	{
		RxCommonUtil.dispose(disposable);
	}

	/*---------------------------------------------------------------------页面跳转---------------------------------------------------------------------*/

	/**
	 * 页面跳转
	 *
	 * @param clasz             需要跳转的页面
	 * @param needFinish        是否结束当前页
	 * @param delayMilliSeconds 跳转延迟
	 */
	public void jumpActivity(Class<? extends Activity> clasz, boolean needFinish, long delayMilliSeconds)
	{
		LogProxy.d(TAG, "jumpActivity-->delaySecond=" + delayMilliSeconds + " ms，要跳转的页面=" + (null == clasz ? "NULL" : clasz.getSimpleName()));
		Intent intent = null;
		if (null != clasz) intent = new Intent(this, clasz);
		jumpActivity(intent, needFinish, delayMilliSeconds);
	}

	public void jumpActivity(Intent intent, boolean needFinish, long delayMilliSeconds)
	{
		if (delayMilliSeconds <= 0)
		{
			jumpActivity(intent, needFinish);
		} else
		{
			jumpActivity(intent, needFinish, delayMilliSeconds / 10, 10, TimeUnit.MILLISECONDS);
		}
	}

	Disposable jumpTimer;
	private int count = 1;

	public void jumpActivity(final Intent intent, final boolean needFinish, final long delayCount, final long period, final TimeUnit unit)
	{
		LogProxy.i(TAG, "jumpActivity 2-->intent=" + intent, "needFinish=" + needFinish, "delayCount=" + delayCount, "period=" + period, "unit=" + unit);

		if (delayCount <= 0)
		{
			jumpActivity(intent, needFinish);
		} else
		{
			jumpTimer = Observable.interval(period, unit)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong ->
					{
						LogProxy.d(TAG, "accept count=" + count, "delayCount=" + delayCount);
						if (count >= delayCount)
						{
							jumpActivity(intent, needFinish);
							dispose(jumpTimer);//注销
						}
						count++;
						LogProxy.d(TAG, "count=" + count);
					});
			addDisposable(jumpTimer);
		}
	}

	public void jumpActivity(Class<? extends Activity> clasz, boolean needFinish)
	{
		Intent intent = null;
		if (null != clasz) intent = new Intent(this, clasz);
		jumpActivity(intent, needFinish);
	}

	public void jumpActivity(Intent intent, boolean needFinish)
	{
		LogProxy.i(TAG, "jumpActivity 1-->intent=" + StringUtil.getPrintString(intent), "needFinish=" + needFinish);

		ActivityUtil.jumpActivity(this,intent);
		if (needFinish) finish();
	}
}
