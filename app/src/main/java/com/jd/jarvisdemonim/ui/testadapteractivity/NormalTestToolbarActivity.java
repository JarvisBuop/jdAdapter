package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseToolBarActivity;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/17 0017
 * Name:
 * OverView: 使用tollbar,注意屏幕适配,关于状态栏;
 * Usage:
 */

public class NormalTestToolbarActivity extends DBaseToolBarActivity {
    @Bind(R.id.txt_toolbar)
    TextView txtTest;
    @Override
    public int getContentViewId() {
        return R.layout.activity_test_toolball;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("这是toolbar");
        setBackBtn();
        txtTest.setText("wo le ge qu");
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
