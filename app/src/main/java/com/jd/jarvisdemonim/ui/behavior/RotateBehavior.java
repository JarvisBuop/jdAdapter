package com.jd.jarvisdemonim.ui.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jd.jarvisdemonim.R;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/24 0024
 * Name:
 * OverView:右边的fab
 * Usage:
 */

public class RotateBehavior extends CoordinatorLayout.Behavior {
    public RotateBehavior() {
    }

    public RotateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, final View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//            AnimatorUtils.rotateShow(child);
        if (target.getId() == R.id.reccler2) {
            if (target instanceof RecyclerView) {
                RecyclerView dependency1 = (RecyclerView) target;
                if ((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0)) {
                    child.setRotation(-90 * getScollYDistance(dependency1));
                } else if ((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0)) {
                    child.setRotation(90 * getScollYDistance(dependency1));
                }
            }
        }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    //获取recycler的滑动高度,设置的margin会影响;
    public int getScollYDistance(RecyclerView mrecy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mrecy.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        if (firstVisiableChildView.getTop() < 0) {
            return (position) * itemHeight - firstVisiableChildView.getTop();
        } else {
            return (position) * itemHeight + firstVisiableChildView.getTop();
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.reccler2;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return false;
    }
}
