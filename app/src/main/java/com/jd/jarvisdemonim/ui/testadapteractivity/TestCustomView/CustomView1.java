package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/1 0001
 * Name:
 * OverView: 自定义view-继承控件;
 * Usage:
 */

public class CustomView1 extends TextView {

    public CustomView1(Context context) {
        super(context);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setSingleLine(true);
        this.setClickable(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setMarqueeRepeatLimit(-1);
    }

}
