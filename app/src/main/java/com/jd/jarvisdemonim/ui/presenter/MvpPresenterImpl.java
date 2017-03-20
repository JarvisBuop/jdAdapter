package com.jd.jarvisdemonim.ui.presenter;

import android.support.annotation.Nullable;

import com.jd.jdkit.IInterface.mvp.BaseInteractor;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.lzy.okhttputils.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView: presenter 用于在model,view层充当中间层;
 * Usage:
 */

public class MvpPresenterImpl implements NTestPresenter, BaseInteractor.onLoadFinishListener {
    MvpInteractorImpl interactorImpl;//此接口用于获取model;
    NTestMvpView baseView;//此接口用于通知view改变;
    private HttpParams mParams;
    private int currentPage = 1;
    List<Object> mDatas;

    public MvpPresenterImpl(NTestMvpView baseView, MvpInteractorImpl interactorImpl) {
        mDatas = new ArrayList<>();
        this.baseView = baseView;
        this.interactorImpl = interactorImpl;
    }

    public MvpPresenterImpl(NTestMvpView baseView) {
        mDatas = new ArrayList<>();
        this.baseView = baseView;
        this.interactorImpl = new MvpInteractorImpl();
    }


    /**
     * activity 中的具体实现;
     */
    @Override
    public void onItemClick(int pos, Object o) {

    }

    @Override
    public void loadMore() {
        currentPage++;
        doPost();
    }

    @Override
    public void onStart() {
        LogUtils.i("onStart");
    }

    @Override
    public void onResume() {
        doPost();
    }

    private void doPost() {
        mParams = new HttpParams();
        mParams.put("api", "search");
        mParams.put("action", "search");
        mParams.put("page", String.valueOf(currentPage));
        mParams.put("pageSize", "3");
        mParams.put("order", "C_Time");
        mParams.put("sqlText", "Title=\"图书\"");
        mParams.put("content", "图书");
        LogUtils.i("params " + String.valueOf(currentPage));
        baseView.showProgress();
        interactorImpl.getModel(this, mParams);
    }

    @Override
    public void onStop() {
        LogUtils.i("onStop");
    }

    @Override
    public void onDestroy() {
        LogUtils.i("onDestroy");
    }

    /**
     * 联网下载,model中的具体实现;
     *
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        LogUtils.i("onSuccess");
        baseView.adapterNotify(o);
        for (int i = 0; i < 5; i++) {
            mDatas.add("test" + i);
        }
        baseView.adapterNotify(mDatas);
    }

    @Override
    public void onError(@Nullable Exception e) {
        LogUtils.i("onError");
    }

    @Override
    public void onCache(Object o) {
        LogUtils.i("onCache");
    }

    @Override
    public void onAfter() {
        LogUtils.i("onAfter");
        baseView.dismissProgress();
    }

    @Override
    public void onBefore() {
        LogUtils.i("onBefore");
    }
}
