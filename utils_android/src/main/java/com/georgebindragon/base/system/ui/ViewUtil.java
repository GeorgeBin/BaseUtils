package com.georgebindragon.base.system.ui;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 * 类名称：ViewUtil
 * 类概述：
 * 详细描述：
 *
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ViewUtil
{
	private static final String TAG = "ViewUtil-->";

	public static View getView(Context context, int res, ViewGroup rootView)
	{
		if (null == context) context = BaseUtils.getContext();

		LayoutInflater from = LayoutInflater.from(context);
		return from.inflate(res, rootView);
	}


	public static void setTextAndSelection(EditText editText, String info)
	{
		if (null != editText)
		{
			editText.setText(info);
			editText.setSelection(EmptyUtil.isEmpty(info) ? 0 : info.length());
		}
	}

	//设置密码可见和不可见
	public static void setPasswordVisibility(EditText editText, boolean setVisible)
	{
		if (null != editText)
		{
			int selectionEnd = editText.getSelectionEnd();
			if (setVisible)
			{
				// 设置为可见
				editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			} else
			{
				// 设置为不可见
				editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
				editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}

			int length = editText.getText().toString().length();

			editText.setSelection((Math.min(selectionEnd, length)));//执行上面的代码后光标会处于输入框的最前方-->重置光标位置
		}
	}


	public static boolean isInView(View view, float x, float y)
	{
		if (null == view) return false;

		int[] point = new int[2];
		view.getLocationOnScreen(point);

		return x >= point[0] && x <= (point[0] + view.getWidth()) && y >= point[1] && y <= (point[1] + view.getHeight());
	}

}