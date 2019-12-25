package com.georgebindragon.base.thread;

import android.os.Handler;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 *
 * 描述：有除颤功能的Handler
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class DebounceHandler
{
	private static final String TAG = "DebounceHandler-->";

	private static final Handler defaultHandler = new Handler();

	protected Handler  handler;
	protected long     debounceTimeMillis;
	protected Runnable callbackRunnable;

	public DebounceHandler(Handler handler, Runnable callbackRunnable, long debounceTimeMillis)
	{
		this.handler = null == handler ? defaultHandler : handler;
		this.debounceTimeMillis = debounceTimeMillis > 0 ? debounceTimeMillis : 1000;
		this.callbackRunnable = null == callbackRunnable ? () -> LogProxy.i(TAG, "run-->callbackRunnable can't be null! ") : callbackRunnable;
	}

	public void destroy()
	{
		handler.removeCallbacks(callbackRunnable);
	}

	public void onDebounce()
	{
		handler.removeCallbacks(callbackRunnable);
		handler.postDelayed(callbackRunnable, debounceTimeMillis);
	}
}
