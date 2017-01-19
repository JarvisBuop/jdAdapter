package com.jd.jdkit.elementkit.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jd.jdkit.R;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import butterknife.ButterKnife;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/30 0030
 * Name:
 * OverView:kit-fragment 基准类;
 * Usage:
 */
public abstract class DBaseFragment extends Fragment {
    private static Handler handler;
    private int containerId;
    private String currentTag;//标记第几个fragment;
    private boolean destroyed;
    private ProgressDialog dialog;
    private boolean cancelable = true;
    private Context mContext;
    public LayoutInflater mInflater;
    public Activity mActivity;

    //是否使用缓存视图;
    private boolean isCacheView = false;
    private View mCacheView;

    public void setCacheView(boolean cacheView) {
        isCacheView = cacheView;
    }


    protected final boolean isDestroyed() {
        return destroyed;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public String getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(String currentTag) {
        this.currentTag = currentTag;
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        });
    }

    protected final void postDelayed(final Runnable runnable, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        }, delay);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(this.getClass().getSimpleName(), "is createdview in " + mActivity.getClass().getSimpleName());
        mInflater = inflater;
        if (isCacheView) {
            if (mCacheView == null) {
                mCacheView = initXml(inflater, container, savedInstanceState);
            }
            // 缓存View判断是否含有parent, 如果有需要从parent删除, 否则发生已有parent的错误.
            ViewGroup parent = (ViewGroup) mCacheView.getParent();
            if (parent != null) {
                parent.removeView(mCacheView);
            }
            ButterKnife.bind(this, mCacheView);
            return mCacheView;
        }
        View view = initXml(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        processLogic(savedInstanceState);
        initListener();
        destroyed = false;
    }

    @Override
    public void onDestroyView() {
        LogUtils.d(this.getClass().getSimpleName(), "is destroyview in " + mActivity.getClass().getSimpleName());
        super.onDestroyView();
        ButterKnife.unbind(this);
        destroyed = true;
        System.gc();
    }

    /**
     * 初始化布局;
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    protected abstract View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化控件;
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据,添加逻辑;
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 初始化事件;
     */
    protected abstract void initListener();

    /**
     * 调用接口时，显示dialog
     */
    public void showDialog() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(mContext.getResources().getString(R.string.loading));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                OkHttpUtils.getInstance().cancelTag(BaseConfig.httpTag);
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

    /**
     * 简化findviewbyid;
     *
     * @param resId
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(@IdRes int resId) {
        return (T) getView().findViewById(resId);
    }

    /**
     * 备用;
     */
    /**
     * fragment跳转
     *
     * @param containerViewId 替换的布局
     * @param fragment        跳转的fragment
     * @param bundle          参数
     * @param isBack          是否可以返回到当前页
     */
    protected void startFragment(int containerViewId, Fragment fragment, Bundle bundle, boolean isBack) {
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(containerViewId, fragment);
        if (isBack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }

    /**
     * fragment跳转
     *
     * @param containerViewId 替换的布局
     * @param fragment        跳转的fragment
     * @param isBack          是否可以返回到当前页
     */
    protected void startFragment(int containerViewId, Fragment fragment, boolean isBack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(containerViewId, fragment);
        if (isBack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }

    /**
     * fragment切换
     *
     * @param containerViewId
     * @param from
     * @param to
     * @param isback
     */
    public void switchFragment(int containerViewId, Fragment from, Fragment to, boolean isback) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(containerViewId, to);
        if (isback) {
            ft.addToBackStack(to.getClass().getName());
        }
        ft.commit();
    }

    /**
     * 内嵌fragment的切换
     *
     * @param containerViewId
     * @param from
     * @param to
     */
    protected void switchChildFragment(int containerViewId, Fragment from, Fragment to, boolean isback) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.hide(from);
        ft.replace(containerViewId, to);
        if (isback) {
            ft.addToBackStack(to.getClass().getName());
        }
        ft.commit();
    }
}
