package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.OneFragment;
import com.jd.jarvisdemonim.ui.testfragment.ThreeFragment;
import com.jd.jarvisdemonim.ui.testfragment.TwoFragment;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/6 0006
 * Name:
 * OverView:
 * Usage:含有md calendar; fragment的切换,一个个的添加;
 */

public class NormalTestBottomNavigationActivity extends DBaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    public static int[] arrLayoutRes = {R.layout.fragment_test1, R.layout.fragment_test2, R.layout.fragment_test3};
    public static String[] arrTitle = {"title" + 0, "title" + 1, "title" + 2};
    @Bind(R.id.framelayout)
    FrameLayout mFrame;
    @Bind(R.id.bottomnavigation)
    BottomNavigationBar mBnb;

    List<DBaseFragment> mFragments;
    BadgeItem numberBadgeItem;//设置徽章
    int lastSelectedPosition = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bottom_tablayout_materialcarlendar2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mFragments.add(OneFragment.newIntance(arrTitle[0], arrLayoutRes[0]));
        mFragments.add(TwoFragment.newIntance(arrTitle[1], arrLayoutRes[1]));
        mFragments.add(ThreeFragment.newIntance(arrTitle[2], arrLayoutRes[2]));
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        addFragment(mFragments.get(0));//添加默认的第一个fragment;
        mBnb.setTabSelectedListener(this);
        bnbRefresh();//显示tab;
    }

    //navigationbar
    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        addFragments(mFragments, String.valueOf(position));//切换fragment;
        if (numberBadgeItem != null) {
            numberBadgeItem.setText(Integer.toString(position));
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 貌似bottomnavigationItem 改变不了图片,图片会全部显示背景;
     */
    private void bnbRefresh() {
        mBnb.clearAll();
        //设置徽章;
        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(true);//自动隐藏徽章;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_icon_1);

        mBnb
                .addItem(new BottomNavigationItem(new BitmapDrawable(getResources(), bitmap), "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                /**
                 * 直接new的图片用于选中的图片;
                 * setInactiveIconResource 用于未选中的图片;
                 * setInActiveColorResource 用于未选中的文字颜色;
                 * setActiveColorResource 用于选中的文字颜色;
                 */
                .addItem(new BottomNavigationItem(R.drawable.head_icon_2, "Books").setInactiveIconResource(R.drawable.head_icon_1).setInActiveColorResource(R.color.orange).setActiveColorResource(R.color.teal))
                /**
                 * 如果不设置未选中的图片,则默认的是整个图片成为背景色;
                 */
                .addItem(new BottomNavigationItem(R.drawable.pic3, "Music").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
    }
}
