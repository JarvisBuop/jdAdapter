package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.viewskit.RiseNumberTextView;

import butterknife.Bind;

;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/29 0029
 * Name:
 * OverView:
 * Usage:
 */
public class NormalTextActivity extends BaseActivity {
    @Bind(R.id.txt_rise)
    RiseNumberTextView txtRise;
    @Override
    public int getContentViewId() {
        return R.layout.test_txt;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/DS-DIGIT.TTF");
        txtRise.setTypeface(face);
        txtRise.setDuration(2000);
        txtRise.withNumber(200);
        txtRise.start();
    }

    @Override
    protected void initVariable() {
        txtRise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRise.withNumber(200);
                txtRise.start();
            }
        });
        txtRise.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {
                LogUtils.i("jarvisdong","123");
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
