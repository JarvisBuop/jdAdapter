package com.jd.jdkit.elementkit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jd.jdkit.BaseConfig;
import com.jd.jdkit.R;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ReflectionUtil;
import com.jd.jdkit.elementkit.utils.system.SystemBarTintManager;
import com.lzy.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/30 0030
 * Name:
 * OverView: kit-activity基准类;
 * Usage:
 */
public abstract class DBaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    protected Context mContext = this;
    private static Handler handler;
    protected String TAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        TAG = this.getClass().getSimpleName();
        LogUtils.d(TAG, TAG + "-isCreate");
        initSystemBarTint();
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initVariable();
        processLogic(savedInstanceState);
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, TAG + "-isDestroy");
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        invokeFragmentManagerNoteStateNotSaved();
        super.onBackPressed();
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        FragmentManager fm = getSupportFragmentManager();
        ReflectionUtil.invokeMethod(fm, "noteStateNotSaved", null);
    }

    /**
     * fragment management
     */
    public DBaseFragment addFragment(DBaseFragment fragment) {
        List<DBaseFragment> fragments = new ArrayList<DBaseFragment>(1);
        fragments.add(fragment);

        List<DBaseFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    /**
     * 将fragment列表的fragment交易显示;
     * 只是添加一个framment;
     *
     * @param fragments
     * @return
     */
    private List<DBaseFragment> addFragments(List<DBaseFragment> fragments) {
        List<DBaseFragment> fragments2 = new ArrayList(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            DBaseFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            DBaseFragment fragment2 = (DBaseFragment) fm.findFragmentById(id);

            if (fragment2 == null) {
                fragment2 = fragment;
                if (!fragment2.isAdded()) {
                    transaction.add(id, fragment);
                }
                commit = true;
            }
            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                LogUtils.ui("baseactivity commit err");
            }
        }

        return fragments2;
    }

    /**
     * 添加多个fragment;
     * 一个一个加;
     *
     * @param fragments
     * @param tag
     * @return
     */
    public List<DBaseFragment> addFragments(List<DBaseFragment> fragments, String tag) {
        List<DBaseFragment> fragments2 = new ArrayList(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        DBaseFragment fragment2 = (DBaseFragment) fm.findFragmentByTag(tag);

        DBaseFragment fragment = fragments.get(Integer.parseInt(tag));//总的fragment列表;
        int id = fragment.getContainerId();
        if (fragment2 == null) {
            fragment2 = fragment;
            if (!fragment2.isAdded()) {
                transaction.add(id, fragment);
            }
        }
        for (int i = 0; i < fragments.size(); i++) {
            DBaseFragment seleltedFragment = fragments.get(i);
            if (Integer.parseInt(tag) == i) {
                transaction.show(seleltedFragment);
            } else {
                transaction.hide(seleltedFragment);
            }
            fragments2.add(i, fragment2);
        }

        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            LogUtils.ui("baseactivity commit err");
        }

        return fragments2;
    }

    /**
     * replace fragment;
     * @param fragment
     * @return
     */
    public DBaseFragment switchContent(DBaseFragment fragment) {
        return switchContent(fragment, false);
    }

    protected DBaseFragment switchContent(DBaseFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }
        return fragment;
    }

    protected void switchFragmentContent(DBaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fragment.getContainerId(), fragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化布局;
     *
     * @return
     */
    public abstract int getContentViewId();

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化变量
     */
    protected abstract void initVariable();

    /**
     * 给View控件添加事件监听器
     */
//    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 调用接口时，显示dialog
     */
    public void showDialog() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(mContext.getResources().getString(R.string.loading));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                OkHttpUtils.getInstance().cancelTag(BaseConfig.httpTag);//取消时取消网路请求;
            }
        });
        dialog.show();
    }

    /**
     * 处理接口返回时，隐藏dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }
}
