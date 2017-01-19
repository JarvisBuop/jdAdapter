package com.jd.jarvisdemonim.ui.testfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;
import com.jd.jdkit.reminder.ReminderManager;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/6 0006
 * Name:
 * OverView:
 * Usage:
 */

public class TabCarFragment extends DBaseFragment implements CalendarView.OnDateChangeListener {
    private static String KEY = "key";
    private CalendarView carlendar;
    private TextView txtOne;

    public static TabCarFragment newInstance(String key) {
        TabCarFragment fragment = new TabCarFragment();
        Bundle bun = new Bundle();
        bun.putString(KEY, key);
        fragment.setArguments(bun);
        return fragment;
    }

    @Override
    protected View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mInflater.inflate(R.layout.fragment__text_carlendar, container, false);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        txtOne = (TextView) view.findViewById(R.id.text_one);
        carlendar = (CalendarView) view.findViewById(R.id.carlendar);

        txtOne.setText(String.valueOf(System.currentTimeMillis()));
        ((Button) view.findViewById(R.id.button_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderManager.getInstance().upDateUnReadNum(998, true, 1);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
        carlendar.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        txtOne.setText(String.valueOf(year + month - 1 + dayOfMonth));
    }
}
