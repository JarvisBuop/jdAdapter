package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.HomeFragment;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.reminder.ReminderItem;
import com.jd.jdkit.reminder.ReminderManager;
import com.jd.myadapterlib.common.CommonFragmentPagerAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/29 0029
 * Name:
 * OverView:
 * Usage:
 */
public class NormalTestTabReminderActivity extends DBaseActivity implements ReminderManager.UnreadNumChangedCallback {
    private ViewPager mViewpager;
    private TabLayout mTablayout;
    CommonFragmentPagerAdapter mAdapter;
    private HomeFragment fragment;

    @Override
    public int getContentViewId() {
        return R.layout.activity_nim;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        registerMsgUnreadInfoObserver(true);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);

        assert mViewpager != null;
        assert mTablayout != null;
    }

    @Override
    protected void initVariable() {
        mAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), this);
        final String[] arr = new String[10];
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = "title" + i;
            fragment = HomeFragment.newInstance(String.valueOf(i));
            fragmentList.add(fragment);
        }
        mAdapter.setmList(fragmentList);
        mViewpager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewpager);
        mAdapter.setDOnItemConvertListener(new DOnItemConvertListener() {
            @Override
            public void itemConvert(RecyViewHolder viewHolder, Object item, int position) {
                viewHolder.setText(R.id.tab_title_label, arr[position]);
//                viewHolder.setVisible(R.id.tab_new_indicator,true);
            }
        });
        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            ReminderManager.getInstance().setReminder(i);//设置需要的监听;
            tab.setCustomView(mAdapter.getTabView(i, R.layout.tab_layout_main));
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void setCurrentMsg(int position) {
        TabLayout.Tab tab = mTablayout.getTabAt(position);
        View customView = tab.getCustomView();
        ImageView viewById = (ImageView) customView.findViewById(R.id.tab_new_indicator);
        TextView txt2 = (TextView) customView.findViewById(R.id.tab_new_msg_label);
        viewById.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        txt2.setText("99");
        tab.setCustomView(customView);
    }

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        TabLayout.Tab tab = mTablayout.getTabAt(item.getId());
        if (tab != null) {
            View customView = tab.getCustomView();
            ImageView viewById = (ImageView) customView.findViewById(R.id.tab_new_indicator);
            TextView txt2 = (TextView) customView.findViewById(R.id.tab_new_msg_label);
            txt2.setText(item.getUnread() + "");
            txt2.setVisibility(item.getUnread() > 0 ? View.VISIBLE : View.GONE);
            tab.setCustomView(customView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
    }
}
