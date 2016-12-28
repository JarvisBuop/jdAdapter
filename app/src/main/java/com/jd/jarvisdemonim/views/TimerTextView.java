package com.jd.jarvisdemonim.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义短信验证码倒计时组件
 */
public class TimerTextView extends TextView implements Runnable {

    private boolean isStart = false;// 是否启动
    private static int time = 60;

    public TimerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context) {
        super(context);
    }

    public void start() {
        if (!isStart) {
            // 开始
            this.post(this);
            this.setText(time + "s");
        }
    }

    @Override
    public void run() {
        if (time == 0) {
            setText("重新获取");
            time = 60;
            isStart = false;
            this.setEnabled(true);
        } else {
            isStart = true;
            this.postDelayed(this, 1000);
            setText(time-- + "s");
            this.setEnabled(false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this);
        time = 60;
    }

}
