package com.jd.jarvisdemonim.ui.testfragment.decorator;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/11 0011
 * Name:
 * OverView: 使用于装饰日历的不可选择日期;
 * Usage:
 */

public class PrimeDayDecorator implements DayViewDecorator {

    private Context mContext;

    public PrimeDayDecorator(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return PRIME_TABLE[day.getDay()];
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }

    private static boolean[] PRIME_TABLE = {
            false,  // 0?
            false,
            true, // 2
            true, // 3
            false,
            true, // 5
            false,
            true, // 7
            false,
            false,
            false,
            true, // 11
            false,
            true, // 13
            false,
            false,
            false,
            true, // 17
            false,
            true, // 19
            false,
            false,
            false,
            true, // 23
            false,
            false,
            false,
            false,
            false,
            true, // 29
            false,
            true, // 31
            false,
            false,
            false, //PADDING
    };
}
