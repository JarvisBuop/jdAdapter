package com.jd.jarvisdemonim.ui.testfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.decorator.EventDecorator;
import com.jd.jarvisdemonim.ui.testfragment.decorator.PrimeDayDecorator;
import com.jd.jarvisdemonim.ui.testfragment.decorator.SizeDayDecorator;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;
import com.jd.jdkit.elementkit.utils.system.DateFormatUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/10 0010
 * Name:
 * OverView:  用于标记字体选中的效果;
 * Usage:
 */

public class OneFragment extends DBaseFragment {
    @Bind(R.id.btn_eori)
    Button btnei;
    @Bind(R.id.calendar_md)
    MaterialCalendarView calendarView;
    @Bind(R.id.text_one_test)
    TextView txtTest;
    private String str;
    private SizeDayDecorator sizeDayDecorator;

    private boolean isExpand = true;
    ViewWrapper wrapper;//日历控件包装，动画用
    private static String KEY = "key";
    private static String KEYINT = "layout";

    public static OneFragment newIntance(String extra, int layoutId) {
        OneFragment fragment = new OneFragment();
        fragment.setContainerId(R.id.framelayout);
        fragment.setCurrentTag("0");
        Bundle bun = new Bundle();
        bun.putInt(KEYINT, layoutId);
        bun.putString(KEY, extra);
        fragment.setArguments(bun);
        return fragment;
    }


    @Override
    protected View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getArguments().getInt(KEYINT), container, false);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        str = getArguments().getString(KEY);
        txtTest.setText(str);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        assert calendarView != null;
        doCalendarSet();
        wrapper = new ViewWrapper(calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                sizeDayDecorator.setDate(date.getDate());
                widget.invalidateDecorators();
                txtTest.setText(widget.getSelectedDate().toString() + "\n" + DateFormatUtils.Data2String(widget.getSelectedDate().getDate()));
            }
        });
        btnei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                    isExpand = !isExpand;
                } else{
                    calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                    isExpand = !isExpand;
                }
            }
        });

        doPost();
    }

    //日历设置;
    private void doCalendarSet() {
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);//设置多选;
        calendarView.setTopbarVisible(true);//设置日历头部可见;
        sizeDayDecorator = new SizeDayDecorator();
        /**
         * SeletorDayDecorator : 选中的日期的背景图片;
         * HighLightBgDecorator : 对设置的日期设置颜色;
         * sizeDayDecorator : 对选中的日期设置字体大小;
         * PrimeDayDecorator : 设置不可点击的日期
         * EnableOneToTenDecorator : 设置可点击的日期;
         * EventDecorator : 设置添加的小红点;
         */
        calendarView.addDecorators(
                /*new SeletorDayDecorator(mActivity),*
               /* new HighLightBgDecorator(mActivity, Color.parseColor("#EB6100")),*/
//                sizeDayDecorator,
                new PrimeDayDecorator(mActivity)
                /*new EnableOneToTenDecorator(),*/);
    }

    //网络获取标记天数;
    private void doPost() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);//指定从哪个月开始添加小红点;(现在是在当前月前两月;)
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);//每次循环日期添加5;
        }
        /**
         * 因为处理完小红点后,原先禁止点击的日期,会重新出现,所以装饰再次处理禁止事件;
         */
        calendarView.addDecorators(new EventDecorator(Color.RED, dates)
                , new PrimeDayDecorator(mActivity));//处理添加数据的小红点;
    }

    private static class EnableOneToTenDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getDay() <= 10;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(false);
        }
    }

    private class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getHeight() {
            return mTarget.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }
}
