package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.os.Bundle;
import android.view.MotionEvent;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/8 0008
 * Name:
 * OverView:测试View和ViewGroup的事件分发和拦截;
 * Usage:
 */

public class NormalTestCustomTouchActivity extends DBaseActivity {
    private String TAG = "jarvistouch "+getClass().getSimpleName();
    @Override
    public int getContentViewId() {
        return R.layout.activity_custom_touch;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"dispatchTouchEvent "+ TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG,"onTouchEvent "+ TouchEventUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);

    }
}
