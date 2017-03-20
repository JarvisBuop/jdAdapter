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
 * OverView:viewgroup中的viewgroup
 * Usage:
 */

public class CustomChildViewGroup extends LinearLayout {
    private String TAG = "jarvistouch "+getClass().getSimpleName();
    public CustomChildViewGroup(Context context) {
        super(context);
    }

    public CustomChildViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomChildViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * true: 运行此方法,事件拦截,不向下传递,但是会运行当前的onTouchEvent()方法(是否向上继续运行onTouchEvent取决于onTouchEvent的返回值)
     * false: 运行此方法,事件不拦截,继续分发;
     * 默认false;
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"onInterceptTouchEvent "+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    /**
     * 三种情况;
     * true: 会运行,被消费,事件停止分发,到此为止;
     * false: 会运行,但是本Viewgroup分发已经结束,不运行当前的onTouchEvent()方法,当前的流程已经结束,与true的区别在于此会马上调用父ViewGroup的onTouchEvent()方法;
     * super.dispatchTouchEvent(ev): 会运行,事件传递,马上运行onInterceptTouchEvent;
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG,"dispatchTouchEvent "+TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

    /**
     * true:会运行,不向上传递,直接消费,不会传到父ViewGroup的onTouchEvent()方法;
     * false or super.onTouchEvent(event):会运行,向上传递,依次运行onTouchEvent()方法;
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG,"onTouchEvent "+TouchEventUtil.getTouchAction(event.getAction()));
//        return super.onTouchEvent(event);
        return false;
    }

    private String str = "内层ViewGroup"+ getClass().getSimpleName();
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
