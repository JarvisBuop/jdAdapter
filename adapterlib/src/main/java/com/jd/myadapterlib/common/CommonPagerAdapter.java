package com.jd.myadapterlib.common;

import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jd.myadapterlib.R;
import com.jd.myadapterlib.dinterface.DOnItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView: page 的切换,pagerAdapter
 * Usage:
 */
public class CommonPagerAdapter extends PagerAdapter {
    public CommonPagerAdapter() {
    }

    private int mAutoPlayInterval = 2000;
    private boolean mAutoPlayAble = false;
    private List<View> mViews;
    private List<View> mHackyViews;
    private List<? extends Object> mObject;
    PlayRunnable mPlayRunnable;


    public void setmAutoPlayInterval(int mAutoPlayInterval) {
        this.mAutoPlayInterval = mAutoPlayInterval;
    }

    public void setmAutoPlayAble(boolean mAutoPlayAble) {
        this.mAutoPlayAble = mAutoPlayAble;
    }

    public void startAutoPlay(ViewPager mViewpager) {
        stopAutoPlay(mViewpager);
        if (mAutoPlayAble && mViewpager != null) {
            mViewpager.postDelayed(mPlayRunnable, mAutoPlayInterval);
        }
    }

    public void stopAutoPlay(ViewPager mViewpager) {
        if (mAutoPlayAble && mViewpager != null) {
            mViewpager.removeCallbacks(mPlayRunnable);
        }
    }

    private void switchToNextPage(ViewPager mViewPager) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    /**
     * 设置每一页的控件、数据模型
     *
     * @param mViews
     * @param mObject
     * @param mItemListener
     */
    public void setmViews(ViewPager mViewpager, List<View> mViews, List<? extends Object> mObject, OnItemFillListener mItemListener) {
        if (mAutoPlayAble && mViews != null && mViews.size() < 3 && mHackyViews == null) {
            mAutoPlayAble = false;
        }
        this.mViews = mViews;
        this.mObject = mObject;
        this.mItemListener = mItemListener;
        if (mViews != null) {
            if (mAutoPlayAble) {
                int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size();
                mViewpager.setCurrentItem(zeroItem);
            } else if (mViewpager != null) {
                mViewpager.setCurrentItem(0);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Attention :must be canAutoPlay;
     */
    public void setScroll(ViewPager mViewpager, boolean isScroll) {
        if (mPlayRunnable == null)
            mPlayRunnable = new PlayRunnable(mViewpager);
        if (mAutoPlayAble) {
            if (isScroll) {
                startAutoPlay(mViewpager);
            } else {
                stopAutoPlay(mViewpager);
            }
        }
    }

    /**
     * 设置布局资源id、数据模型
     *
     * @param mViewpager
     * @param layoutResId
     * @param models
     */
    public void setmViews(ViewPager mViewpager, @LayoutRes int layoutResId, List<? extends Object> models, OnItemFillListener mItemListener) {
        mViews = new ArrayList<>();
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                mViews.add(View.inflate(mViewpager.getContext(), layoutResId, null));
            }
        }
        if (mAutoPlayAble && mViews.size() < 3) {
            mHackyViews = new ArrayList<>(mViews);
            mHackyViews.add(View.inflate(mViewpager.getContext(), layoutResId, null));
            if (mHackyViews.size() == 2) {
                mHackyViews.add(View.inflate(mViewpager.getContext(), layoutResId, null));
            }
        }
        setmViews(mViewpager, mViews, models, mItemListener);
    }

    /**
     * 设置数据模型和文案，布局资源默认为ImageView
     *
     * @param models 每一页的数据模型集合
     */
    public void setmViews(ViewPager mViewpager, List<? extends Object> models, OnItemFillListener mItemListener) {
        setmViews(mViewpager, R.layout.common_banner_item, models, mItemListener);
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : (mAutoPlayAble ? Integer.MAX_VALUE : mViews.size());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int finalPosition = position % mViews.size();
        View view = (View) mViews.get(finalPosition);

        if (container.equals(view.getParent())) {
            container.removeView(view);
        }
        if (mListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, finalPosition, mObject == null ? null : mObject.get(finalPosition));
                }
            });
        }
        if (mItemListener != null) {
            mItemListener.fillBannerItem(view, mObject == null ? null : mObject.get(finalPosition), finalPosition);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (!mAutoPlayAble) {
            container.removeView((View) mViews.get(position));
        } else {
            int finalPosition = position % mViews.size();
            container.removeView((View) mViews.get(finalPosition));
        }
    }

    private DOnItemClickListener mListener;

    public void setDOnItemClickListener(DOnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public static <T extends View> T generateView(int id, View view) {
        return (T) view.findViewById(id);
    }

    private OnItemFillListener mItemListener;

    public interface OnItemFillListener {
        void fillBannerItem(View view, Object model, int position);
    }

    public class PlayRunnable implements Runnable {
        private final WeakReference<View> weakReference;

        public PlayRunnable(ViewPager viewPager) {
            weakReference = new WeakReference<View>(viewPager);
        }

        @Override
        public void run() {
            ViewPager mViewPager = (ViewPager) weakReference.get();
            if (mViewPager != null) {
                switchToNextPage(mViewPager);
                startAutoPlay(mViewPager);//需要handler去滑动;
            }
        }
    }
}
