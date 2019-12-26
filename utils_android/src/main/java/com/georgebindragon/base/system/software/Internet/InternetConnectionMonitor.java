package com.georgebindragon.base.system.software.Internet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.monitor.BaseListenerMonitor;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.system.software.InternetConnectionUtil;
import com.georgebindragon.base.utils.EmptyUtil;

import java.util.Queue;

/**
 * 创建人：George
 *
 * 描述：网络连接监听器：网络是否连接、网络连接类型
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class InternetConnectionMonitor extends BaseListenerMonitor<InternetConnectionMonitor.InternetConnectionListener> implements IBaseReceiverCallBack
{
	private static final String TAG                 = "InternetConnectionMonitor-->";
	private static final String ACTION_CONNECTIVITY = ConnectivityManager.CONNECTIVITY_ACTION;

	private static final InternetConnectionMonitor ourInstance = new InternetConnectionMonitor();

	public static InternetConnectionMonitor getInstance() { return ourInstance; }

	private InternetConnectionMonitor()
	{
		UtilsActions.getInstance().listenSomeKey(ACTION_CONNECTIVITY, this);
		LogProxy.d(TAG, "注册");
	}

	public void init()
	{
		LogProxy.d(TAG, "init-->");
	}

	public void destroy()
	{
		UtilsActions.getInstance().stopSomeListener(ACTION_CONNECTIVITY, this);
		LogProxy.d(TAG, "destroy-->注销完成");
	}

	private boolean lastIsConnected = false;
	private int     lastType        = -1;

	private void checkAndNotify(boolean isConnected, int type)
	{
		LogProxy.i(TAG, "checkAndNotify-->isConnected=" + isConnected, "type=" + type, "lastIsConnected=" + lastIsConnected, "lastType=" + lastType);

		if (lastIsConnected != isConnected || lastType != type)
		{
			notifyListeners(isConnected, type);
			lastIsConnected = isConnected;
			lastType = type;
		}
	}

	private void notifyListeners(boolean isConnected, int type)
	{
		final Queue<InternetConnectionListener> listeners = getListenerList();
		if (EmptyUtil.notEmpty(listeners))
		{
			for (InternetConnectionListener listener : listeners)
			{
				if (EmptyUtil.notEmpty(listener)) listener.onInternetConnectionChange(isConnected, type);
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (EmptyUtil.isEmpty(intent)) return;
		String action = intent.getAction();
		if (EmptyUtil.isEmpty(action)) return;

		if (ACTION_CONNECTIVITY.equals(action))
		{
			boolean connected = InternetConnectionUtil.isConnected(BaseUtils.getContext());
			int     netType   = InternetConnectionUtil.getNetType(BaseUtils.getContext());
			checkAndNotify(connected, netType);
			LogProxy.i(TAG, "onReceive-->connected=" + connected, "netType=" + netType);
		}
	}

	public interface InternetConnectionListener
	{
		/**
		 * @param isConnected 网络是否连接上
		 * @param type        the type on current internet connection
		 *                    {@link InternetConnectionUtil#NON_NETWORK}
		 *                    {@link InternetConnectionUtil#WIFI       }
		 *                    {@link InternetConnectionUtil#UNINET     }
		 *                    {@link InternetConnectionUtil#UNIWAP     }
		 *                    {@link InternetConnectionUtil#WAP_3G     }
		 *                    {@link InternetConnectionUtil#NET_3G     }
		 *                    {@link InternetConnectionUtil#CMWAP      }
		 *                    {@link InternetConnectionUtil#CMNET      }
		 *                    {@link InternetConnectionUtil#CTWAP      }
		 *                    {@link InternetConnectionUtil#CTNET       }
		 *                    {@link InternetConnectionUtil#MOBILE      }
		 *                    {@link InternetConnectionUtil#LTE         }
		 */
		void onInternetConnectionChange(boolean isConnected, int type);
	}

}
