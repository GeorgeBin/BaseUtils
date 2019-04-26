package com.georgebindragon.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.lfc.RxThrottle;
import com.georgebindragon.base.system.hardware.call.PhoneCallMonitor;
import com.georgebindragon.base.system.hardware.call.PhoneCallUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PhoneCallMonitor.PhoneCallStateListener
{
	public String TAG = "Activity: " + getClass().getSimpleName() + "-->";

	String[] p  = {Manifest.permission.READ_PHONE_STATE};
	String[] p2 = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		registerReFreshSubject();

		textView = findViewById(R.id.text);
		textView2 = findViewById(R.id.text2);

		button = findViewById(R.id.btn);
		button.setOnClickListener(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			requestPermissions(p, 11111);//申请查看电话状态, 只能获取状态值, 不能获取号码
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			requestPermissions(p2, 11111);//申请查看电话状态, 只能获取状态值, 不能获取号码
		}
		PhoneCallMonitor.getInstance().registerMonitor(this);
		PhoneCallMonitor.getInstance().addListener(this);

		int callState = PhoneCallUtil.getCallState(this);
		reFreshCallState(callState, "初始化");

	}

	TextView textView;
	TextView textView2;
	Button   button;

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			case R.id.btn:
				reFreshManuallyByFrequency();
				break;
			default:
				break;
		}
	}

	/**
	 * Returns one of the following constants that represents the current state of all
	 * phone calls.
	 *
	 * {@link TelephonyManager#CALL_STATE_RINGING}
	 * {@link TelephonyManager#CALL_STATE_OFFHOOK}
	 * {@link TelephonyManager#CALL_STATE_IDLE}
	 */
	@Override
	public void onPhoneState(int callState, String incomingNumber)
	{
		reFreshCallState(callState, incomingNumber);
	}

	private int reFreshFrequency = 5;

	public void setReFreshFrequency(int reFreshFrequency)
	{
		this.reFreshFrequency = reFreshFrequency;
	}

	@SuppressLint("SetTextI18n")
	protected void reFreshUIAndData()
	{
		LogProxy.d(TAG, "触发 下拉刷新动画 + 数据更新");
		textView.setText("已经开机: " + (TimeUtil.getMilliSecondsSinceBoot() / 1000) + " 秒");
	}

	public void reFreshCallState(int callState, String incomingNumber)
	{
		String state;
		switch (callState)
		{
			case TelephonyManager.CALL_STATE_RINGING:
				state = "响铃";
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				state = "摘机";
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				state = "空闲";
				break;
			default:
				state = "其他";
				break;
		}
		textView2.setText("呼叫状态: callState=" + callState + "\tstate=" + state + "\tincomingNumber=" + incomingNumber);
	}

	RxThrottle<Integer> refreshThrottle;

	public void registerReFreshSubject()
	{
		refreshThrottle = new RxThrottle<>(false, reFreshFrequency, TimeUnit.SECONDS, AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(),
				times -> reFreshUIAndData());
		refreshThrottle.startListen();
	}

	public void unregisterReFreshSubject()
	{
		if (EmptyUtil.notEmpty(refreshThrottle)) refreshThrottle.stopListen();
	}

	//menu 按钮 触发
	protected void reFreshManuallyByFrequency()
	{
		LogProxy.d(TAG, "按钮被点击 , 预设频率=" + reFreshFrequency + "秒, 需要达到预设频率, 才能回调");
		refreshThrottle.setNext(reFreshFrequency);
	}

	@Override
	protected void onDestroy()
	{
		unregisterReFreshSubject();
		super.onDestroy();
	}
}
