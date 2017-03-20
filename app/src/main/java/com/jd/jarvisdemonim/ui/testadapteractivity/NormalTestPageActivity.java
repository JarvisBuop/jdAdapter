package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.entity.TestBean;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.myadapterlib.common.CommonPagerAdapter;
import com.jd.myadapterlib.dinterface.DOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:头3,pageradapter;适配器测试;
 * Usage:
 */
public class NormalTestPageActivity extends BaseActivity {
    @Bind(R.id.pager)
    ViewPager mViewpager;
    CommonPagerAdapter mPagerAdapter;
    List<Object> beans;
    List<View> views;

    @Override
    public int getContentViewId() {
        return R.layout.test_activity_viewpager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPagerAdapter = new CommonPagerAdapter();
        beans = new ArrayList<>();
        views = new ArrayList<>();
    }

    @Override
    protected void initVariable() {
        mViewpager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPagerAdapter.setDOnItemClickListener(new DOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object o) {
                LogUtils.i("jarvisclick", position + "");
            }
        });
        mPagerAdapter.setmAutoPlayAble(true);
        mPagerAdapter.setScroll(mViewpager,true);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        mPagerAdapter.setScroll(mViewpager,true);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mPagerAdapter.setScroll(mViewpager,false);
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mPagerAdapter.setScroll(mViewpager,false);
                        break;
                }
            }
        });
        doPost();
    }

    private void doPost() {
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content1"));
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content2"));
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content3"));
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content4"));
        beans.add(new TestBean(R.drawable.ic_gf_done, "设置为commonAdapter", "content5"));
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content6"));
        beans.add(new TestBean(R.drawable.ic_gf_done, "设置为commonAdapter", "content7"));
        beans.add(new TestBean(R.drawable.gf_ic_preview, "设置为commonAdapter", "content8"));
        beans.add(new TestBean(R.drawable.ic_gf_done, "设置为commonAdapter", "content9"));
        for (int i = 0; i < beans.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.test_banner_item, null);
            views.add(view);
        }
        mPagerAdapter.setmViews(mViewpager, views, beans, new CommonPagerAdapter.OnItemFillListener() {
            @Override
            public void fillBannerItem(View view, Object model, int position) {
                if (model instanceof TestBean) {
                    TestBean bean = (TestBean) model;
                    ImageView view1 = CommonPagerAdapter.generateView(R.id.img_banner, view);
                    TextView view2 = CommonPagerAdapter.generateView(R.id.txt_banner, view);
                    view1.setImageResource(R.drawable.gf_ic_preview);
                    view2.setText(bean.getContent());
                }
            }
        });
//        mPagerAdapter.setmViews(mViewpager,views, new CommonPagerAdapter.OnItemFillListener() {
//            @Override
//            public void fillBannerItem(View view, Object model, int position) {
//                if (model instanceof TestBean) {
//                    TestBean bean = (TestBean) model;
//                    ImageView view1 = CommonPagerAdapter.generateView(R.id.img_banner, view);
//                    TextView view2 = CommonPagerAdapter.generateView(R.id.txt_banner, view);
//                    view1.setImageResource(bean.getRes());
//                    view2.setText(bean.getContent());
//                }
//            }
//        });
//        mPagerAdapter.setmViews(mViewpager, R.layout.test_zidingyi, beans, new CommonPagerAdapter.OnItemFillListener() {
//            @Override
//            public void fillBannerItem(View view, Object model, int position) {
//                if (model instanceof TestBean) {
//                    TestBean bean = (TestBean) model;
//                    ImageView view1 = CommonPagerAdapter.generateView(R.id.img_test, view);
//                    TextView view2 = CommonPagerAdapter.generateView(R.id.txt_test, view);
//                    view1.setImageResource(bean.getRes());
//                    view2.setText(bean.getContent());
//                }
//            }
//        });

    }
}
