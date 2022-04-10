package com.georgebindragon.base.system.ui;

import android.widget.EditText;

import com.georgebindragon.base.utils.EmptyUtil;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class EditTextUtil
{
	private static final String TAG = "EditTextUtil-->";

	public static void setTextAndSelection(EditText editText, String info)
	{
		if (null != editText)
		{
			editText.setText(info);
			editText.setSelection(EmptyUtil.isEmpty(info) ? 0 : info.length());
		}
	}

}