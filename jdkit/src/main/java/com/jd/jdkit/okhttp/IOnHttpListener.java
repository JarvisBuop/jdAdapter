package com.jd.jdkit.okhttp;

import okhttp3.Response;

public interface IOnHttpListener<T> {
    void onSuccess(T t);
    void onError(Response response, Exception e);
    void onCache(T t);
    void onAfter();
    void onBefore();
}
