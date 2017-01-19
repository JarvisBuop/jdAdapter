package com.jd.jarvisdemonim.ui.testfragment.decorator;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/11 0011
 * Name:
 * OverView:
 * Usage:
 */

public class SizeDayDecorator implements DayViewDecorator {
    private  CalendarDay today;

    public SizeDayDecorator() {
        today = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day!=null && day.equals(today);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD_ITALIC));
        view.addSpan(new RelativeSizeSpan(1.5f));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.today = CalendarDay.from(date);
    }
}
