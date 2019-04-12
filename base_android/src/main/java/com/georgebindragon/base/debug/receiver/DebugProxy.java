package com.georgebindragon.base.debug.receiver;

import android.content.Context;
import android.media.AudioManager;

import com.georgebindragon.base.abilities.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.BroadcastReceiverUtil;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class DebugProxy
{
	private static final String TAG = "DebugProxy-->";

	private static final String DebugAction_Extra_Enable       = "enable";    //开关
	//
	private static final String DebugAction_Log                = "com.rongchat.debug:log";              //log
	private static final String DebugAction_Internet           = "com.rongchat.debug:internet";         //internet
	//音频调试
	private static final String DebugAction_Audio_Stream_Music = "com.rongchat.debug:audio.steam.music";  //切换Audio Stream为 多媒体
	private static final String DebugAction_Audio_Stream_Cal   = "com.rongchat.debug:audio.steam.call";   //切换Audio Stream为 通话(听筒或免提)
	private static final String DebugAction_Audio_Stream_Ring  = "com.rongchat.debug:audio.steam.ring";   //切换Audio Stream为 铃音
	//初始化
	private static final String DebugAction_Audio_init         = "com.rongchat.debug:audio.init";           //重新初始化
	private static final String DebugAction_Audio_Save         = "com.rongchat.debug:audio.save";           //音频.录制.开启


	//广播数组
	private static final String[] debugActions =
			{
					DebugAction_Log,
					DebugAction_Internet,
					DebugAction_Audio_Stream_Music,
					DebugAction_Audio_Stream_Cal,
					DebugAction_Audio_Stream_Ring,
					DebugAction_Audio_init,
					DebugAction_Audio_Save,
			};

	private static IDebugCallBack iDebugCallBack;

	public static void setDebugCallBack(IDebugCallBack debugCallBack)
	{
		iDebugCallBack = debugCallBack;
	}

	public static void registerDebugBroadcast(Context context)
	{
		registerDebugBroadcast(context, debugActions, (context1, intent) ->
		{
			if (EmptyUtil.notEmpty(intent))
			{
				String action = intent.getAction();
				if (EmptyUtil.notEmpty(action))
				{
					LogProxy.d(TAG, "DebugBroadcast-->收到广播 action=" + action);
					switch (action)
					{
						case DebugAction_Log:
							boolean log = intent.getBooleanExtra(DebugAction_Extra_Enable, true);//默认调用是开启
							LogProxy.d(TAG, "DebugBroadcast-->应用log输出: " + (log ? "打开" : "关闭"));
							logShow(log);
							LogProxy.d(TAG, "DebugBroadcast-->应用log输出: " + (log ? "打开" : "关闭"));
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onLogSwitch(log);
							break;
						case DebugAction_Internet:
							boolean internet = intent.getBooleanExtra(DebugAction_Extra_Enable, true);//默认调用是开启

							LogProxy.d(TAG, "DebugBroadcast-->网络情况log 输出到文件夹: " + (internet ? "打开" : "关闭"));
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onInternet(internet);
							break;
						case DebugAction_Audio_Stream_Music:
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onStreamSwitch(AudioManager.STREAM_MUSIC);
							LogProxy.d(TAG, "DebugBroadcast-->切换 Audio Stream 为: 多媒体");
							break;
						case DebugAction_Audio_Stream_Cal:
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onStreamSwitch(AudioManager.STREAM_VOICE_CALL);
							LogProxy.d(TAG, "DebugBroadcast-->切换 Audio Stream 为: 通话(听筒或免提)");
							break;
						case DebugAction_Audio_Stream_Ring:
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onStreamSwitch(AudioManager.STREAM_RING);
							LogProxy.d(TAG, "DebugBroadcast-->切换 Audio Stream 为: 铃音");
							break;
						case DebugAction_Audio_init:
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.init();
							LogProxy.d(TAG, "DebugBroadcast-->初始化");
							break;
						case DebugAction_Audio_Save:
							boolean wavSave = intent.getBooleanExtra(DebugAction_Extra_Enable, false);//默认: 关闭
							if (EmptyUtil.notEmpty(iDebugCallBack)) iDebugCallBack.onWavSave(wavSave);
							LogProxy.d(TAG, "DebugBroadcast-->音频.存储: " + (wavSave ? "打开" : "关闭"));
							break;
						default:
							LogProxy.d(TAG, "DebugBroadcast-->default, 暂时无处理");
							break;
					}
				} else
				{
					LogProxy.d(TAG, "DebugBroadcast-->action==null");
				}
			} else
			{
				LogProxy.d(TAG, "DebugBroadcast-->intent==null");
			}
		});
	}

	private static void logShow(boolean enable)
	{
		LogProxy.setLogVisibility(enable);
	}

	private static void registerDebugBroadcast(Context context, String[] actions, IBaseReceiverCallBack callBack)
	{
		BroadcastReceiverUtil.registerBroadcastByActionStrings(context, actions, DebugReceiver.getInstance());
		LogProxy.d(TAG, "registerDebugBroadcast-->注册");

		if (EmptyUtil.notEmpty(callBack))
		{
			DebugReceiver.getInstance().setReceiverCallBack(callBack);
			LogProxy.d(TAG, "registerDebugBroadcast-->设置回调");
		} else LogProxy.e(TAG, "registerDebugBroadcast-->设置回调错误：参数错误");

	}

	public static void unregisterDebugBroadcast(Context context)
	{
		BroadcastReceiverUtil.unregisterBroadcastReceiver(context, DebugReceiver.getInstance());
		DebugReceiver.getInstance().setReceiverCallBack(null);
		LogProxy.d(TAG, "unregisterDebugBroadcast-->注销");
	}

}
