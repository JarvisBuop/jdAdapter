package com.jd.jarvisdemonim.ui.testadapteractivity.model;

import android.support.annotation.Nullable;

import com.jd.jarvisdemonim.http.HttpUtils;
import com.jd.jdkit.entity.ModelImpl;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.jd.jdkit.okhttp.ResultCallback;
import com.lzy.okhttputils.model.HttpParams;
import com.lzy.okhttputils.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/4 0004
 * Name:
 * OverView: 联网下载测试;
 * Usage:
 */

public class RegisModelImpl implements ModelImpl {
    @Override
    public void getModel(final IOnHttpListener mListener, HttpParams mParams) {
        HttpUtils.getInstance().registerUuid4App(mParams, new ResultCallback<RegisterUuid4AppBean>() {
            @Override
            public void onBefore(BaseRequest request) {
                mListener.onBefore();
            }

            @Override
            public void onAfter(@Nullable RegisterUuid4AppBean registerUuid4AppBean, @Nullable Exception e) {
                mListener.onAfter();
            }

            @Override
            public void onCacheSuccess(RegisterUuid4AppBean registerUuid4AppBean, Call call) {
                mListener.onCache(registerUuid4AppBean);
            }

            @Override
            public void onSuccess(RegisterUuid4AppBean registerUuid4AppBean, Call call, Response response) {
                mListener.onSuccess(registerUuid4AppBean);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                mListener.onError(response,e);
            }
        });
    }
}
