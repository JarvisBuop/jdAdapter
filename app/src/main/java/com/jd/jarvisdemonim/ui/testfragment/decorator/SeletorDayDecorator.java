package com.jd.jarvisdemonim.ui.testfragment.decorator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jd.jarvisdemonim.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/11 0011
 * Name:
 * OverView: 日历装饰,修改的为选中天数的图片;
 * Usage:
 */

public class SeletorDayDecorator implements DayViewDecorator {
    Context mContext;
    Drawable mDrawable;

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    public SeletorDayDecorator(Context mContext) {
        this.mContext = mContext;
        mDrawable = mContext.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(mDrawable);
    }
}
