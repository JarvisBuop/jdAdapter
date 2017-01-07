package com.jd.jarvisdemonim.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.views.CustomProgress;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import butterknife.ButterKnife;

/**
 * Name:    基准basefragment;
 */
public abstract class BaseFragment extends Fragment {
    private CustomProgress pd;
    private boolean cancelable = true;
    private Context mContext;
    public LayoutInflater mInflater;
    public Activity mActivity;
    public boolean dialogMiss = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        LogUtils.d(this.getClass().getSimpleName(), "is createdview in " + mActivity.getClass().getSimpleName());
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
    }

    @Override
    public void onDestroyView() {
        LogUtils.d(this.getClass().getSimpleName(), "is destroyview in " + mActivity.getClass().getSimpleName());
        super.onDestroyView();
        ButterKnife.unbind(this);
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
    public void initProgressDialog() {
        if (pd == null) {
            pd = CustomProgress.show(mContext, mContext.getResources().getString(R.string.loading),
                    cancelable, cancelable ? new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
//                            HttpUtils.cancelPost();
                            dialogMiss = true;
                        }
                    } : null);

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    /**
     * 处理接口返回时，隐藏dialog
     */
    public void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    /**
     * 简化findviewbyid;
     *
     * @param id
     * @param view
     * @param <T>
     * @return
     */
    public <T extends View> T generateView(int id, View view) {
        return (T) view.findViewById(id);
    }

    /**
     * * Fragment切换
     *
     * @Params toFragment 将要切换到的Fragment
     * resId      装载Fragment的view Id
     * index      Fragment的标识index
     * toleft     判断Fragment向左切换还是向右切换，以采用不同的动画
     * Notes:  R.anim.push_left_in等均为简单的Tranlate动画
     * mCurrentFragment为当前所在的Fragment，继承自BaseFragment
     */
    protected void switchDiffFragmentContent
    (Fragment mCurrentFragment, Fragment toFragment, int resId, int index, boolean toleft) {
        if (null == mCurrentFragment || null == toFragment) {
            return;
        }
        if (mCurrentFragment.getArguments().getInt("Index") !=
                toFragment.getArguments().getInt("Index")) {
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getChildFragmentManager().beginTransaction();
//            if (toleft)
//            {
//                fragmentTrasaction.SetCustomAnimations(R.anim.push_left_in,
//                        R.anim.push_left_out);
//            }else{
//                fragmentTrasaction.setCustomAnimations(R.anim.push_right_in,
//                        R.anim.push_right_out);
//            }
            //先判断是否添加过
            if (!toFragment.isAdded()) {
                //隐藏当前fragment,add下一个fragment
                fragmentTransaction.hide(mCurrentFragment);
                fragmentTransaction.add(resId, toFragment, String.valueOf(index));
                fragmentTransaction.commit();
            } else {
                //隐藏当前fragment，show下一个fragment
                fragmentTransaction.hide(mCurrentFragment);
                fragmentTransaction.show(toFragment);
                fragmentTransaction.commit();
            }
            mCurrentFragment = (BaseFragment) toFragment;
        }
    }

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


    /********************
     * 设置title;
     *********************/
    protected void setBack() {
        ImageView back = (ImageView) mActivity.findViewById(R.id.back);
        if (back == null)
            return;
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mActivity.finish();
            }
        });
    }

    protected void setTitle(String titleName) {
        TextView title = (TextView) mActivity.findViewById(R.id.title);
        if (title == null)
            return;
        title.setText(titleName);
    }

    protected View setSearch() {
        ImageView right = (ImageView) mActivity.findViewById(R.id.search);
        if (right == null) {
            return null;
        } else {
            right.setVisibility(View.VISIBLE);
            return right;
        }
    }

    protected View setHome() {
        ImageView right = (ImageView) mActivity.findViewById(R.id.home);
        if (right == null) {
            return null;
        } else {
            right.setVisibility(View.VISIBLE);
            return right;
        }
    }

}
