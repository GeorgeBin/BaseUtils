package com.georgebindragon.base.data.sort.comparator;

import com.georgebindragon.base.data.sort.comparable.IntegerPropertyComparable;

import java.util.Comparator;

/**
 * 创建人：George
 * 类名称：IntegerDescendingComparator
 *
 * 描述：按数字降序排序
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class IntegerDescendingComparator implements Comparator<IntegerPropertyComparable>
{
	@Override
	public int compare(IntegerPropertyComparable comparable1, IntegerPropertyComparable comparable2)
	{
		// 降序：9/8/7/6/5/null

		if (comparable1 != null && comparable2 != null)
		{
			return comparable1.getIntegerProperty() - comparable2.getIntegerProperty();
		} else
		{
			return comparable1 == null ? -100 : -101;
		}
	}
}
