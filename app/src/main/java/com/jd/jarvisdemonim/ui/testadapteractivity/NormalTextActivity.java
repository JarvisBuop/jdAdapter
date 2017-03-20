package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

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
 * OverView:字体跳动;
 * Usage:
 */
public class NormalTextActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.btn_stop)
    Button btnStop;
    @Bind(R.id.btn_set)
    Button btnSet;
    @Bind(R.id.chronometer)
    Chronometer mChronometer;//

    @Bind(R.id.txt_rise)
    RiseNumberTextView txtRise;

    private long mRecord;

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
                LogUtils.i("jarvisdong", "123");
            }
        });
        mChronometer.setFormat("计时: %s");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnSet.setOnClickListener(this);
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                LogUtils.i(chronometer.getText().toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (mRecord != 0) {
                    mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecord));
                    mRecord = 0;
                } else {
                    mChronometer.setBase(SystemClock.elapsedRealtime());//注释是再次点击时,不会清零;
                }
                mChronometer.start();
                break;
            case R.id.btn_stop:
                mChronometer.stop();
                mRecord = SystemClock.elapsedRealtime();
                break;
            case R.id.btn_set:
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mRecord = 0 ;
                break;
        }
    }
}
