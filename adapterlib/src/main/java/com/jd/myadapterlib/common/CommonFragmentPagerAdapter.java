package com.jd.myadapterlib.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23
 * Name:
 * OverView: fragment 的切换;FragmentPagerAdapter
 * tablayout & viewpager
 * Usage:
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    String[] titles;
    List<Fragment> mList;

    public void setmList(List<Fragment> mList, String[] titles) {
        this.mList = mList;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public CommonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? super.getPageTitle(position) : titles[position];
    }
}
