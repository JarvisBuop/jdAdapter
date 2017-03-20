package com.jd.jdkit.IInterface.mvp;

import android.support.annotation.Nullable;

import com.lzy.okhttputils.model.HttpParams;

public interface BaseInteractor<T> {
    interface onLoadFinishListener<T> {
        void onSuccess(T t);

        void onError(@Nullable Exception e);

        void onCache(T t);

        void onAfter();

        void onBefore();
    }

    void getModelMulti(onLoadFinishListener mListener, @Nullable String... params);

    void getModel(onLoadFinishListener mListener, @Nullable HttpParams params);
}
