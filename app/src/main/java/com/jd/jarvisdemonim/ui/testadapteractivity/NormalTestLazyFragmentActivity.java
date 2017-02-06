package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment1;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.myadapterlib.common.CommonFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/4 0004
 * Name:
 * OverView:
 * Usage:
 */

public class NormalTestLazyFragmentActivity extends DBaseActivity {
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    CommonFragmentPagerAdapter mFPagerAdapter;
    TestLazyFragment1 fragment1;
    List<Fragment> mfrag;

    @Override
    public int getContentViewId() {
        return R.layout.activity_nim;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
        mfrag = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            fragment1 = TestLazyFragment1.newInstance("TestLazyFragment" + i + ":显示;");
            mfrag.add(fragment1);
        }
        mFPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), mContext);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mFPagerAdapter.setmList(mfrag);
//        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setAdapter(mFPagerAdapter);
    }
}
