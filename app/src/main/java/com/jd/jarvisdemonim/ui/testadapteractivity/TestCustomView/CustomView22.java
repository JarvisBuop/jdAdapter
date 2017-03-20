package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/1 0001
 * Name:
 * OverView:自定义view-绘制控件,继承自Viewgroup;
 * Usage: 自定义ViewGroup需要注意它的子View;
 */

public class CustomView22 extends ViewGroup {
    private String TAG = "CustomView22";
    public CustomView22(Context context) {
        this(context, null);
    }

    public CustomView22(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView22(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    /**
     * 需要测量每个子VIew的大小,然后去容纳他们,得出Viewgroup的大小;
     * 具体的思想是:宽高需测量,atmost时,使用子View最大宽度和子VIew总高度;
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.i(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        MarginLayoutParams params = null;
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //主要测量wrap_content
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (modeWidth == MeasureSpec.AT_MOST && modeHeight == MeasureSpec.AT_MOST) {
                int width = getMaxWidth();
                int height = getHeightAdd();
                setMeasuredDimension(width + getMarginHorizental(), height + getMarginVertical());
            } else if (modeWidth == MeasureSpec.AT_MOST) {
                setMeasuredDimension(getMaxWidth() + getMarginHorizental(), sizeHeight);
            } else if (modeHeight == MeasureSpec.AT_MOST) {
                setMeasuredDimension(sizeWidth, getHeightAdd() + getMarginVertical());
            }
        }

    }

    //获取子View的所有高度相加;
    public int getHeightAdd() {
        int childCount = getChildCount();
        int resultHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
//            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
//            int top = layoutParams.topMargin;
//            int bottom = layoutParams.bottomMargin;
//            resultHeight += (view.getHeight()+top+bottom);
            resultHeight +=view.getHeight();
        }
        return resultHeight;
    }

    //获取子View的最大宽度
    public int getMaxWidth() {
        int childCount = getChildCount();
        int resultWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            resultWidth = view.getWidth() > resultWidth ? view.getWidth() : resultWidth;
        }
        return resultWidth;
    }

    //获取所有的margin
    public int getMarginHorizental() {
        int result = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            result += layoutParams.leftMargin;//此处设置一个即可,参考LinearLayout的onMeasure()方法;
        }
        return result;
    }

    public int getMarginVertical() {
        int result = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            result += (layoutParams.topMargin /*+ layoutParams.bottomMargin*/);//只需要加一个就可以了;
        }
        return result;
    }

    /**
     * 重写Viewgroup的方法,避免造型异常;
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * 自定义viewgroup必须重写onlayout;规定每个自View的位置;
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.i(TAG, "onLayout" + l + "/" + t + "/" + r + "/" + b);
        int curHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int c1 = params.leftMargin;
            int c2 = params.topMargin;
            int c3 = c1 + child.getMeasuredWidth();
            int c4 = c2 + child.getMeasuredHeight() + params.bottomMargin;
            child.layout(c1, c2 + curHeight, c3, c4 + curHeight);//viewgroup的根View;
            curHeight += (c4);//下一个子View的摆放位置;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.i(TAG, "onDraw");
        super.onDraw(canvas);

    }


    /**
     * viewgroup需要重绘各个子View;
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        LogUtils.i(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG, "onTouchEvent");
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);

    }


}
