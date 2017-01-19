package com.jd.jarvisdemonim.ui.testfragment.decorator;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/11 0011
 * Name:
 * OverView:用于设置日历的标记背景色;
 * Usage:
 */

public class HighLightBgDecorator implements DayViewDecorator {
    private int color;
    private Context mContext;

    public HighLightBgDecorator(Context context, int color) {
        this.mContext = context;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Calendar calendar = Calendar.getInstance();
        day.copyTo(calendar);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        return i == Calendar.SATURDAY || i==Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(color));
    }
}
