package com.jd.jdkit.entity;

import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;


/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/4 0004
 * Name:
 * OverView:
 * Usage:
 */

public interface ModelImpl {
    void getModel(IOnHttpListener mListener, HttpParams mParams);
}
