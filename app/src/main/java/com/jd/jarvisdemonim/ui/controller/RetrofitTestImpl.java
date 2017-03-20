package com.jd.jarvisdemonim.ui.controller;

import com.jd.jarvisdemonim.entity.RetrofitTestBean;
import com.jd.jarvisdemonim.http.RApiService;
import com.jd.jarvisdemonim.http.RetrofitHelper;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.jd.jdkit.IInterface.mvc.MultiModeImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/17 0017
 * Name:
 * OverView: model 调取网络请求;
 * Usage:
 */

public class RetrofitTestImpl implements MultiModeImpl {

    @Override
    public void getModel(final IOnHttpListener mListener, String... mParams) {
        mListener.onBefore();
        RApiService apiService = RetrofitHelper.getInstance().getApiService(null, RApiService.class, null);
        Call<RetrofitTestBean> datasByRetrofit = apiService.getDatasByRetrofit(mParams[0], mParams[1], mParams[2], mParams[3], mParams[4], mParams[5], mParams[6]);
        datasByRetrofit.enqueue(new Callback<RetrofitTestBean>() {
            @Override
            public void onResponse(Call<RetrofitTestBean> call, Response<RetrofitTestBean> response) {
                RetrofitTestBean body = response.body();
                mListener.onSuccess(body);
                mListener.onAfter();
            }

            @Override
            public void onFailure(Call<RetrofitTestBean> call, Throwable t) {
                mListener.onError(null,null);
                mListener.onAfter();
            }
        });
    }
}
