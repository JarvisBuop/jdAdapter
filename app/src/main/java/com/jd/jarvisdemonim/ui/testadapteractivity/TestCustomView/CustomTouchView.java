package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/8 0008
 * Name:
 * OverView: viewgroup 中的View
 * Usage:
 */

public class CustomTouchView extends View {
    private String TAG = "jarvistouch "+getClass().getSimpleName();
    public CustomTouchView(Context context) {
        super(context);
    }

    public CustomTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 是否分发onTouchEvent事件;(3种情况)
     * true: 会运行,事件被View处理了或者叫消费了,不运行onTouchEvent()事件,不会回传,即后来所有的onTouchEvent都不会运行,到此为止;
     * false: 会运行,当前onTouchEvent()不运行;与true的区别是会调用父ViewGroup的onTouchEvent()方法;
     * super.dispatchTouchEvent(event): 会运行,当前onTouchEvent()运行,回传到onTouchEvent()返回值(基于onTouchEvent返回值);
     * 默认super.dispatchTouchEvent(event);
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.i(TAG,"dispatchTouchEvent "+TouchEventUtil.getTouchAction(event.getAction()));
        return super.dispatchTouchEvent(event);
//        return false;
    }

    /**
     * 都会运行,返回值表示是否继续运行Viewgroup的ontouchevent()方法;(两种情况)
     * true:表示处理此事件,并且不让包含它的ViewGroup继续处理事件,到此为止;
     * false: 表示处理此事件,但是继续回传给viewgroup,上层的onTouchEvent继续运行;
     * 默认是false == super.onTouchEvent(event);效果一样;
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG,"onTouchEvent "+TouchEventUtil.getTouchAction(event.getAction()));
//        return super.onTouchEvent(event);
        return false;
    }

    private String str = "内层View" + getClass().getSimpleName();
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
