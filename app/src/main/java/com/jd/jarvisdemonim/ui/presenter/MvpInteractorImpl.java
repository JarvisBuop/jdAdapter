package com.jd.jarvisdemonim.ui.presenter;

import android.support.annotation.Nullable;

import com.jd.jarvisdemonim.entity.RetrofitTestBean;
import com.jd.jarvisdemonim.http.HttpUtils;
import com.jd.jdkit.IInterface.mvp.BaseInteractor;
import com.jd.jdkit.okhttp.ResultCallback;
import com.lzy.okhttputils.model.HttpParams;
import com.lzy.okhttputils.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView: 联网获取bean的model层的实现类;
 * Usage:
 */

public class MvpInteractorImpl implements BaseInteractor {

    @Override
    public void getModelMulti(onLoadFinishListener mListener, @Nullable String... params) {
    }

    @Override
    public void getModel(final onLoadFinishListener mListener, @Nullable HttpParams params) {
        HttpUtils.getInstance().getMainTestUrl(params, new ResultCallback<RetrofitTestBean>() {
            @Override
            public void onBefore(BaseRequest request) {
                mListener.onBefore();
            }

            @Override
            public void onAfter(@Nullable RetrofitTestBean retrofitTestBean, @Nullable Exception e) {
                mListener.onAfter();
            }

            @Override
            public void onCacheSuccess(RetrofitTestBean retrofitTestBean, Call call) {
                mListener.onCache(retrofitTestBean);
            }

            @Override
            public void onSuccess(RetrofitTestBean retrofitTestBean, Call call, Response response) {
                mListener.onSuccess(retrofitTestBean);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                mListener.onError(e);
            }
        });
    }
}
