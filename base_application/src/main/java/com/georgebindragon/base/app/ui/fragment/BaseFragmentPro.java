package com.georgebindragon.base.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 * 类名称：BaseFragmentPro
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseFragmentPro extends BaseFragment
{
	/*--------------------------------------------------------------------- 生命周期 ---------------------------------------------------------------------*/

	@Override
	public void onAttach(@NonNull Context context)
	{
		super.onAttach(context);
		LogProxy.d(TAG, "onAttach");
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LogProxy.d(TAG, "onCreate");
	}

	@Override
	protected View onCreateView()
	{
		LogProxy.d(TAG, "onCreateView");

		View contentView = LayoutInflater.from(getActivity()).inflate(getFragmentContentView(), null);

		initFragmentBase();
		initFragmentView(contentView);
		initFragmentData();

		return contentView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		LogProxy.d(TAG, "onActivityCreated");
	}

	@Override
	public void onStart()
	{
		super.onStart();
		LogProxy.d(TAG, "onStart");
	}

	@Override
	public void onResume()
	{
		super.onResume();
		LogProxy.d(TAG, "onResume");
	}

	@Override
	public void onPause()
	{
		super.onPause();
		LogProxy.d(TAG, "onPause");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		LogProxy.d(TAG, "onStop");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		LogProxy.d(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy()
	{
		RxCommonUtil.dispose(disposableList);
		super.onDestroy();
		LogProxy.d(TAG, "onDestroy");
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		LogProxy.d(TAG, "onDetach");
	}

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

	/*---------------------------------------------------------------------自定义生命周期---------------------------------------------------------------------*/

	@LayoutRes
	protected abstract int getFragmentContentView();

	//基础初始化
	protected void initFragmentBase()
	{
		LogProxy.i(TAG, "initFragmentBase-->");
	}

	//初始化窗口
	protected void initFragmentView(View contentView)
	{
		LogProxy.i(TAG, "initFragmentView-->");
	}

	//初始化数据
	protected void initFragmentData()
	{
		LogProxy.i(TAG, "initFragmentData-->");
	}

}
