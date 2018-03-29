package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment1;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment2;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment3;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment4;
import com.jd.jarvisdemonim.ui.testfragment.TestLazyFragment5;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.myadapterlib.common.CommonFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/4 0004
 * Name:
 * OverView: 懒加载fragment;
 * Usage:
 */

public class NormalTestLazyFragmentActivity extends DBaseActivity {
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    CommonFragmentPagerAdapter mFPagerAdapter;
    TestLazyFragment1 fragment1;
    TestLazyFragment2 fragment2;
    TestLazyFragment3 fragment3;
    TestLazyFragment4 fragment4;
    TestLazyFragment5 fragment5;
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

        fragment1 = TestLazyFragment1.newInstance("TestLazyFragment" + 1 + ":显示;");
        fragment2 = TestLazyFragment2.newInstance("TestLazyFragment" + 2 + ":显示;");
        fragment3 = TestLazyFragment3.newInstance("TestLazyFragment" + 3 + ":显示;");
        fragment4 = TestLazyFragment4.newInstance("TestLazyFragment" + 4 + ":显示;");
        fragment5 = TestLazyFragment5.newInstance("TestLazyFragment" + 5 + ":显示;");
        mfrag.add(fragment1);
        mfrag.add(fragment2);
        mfrag.add(fragment3);
        mfrag.add(fragment4);
        mfrag.add(fragment5);
        mFPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), mContext);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mFPagerAdapter.setmList(mfrag);
//        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setAdapter(mFPagerAdapter);
    }
}
