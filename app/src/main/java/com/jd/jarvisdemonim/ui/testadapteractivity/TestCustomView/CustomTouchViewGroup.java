package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/8 0008
 * Name:
 * OverView: 最外层viewgroup;
 * Usage:
 */

public class CustomTouchViewGroup extends LinearLayout {
    private String TAG = "jarvistouch "+getClass().getSimpleName();


    public CustomTouchViewGroup(Context context) {
        super(context);
    }

    public CustomTouchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTouchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //ViewGroup里的方法;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"dispatchTouchEvent "+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
    //ViewGroup里的方法;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"onInterceptTouchEvent "+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
    }

    //View里的方法;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"onTouchEvent "+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

    private String str = "外层ViewGroup"+ getClass().getSimpleName();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mpaint = new Paint();
        mpaint.setStrokeWidth(8);
        mpaint.setColor(Color.BLACK);
        Rect rect = new Rect();
        mpaint.getTextBounds(str,0,str.length(),rect);
        canvas.drawText(str,getWidth()/2-rect.width()/2,getHeight()/2-rect.height()/2,mpaint);
    }
}
