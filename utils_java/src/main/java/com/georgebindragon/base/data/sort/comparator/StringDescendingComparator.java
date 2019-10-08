package com.georgebindragon.base.data.sort.comparator;

import com.georgebindragon.base.data.sort.comparable.StringPropertyComparable;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * 创建人：George
 * 类名称：StringDescendingComparator
 *
 * 描述：按名称降序排序
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class StringDescendingComparator implements Comparator<StringPropertyComparable>
{
	@Override
	public int compare(StringPropertyComparable comparable1, StringPropertyComparable comparable2)
	{
		if (null == comparable1 || null == comparable2) return 0;

		//按名称降序排序（缺点是会把每一位拆开来排序，造成后果：1、10、2、3、31、32、4）
		return Collator.getInstance(Locale.getDefault()).compare(comparable1.getStringProperty(), comparable2.getStringProperty());
	}
}
