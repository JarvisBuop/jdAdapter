package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jdkit.elementkit.utils.system.DateFormatUtils;
import com.jd.jdkit.elementkit.utils.system.ScreenUtils;
import com.jd.myadapterlib.common.RecyCarlendarAdapter;

import java.util.Date;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/16 0016
 * Name:
 * OverView:自定义适配器实现日历;
 * Usage: 使用view的包装类;
 */

public class NormalTestCalendarAdapterActivity extends BaseActivity {
    @Bind(R.id.recyclerview_date)
    RecyclerView mRecyData;
    @Bind(R.id.tv_date_selected)
    TextView mDisplay;

    ViewWrapper wrapper;
    GridLayoutManager glm;
    RecyCarlendarAdapter mCarAdapter;
    private boolean isAll = true;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRecyData.scrollBy(0, msg.arg1);
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_date_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        wrapper = new ViewWrapper(mRecyData);
        mCarAdapter = new RecyCarlendarAdapter(mRecyData, R.layout.calendar_item);
        glm = new GridLayoutManager(this, 7);
    }

    @Override
    protected void initVariable() {
        mRecyData.setHasFixedSize(true);
        mRecyData.setLayoutManager(glm);
        mRecyData.setAdapter(mCarAdapter);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDisplay.setText(DateFormatUtils.long2StringByYYYYMD(new Date().getTime()));
        mCarAdapter.chooseTime(new Date().getTime(), 1);
        mCarAdapter.setOnCalendarItemClickListener(new RecyCarlendarAdapter.OnCalendarItemClickListener() {
            @Override
            public void calendarItem(int pos, long time, RecyCarlendarAdapter.DateState state, View view) {
                if (isAll) {
                    doZoomDown(pos ,time ,view);
                } else {
                    doZoomUp(pos ,time ,view);
                }
                isAll = !isAll;
            }
        });
    }

    private void doZoomUp(final int pos, long time, View view) {
        mRecyData.smoothScrollToPosition(pos);
        ObjectAnimator anim = ObjectAnimator.ofInt(wrapper, "Height", ScreenUtils.dpToPx(50), ScreenUtils.dpToPx(250));
        anim.setDuration(300);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRecyData.smoothScrollToPosition(pos);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    private void doZoomDown(final int pos, long time, View view) {
        if (mCarAdapter.chooseTime(time, 1)) {
            mDisplay.setText(DateFormatUtils.long2StringByYYYYMD(time));
        }
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        view.getLocationOnScreen(location1);
        final int y = location1[1];//点击itemview的y方向位置;
        mRecyData.getLocationOnScreen(location2);
        final int yy = location2[1];//recyclerview的y方向位置;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    Message msg = new Message();
                    msg.arg1 = (y - yy) / 50;
//                        recyclerviewDate.smoothScrollBy(0,(y-yy)/10);
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        ObjectAnimator anim = ObjectAnimator.ofInt(wrapper, "Height", ScreenUtils.dpToPx(250), ScreenUtils.dpToPx(50));
        anim.setDuration(1000);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRecyData.smoothScrollToPosition(pos);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    /**
     * view的包装类;
     */
    private class ViewWrapper {
        private View mTargetView;

        public ViewWrapper(View mTargetView) {
            this.mTargetView = mTargetView;
        }

        public int getHeight() {
            return mTargetView.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTargetView.getLayoutParams().height = height;
            mTargetView.requestLayout();
        }
    }
}
