package com.jd.jarvisdemonim.utils;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparatorUtils implements Comparator<SortModelData> {

	public int compare(SortModelData o1, SortModelData o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
