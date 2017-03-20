package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.BarUtils;
import com.jd.jdkit.elementkit.utils.system.SnackbarUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.elementkit.utils.system.ToastyUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/22 0022
 * Name:
 * OverView: MD设计 测试
 * Usage: design 包;
 * 问题1:NavigationView 的遮罩层展示没想到解决方法;
 * 问题2:CollapsingToolbarLayout和NavigationView会引起toolbar的title消失,未解决;
 */

public class NormalTestMDActivity extends DBaseActivity implements View.OnClickListener {
    @Bind(R.id.rootview)
    CoordinatorLayout mRoot;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collpasingtoolbarlayout)
    CollapsingToolbarLayout mCtl;
    @Bind(R.id.md_img)
    ImageView imgAnim;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    //drawlayout
    @Bind(R.id.drawlayout)
    DrawerLayout mDrawlayout;
    @Bind(R.id.drawer_left)
    LinearLayout view;
    ActionBarDrawerToggle mDrawerToggle;
    @Bind(R.id.drawer_right)//实际的左边;
            NavigationView mnavigation;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        imgAnim.setImageResource(R.drawable.pic_anim);
        AnimationDrawable drawable = (AnimationDrawable) imgAnim.getDrawable();
        drawable.start();
    }

    @Override
    public int getContentViewId() {
        return R.layout.md_activity_drawlayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置drawer margintop
        setMargin(view);
        setMargin(mnavigation);

        setSupportActionBar(toolbar);
        mDrawlayout.setScrimColor(Color.TRANSPARENT);
        mDrawlayout.setDrawerShadow(R.color.white, GravityCompat.START);//设置背景色;
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawlayout, toolbar, R.string.open, R.string.close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getActionBar().setTitle("123");
                mCtl.setTitle("可折叠标题");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle("321");
                mCtl.setTitle("可折叠标题");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        View headerView = mnavigation.getHeaderView(0);
        TextView textView1 = (TextView) headerView.findViewById(R.id.menu1);
        TextView textView2 = (TextView) headerView.findViewById(R.id.menu2);
        TextView textView3 = (TextView) headerView.findViewById(R.id.menu3);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
    }

    private void setMargin(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
        } else {
            //不存在时创建一个新的参数
            //基于View本身原有的布局参数对象
            marginParams = new ViewGroup.MarginLayoutParams(layoutParams);
        }
        if (marginParams != null) {
            //状态栏加action的高度;
            int topMargin = BarUtils.getStatusBarHeight(mContext) + BarUtils.getActionBarHeight(this);
            marginParams.setMargins(0, topMargin, 0, 0);
            LogUtils.i(topMargin + "");
            view.setLayoutParams(marginParams);
        }
    }

    @Override
    protected void initVariable() {
        //设置收缩时的文字颜色;
        mCtl.setCollapsedTitleTextColor(Color.WHITE);
        //设置展开的文字颜色;
        mCtl.setExpandedTitleColor(Color.WHITE);
        mCtl.setTitle("可折叠标题");

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);//自己菜单;
        toolbar.setPopupTheme(R.style.toolbar_pop);//设置toolbar的item背景色;
//        toolbar.setLogo(R.drawable.ic_gf_camera);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        msg += "Click edit";
                        mDrawlayout.openDrawer(GravityCompat.END);
                        break;
                    case R.id.action_share:
                        msg += "Click share";
                        mDrawlayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.action_settings:
                        msg += "Click setting";
                        break;
                }

                if (!msg.equals("")) {
                    SnackbarUtils.showShortSnackbar(mRoot, msg, Color.GREEN, Color.BLUE);
                }
                return true;
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDrawlayout.addDrawerListener(mDrawerToggle);
        fab.setOnClickListener(this);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        mnavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String str = null;
                switch (item.getItemId()) {
                    case R.id.navigation_item_1:
                        str = "1";
                        break;
//                    case R.id.navigation_item_2:
//                        str = "2";
//                        break;
                    case R.id.navigation_item_3:
                        str = "3";
                        break;
                    case R.id.navigation_sub_item_1:
                        str = "21";
                        break;
                    case R.id.navigation_sub_item_2:
                        str = "22";
                        break;
                    case R.id.navigation_sub_item_3:
                        str = "23";
                        break;
                    case R.id.navigation_sub_item_4:
                        str = "24";
                        break;
                }
                ToastUtils.showToast(str + "");
                mDrawlayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu1:
                ToastyUtils.showSuccess("menu1");
                break;
            case R.id.menu2:
                ToastyUtils.showSuccess("menu2");
                break;
            case R.id.menu3:
                ToastyUtils.showSuccess("menu3");
                break;
            default:
                SnackbarUtils.showShortSnackbar(mRoot, "message", Color.parseColor("#ffff00"), Color.parseColor("#00a0e9"), "确定", Color.parseColor("#ff00ff"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.i("click");
                    }
                });
        }
    }

    //设置toolbar的menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_md, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawlayout.isDrawerOpen(GravityCompat.START);
        LogUtils.i(drawerOpen + "");
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @OnClick({R.id.menu1, R.id.menu2, R.id.menu3, R.id.txt_intent1, R.id.txt_intent2})
    void click(View view) {
        switch (view.getId()) {
            case R.id.menu1:
                ToastUtils.showToast("menu1");
                break;
            case R.id.menu2:
                ToastUtils.showToast("menu2");
                break;
            case R.id.menu3:
                ToastUtils.showToast("menu3");
                break;
            case R.id.txt_intent1:
                startActivity(new Intent(mContext, NormalInnerMDActivity.class));
                break;
        }
    }
}
