package com.georgebindragon.application.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.georgebindragon.application.sample.bean.GroupBean;
import com.georgebindragon.application.sample.media.AlertPlayer_Media;
import com.georgebindragon.application.sample.media.AlertPlayer_SoundPool;
import com.georgebindragon.application.sample.mvi.main.MainPresenter;
import com.georgebindragon.application.sample.mvi.main.MainView;
import com.georgebindragon.application.sample.mvi.main.MainViewState;
import com.georgebindragon.application.sample.ui.adapter.PopListAdapter;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.lfc.RxThrottle;
import com.georgebindragon.base.system.hardware.call.PhoneCallMonitor;
import com.georgebindragon.base.system.hardware.call.PhoneCallUtil;
import com.georgebindragon.base.system.software.DeviceUtil;
import com.georgebindragon.base.system.software.PermissionUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.TimeUtil;
import com.georgebindragon.base.widget.qmui.popup.QMUIRecyclerViewPopup;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;


public class MainActivity extends AppCompatActivity implements MainView, PhoneCallMonitor.PhoneCallStateListener, View.OnClickListener
{

	private static final String TAG = "MainActivity-->";

	@Override
	protected void attachBaseContext(Context newBase)
	{
		Locale defaultLocale = Locale.getDefault();
		LogProxy.v(TAG, "当前应用内语言: " + defaultLocale.toString());

		// Context context = TestUtil.wrapLanguageContext(newBase, Locale.ENGLISH);
		// super.attachBaseContext(context);

		super.attachBaseContext(newBase);
		Locale aDefault = Locale.getDefault();
		LogProxy.v(TAG, "当前应用内语言: " + aDefault.toString());
	}

	@Override
	public void render(MainViewState viewState)
	{
		mvi_btn.setText(viewState.getButtonText());
	}

	Button mvi_btn;
	Button testPop_btn;
	Button testPop_btn2;

	TextView textView;
	TextView textView2;
	TextView testInfo_device_manufacturer_tv;

	Button   button;

	TextView         testSound_tv;
	AppCompatSeekBar seekBar;

	String[] permission_read_phone_state = {Manifest.permission.READ_PHONE_STATE};//申请查看电话状态, 只能获取状态值, 不能获取号码
	String[] permission_read_call_log    = {Manifest.permission.READ_CALL_LOG};//申请查看电话状态, 只能获取状态值, 不能获取号码

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mvi_btn = findViewById(R.id.mvi_btn);
		mvi_btn.setOnClickListener(v -> onButtonClick());

		testPop_btn = findViewById(R.id.testPop_btn);
		testPop_btn.setOnClickListener(v -> testPop2());

		testPop_btn2 = findViewById(R.id.testPop_btn2);
		testPop_btn2.setOnClickListener(v -> onButtonClick_SoundPlayer());

		testSound_tv = findViewById(R.id.testSound_tv);
		seekBar = findViewById(R.id.testSound_sb);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				LogProxy.d(TAG, "OnSeekBarChangeListener-->onProgressChanged", "progress=" + progress, "fromUser=" + fromUser);
				testSound_tv.setText("" + progress + "%");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
				LogProxy.d(TAG, "OnSeekBarChangeListener-->onStartTrackingTouch");
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
				int progress = seekBar.getProgress();
				int max      = seekBar.getMax();
				LogProxy.d(TAG, "OnSeekBarChangeListener-->onStopTrackingTouch", "progress=" + progress);

				float volume = (float) progress / (float) max;
				AlertPlayer_Media.getInstance().setVolume(volume);
				AlertPlayer_SoundPool.getInstance().setVolume(volume);
			}
		});

		textView = findViewById(R.id.text);
		textView2 = findViewById(R.id.text2);
		testInfo_device_manufacturer_tv = findViewById(R.id.testInfo_device_manufacturer_tv);
		testInfo_device_manufacturer_tv.setText("制造商: "+ DeviceUtil.getManufacturer());
		LogProxy.d(TAG, "制造商="+ DeviceUtil.getManufacturer());

		button = findViewById(R.id.btn);
		button.setOnClickListener(this);

		MainPresenter.getInstance().init(this);
		MainPresenter.getInstance().initButton();

		registerReFreshSubject();

		if (!PermissionUtil.checkPermission(this, permission_read_phone_state))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			{
				requestPermissions(permission_read_phone_state, 1111);
			}
		}

		if (!PermissionUtil.checkPermission(this, permission_read_call_log))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			{
				requestPermissions(permission_read_call_log, 1111);
			}
		}

		PhoneCallMonitor.getInstance().registerMonitor(this);
		PhoneCallMonitor.getInstance().addListener(this);

		int callState = PhoneCallUtil.getCallState(this);
		reFreshCallState(callState, "初始化");

		initSoundPlayer();
	}


	private void initSoundPlayer()
	{
		AlertPlayer_Media.getInstance().init(this);
		AlertPlayer_SoundPool.getInstance().init(this);
	}

	int count = 0;

	private void onButtonClick_SoundPlayer()
	{
				AlertPlayer_Media.getInstance().playNext(count);
//		AlertPlayer_SoundPool.getInstance().playNext(count);
//		AlertPlayer_Media.getInstance().play_Error();
//		AlertPlayer_SoundPool.getInstance().play_Error();
		count++;
	}

	@Override
	protected void onDestroy()
	{
		unregisterReFreshSubject();
		super.onDestroy();
	}

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

	@SuppressLint("SetTextI18n")
	protected void reFreshUIAndData()
	{
		LogProxy.d(TAG, "触发 下拉刷新动画 + 数据更新");
		textView.setText("已经开机: " + (TimeUtil.getMilliSecondsSinceBoot() / 1000) + " 秒");
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

	RxThrottle<Integer> refreshThrottle;

	public void registerReFreshSubject()
	{
		refreshThrottle = new RxThrottle<>(false, reFreshFrequency, TimeUnit.SECONDS, AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(),
				times -> reFreshUIAndData());
		refreshThrottle.startListen();
	}

	private int reFreshFrequency = 5;

	public void setReFreshFrequency(int reFreshFrequency)
	{
		this.reFreshFrequency = reFreshFrequency;
	}

	//menu 按钮 触发
	protected void reFreshManuallyByFrequency()
	{
		LogProxy.d(TAG, "按钮被点击 , 预设频率=" + reFreshFrequency + "秒, 需要达到预设频率, 才能回调");
		refreshThrottle.setNext(reFreshFrequency);
	}

	public void unregisterReFreshSubject()
	{
		if (EmptyUtil.notEmpty(refreshThrottle)) refreshThrottle.stopListen();
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

	@Override
	public Observable<String> onButtonClick()
	{
		return Observable.timer(5, TimeUnit.SECONDS).flatMap((Function<Long, ObservableSource<String>>) aLong -> Observable.just("test"));
	}

	private QMUIListPopup mListPopup;

	private void initPop()
	{
		if (mListPopup == null)
		{
			String[]     listItems = new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5",};
			List<String> data      = new ArrayList<>();
			Collections.addAll(data, listItems);
			ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, data);
			mListPopup = new QMUIListPopup(this, QMUIPopup.DIRECTION_NONE, adapter);
			mListPopup.create(QMUIDisplayHelper.dp2px(this, 250), QMUIDisplayHelper.dp2px(this, 200), (adapterView, view, i, l) ->
			{
				Toast.makeText(MainActivity.this, "Item " + (i + 1), Toast.LENGTH_SHORT).show();
				mListPopup.dismiss();
			});
			mListPopup.setOnDismissListener(() -> Toast.makeText(MainActivity.this, "onDismiss ", Toast.LENGTH_SHORT).show());
		}
	}

	private void testPop()
	{
		mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
		mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
		mListPopup.show(mvi_btn);
	}


	private QMUIRecyclerViewPopup mRecyclerViewPopup;

	private void initPop2()
	{
		if (mRecyclerViewPopup == null)
		{
			List<GroupBean> data = new ArrayList<>();

			data.add(new GroupBean(1, true, "第一名"));
			data.add(new GroupBean(2, true, "呵呵"));
			data.add(new GroupBean(3, false, "你咋这样呢"));
			data.add(new GroupBean(4, true, "我的名字就这么古怪"));

			PopListAdapter adapter = new PopListAdapter(R.layout.simple_list_item, data);

			mRecyclerViewPopup = new QMUIRecyclerViewPopup(this, QMUIPopup.DIRECTION_BOTTOM, adapter);
			//计算pop window 的宽和最大高度
			int PopWidth  = RecyclerView.LayoutParams.MATCH_PARENT;
			int PopHeight = RecyclerView.LayoutParams.WRAP_CONTENT;

			mRecyclerViewPopup.create(PopWidth, PopHeight);
			adapter.setOnItemClickListener((adapter1, view, position) ->
			{
				Toast.makeText(MainActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
				Object item = adapter1.getItem(position);
				if (item instanceof GroupBean)
				{
					GroupBean bean = (GroupBean) item;
					LogProxy.d(TAG, "bean= " + bean.getName());
				}
			});

			mRecyclerViewPopup.setOnDismissListener(() -> Toast.makeText(MainActivity.this, "onDismiss ", Toast.LENGTH_SHORT).show());
		}
	}

	private void testPop2()
	{
		initPop2();
		mRecyclerViewPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
		mRecyclerViewPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
		mRecyclerViewPopup.show(mvi_btn);
	}

}
