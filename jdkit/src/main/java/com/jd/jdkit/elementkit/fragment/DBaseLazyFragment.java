package com.jd.jdkit.elementkit.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/4 0004
 * Name:
 * OverView: 懒加载,基准类;
 * Usage:此懒加载只是延迟了数据的懒加载,控件还是预加载;
 */

public abstract class DBaseLazyFragment extends Fragment {
    protected boolean isViewInitiated;//当前view是否建立映射关系;
    protected boolean isVisibleToUser;//当前fragment是否可见;
    protected boolean isDataInitiated;//是否加载数据;
    private Context mContext;
    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onCreate");
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onCreateView");
        View view = initXml(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        isViewInitiated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    //传true可刷新;
    public boolean prepareFetchData(boolean update) {
        LogUtils.i("DBaseLazyFragment",this.getClass().getSimpleName()+"状态:"+isVisibleToUser+"/"+isViewInitiated+"/"+(!isDataInitiated || update));
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || update)) {
            initData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    @Override
    public void onStart() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onStop");
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-setUserVisibleHint " +isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    @Override
    public void onDestroyView() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onDestroyView");
        super.onDestroyView();
        isDataInitiated = false;//设置此为false,因为viewpager的预加载功能,每次选中都能重新运行initdata,加载数据;
    }

    @Override
    public void onDestroy() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "-onDetach");
        super.onDetach();
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
     */
    protected abstract void initData();

}
