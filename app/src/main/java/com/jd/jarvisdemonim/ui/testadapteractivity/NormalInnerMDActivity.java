package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.system.SnackbarUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/23 0023
 * Name:
 * OverView: 测试材料设计MaterialDesign;
 * Usage: MDActivity的内部Behavior设计;
 */

public class NormalInnerMDActivity extends DBaseActivity implements View.OnClickListener {
    @Bind(R.id.fab_behavior)
    FloatingActionButton fabBehavior;
    @Bind(R.id.coordinatorlayout2)
    CoordinatorLayout mRoot;
    @Bind(R.id.toolbar2)
    Toolbar mToolbar;
    @Bind(R.id.reccler2)
    RecyclerView mRecy;
    //    XRecyclerView mRecy;
    @Bind(R.id.fab2)
    FloatingActionButton fab;
    @Bind(R.id.tablayout2)
    TabLayout mTablayout;
    List<String> mDatas;
    private boolean mFlag;

    @Override
    public int getContentViewId() {
        return R.layout.md_activity_inner_mdtest;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("item" + i);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("My Test");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu1) {
                    SnackbarUtils.showShortSnackbar(mRoot, "menu1", Color.GREEN, Color.BLUE);
                    mFlag = true;
                    invalidateOptionsMenu();
                }else if(item.getItemId() == R.id.menu2){
                    SnackbarUtils.showShortSnackbar(mRoot, "menu2", Color.GREEN, Color.BLUE);
                    mFlag = false;
                    invalidateOptionsMenu();
                }
                return true;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initVariable() {
        for (int i = 0; i < 5; i++) {
            TabLayout.Tab tab = mTablayout.newTab().setText("tab" + i);
            mTablayout.addTab(tab);
        }
        mRecy.setLayoutManager(new LinearLayoutManager(mContext));
        mRecy.setAdapter(new RecyCommonAdapter(mRecy, R.layout.test2_item, mDatas) {
            @Override
            protected void convert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof String) {
                    String str = (String) item;
                    viewHolder.setText(R.id.txt_two, str);
                    viewHolder.setItemChildClickListener(R.id.bg_ll);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab2:
                SnackbarUtils.showShortSnackbar(mRoot, "fab", Color.GREEN, Color.BLUE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (mFlag) {
//            menu.findItem(R.id.menu1).setVisible(false);
//            menu.findItem(R.id.menu2).setVisible(true);
//        } else {
//            menu.findItem(R.id.menu1).setVisible(true);
//            menu.findItem(R.id.menu2).setVisible(false);
//        }
        return super.onPrepareOptionsMenu(menu);
    }
}
