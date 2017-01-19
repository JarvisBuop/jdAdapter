package com.jd.jdkit.elementkit.utils.system;

import com.jd.jdkit.entity.BaseSortIndexBean;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparatorUtils implements Comparator<BaseSortIndexBean> {

	public int compare(BaseSortIndexBean o1, BaseSortIndexBean o2) {
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
