package com.jd.jarvisdemonim.ui.behavior;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/23 0023
 * Name:
 * OverView: 中间的fab;
 * Usage:
 */

public class FirstBehavior extends CoordinatorLayout.Behavior {
    public FirstBehavior() {
    }

    public FirstBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当behavior依附于LayoutParams的实例时调用
     *
     * @param params
     */
    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        super.onAttachedToLayoutParams(params);
    }

    /**
     * 当behavior已经被持有的LayoutParams清除时调用
     */
    @Override
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
    }

    /**
     * Respond to CoordinatorLayout touch events before they are dispatched to child views.
     * 分发到子view之前col的调用;
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    /**
     * Respond to CoordinatorLayout touch events after this Behavior has started intercepting them.
     * 在behavior开始拦截事件时调用;
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }


    /**
     * Determine whether interaction with views behind the given child in the child order should be blocked.
     * 决定在给定的view之后的view是否被阻塞;
     *
     * @param parent
     * @param child
     * @return
     */
    @Override
    public boolean blocksInteractionBelow(CoordinatorLayout parent, View child) {
        return super.blocksInteractionBelow(parent, child);
    }

    /**
     * [1]
     * Determine whether the supplied child view has another specific sibling view as a layout dependency.
     * 决定提供的子view是否有另一个作为布局依赖特殊的兄弟视图
     * 确定依赖关系,依赖变化,子view 变化;
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.reccler2;
    }

    /**
     * [2]
     * Respond to a change in a child's dependent view
     * 子view依赖view
     * 依赖变化,马上子view也会变化;
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    //其实列表的滑动设置 onNestedScroll 更好,因为recyclerview的滑动距离不好计算,而此方法中有滑动距离;
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency.getId() == R.id.reccler2) {
            child.setY(dependency.getY());
            if (dependency instanceof RecyclerView) {
                RecyclerView dependency1 = (RecyclerView) dependency;
                child.setY(getScollYDistance(dependency1) + dependency.getY());
                LogUtils.i("fab的距离:" + getScollYDistance(dependency1) + "/" + dependency.getY() + "\n");
            }
        }
        return true;
//        return false;
    }

    //获取recycler的滑动高度,设置的margin会影响;
    public int getScollYDistance(RecyclerView mrecy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mrecy.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int[] margin = getMargin(firstVisiableChildView);
        int itemHeight = firstVisiableChildView.getHeight();
        LogUtils.i("itemView的高度:" + itemHeight + "/" + firstVisiableChildView.getTop() + "position" + position);//就是margin;
        if (firstVisiableChildView.getTop() < 0) {
            return (position) * itemHeight - firstVisiableChildView.getTop();
        } else {
            return (position) * itemHeight + firstVisiableChildView.getTop();
        }
    }

    public int[] getMargin(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int[] arr = new int[4];
        arr[0] = layoutParams.leftMargin;
        arr[1] = layoutParams.topMargin;
        arr[2] = layoutParams.rightMargin;
        arr[3] = layoutParams.bottomMargin;
        return arr;
    }

    /**
     * Respond to a child's dependent view being removed.
     * 子view依赖的view被清除;
     *
     * @param parent
     * @param child
     * @param dependency
     */
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    /**
     * Called when the parent CoordinatorLayout is about to measure the given child view.
     * col即将测量给的子view
     * ;
     *
     * @param parent
     * @param child
     * @param parentWidthMeasureSpec
     * @param widthUsed
     * @param parentHeightMeasureSpec
     * @param heightUsed
     * @return
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    /**
     * Called when the parent CoordinatorLayout is about the lay out the given child view.
     * 布局位置;
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * [11]
     * Called when a descendant of the CoordinatorLayout attempts to initiate a nested scroll.
     * col的子view视图去初始化一个滑动的view调用;
     * <p>
     * 设置只有竖直滑动才可;
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return 返回为true, 才会接受后续滑动事件;
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * Called when a nested scroll has been accepted by the CoordinatorLayout.
     * col已经接受可滑动的view;
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    //活动结束[33]
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    /**
     * [22]
     * ///Called when a nested scroll in progress has updated and the target has scrolled or attempted to scroll.
     * 可滑动的view已更新,目标view已经滑动或试图滑动调用;
     * 滑动事件的处理;
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        offset(child,dyConsumed);

    }
//    int offsetTotal = 0;boolean scrolling = false;
//    public void offset(View child,int dy){
//        int old = offsetTotal;
//        int top = offsetTotal - dy;
//        top = Math.max(top, -child.getHeight());
//        top = Math.min(top, 0);
//        offsetTotal = top;
//        if (old == offsetTotal){
//            scrolling = false;
//            return;
//        }
//        int delta = offsetTotal-old;
//        child.offsetTopAndBottom(delta);
//        scrolling = true;
//    }


    /**
     * Called when a nested scroll in progress is about to update, before the target has consumed any of the scrolled distance.
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    //手指释放,活动操作;快速滑动;
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//        child.setY(-velocityY + child.getY());
//        return true;
    }

    //开始fling
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    //Called when the window insets have changed. 当窗口嵌入改变;
    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, View child, WindowInsetsCompat insets) {
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }

    /**
     * Called when a child of the view associated with this behavior wants a particular rectangle to be positioned onto the screen.
     * 和behavior有关的子view想要定位在屏幕上以一个特定的举行;
     *
     * @param coordinatorLayout
     * @param child
     * @param rectangle
     * @param immediate
     * @return
     */
    @Override
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View child, Rect rectangle, boolean immediate) {
        return super.onRequestChildRectangleOnScreen(coordinatorLayout, child, rectangle, immediate);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, View child, Parcelable state) {
        super.onRestoreInstanceState(parent, child, state);
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, View child) {
        return super.onSaveInstanceState(parent, child);
    }

    /**
     * Called when a view is set to dodge view insets.
     *
     * @param parent
     * @param child
     * @param rect
     * @return
     */
    @Override
    public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull Rect rect) {
        return super.getInsetDodgeRect(parent, child, rect);
    }
}
