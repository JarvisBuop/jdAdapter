package com.jd.myadapterlib.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

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
    private Context mContext;
    String[] titles;
    List<Fragment> mList;
    private RecyViewHolder holder;

    public void setmList(List<Fragment> mList, String[] titles) {
        this.mList = mList;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public void setmList(List<Fragment> mList) {//设置title为空,使用自定义的tablayout;
        titles = null;
        this.mList = mList;
        notifyDataSetChanged();
    }

    public CommonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CommonFragmentPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
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

    public View getTabView(int position, int layoutId) {
        if (mContext == null) {
            throw new IllegalArgumentException("context must not null");
        }
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        holder = new RecyViewHolder(mContext, view);
        if (mListener != null) {
            mListener.itemConvert(holder, mList.get(position), position);
        }
        return view;
    }

    /**
     * 用于自定义tablayout view;
     */
    private DOnItemConvertListener mListener;

    public void setDOnItemConvertListener(DOnItemConvertListener listener) {
        this.mListener = listener;
    }
    /**
     * 设置图片spnnerstring
     * 需要设置textallcap;
     Drawable image = context.getResources().getDrawable(imageResId[position]);
     image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
     SpannableString sb = new SpannableString(" " + tabTitles[position]);
     ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
     sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
     return sb;*/
}
