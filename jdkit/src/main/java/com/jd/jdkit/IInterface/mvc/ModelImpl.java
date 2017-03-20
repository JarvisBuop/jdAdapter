package com.jd.jdkit.IInterface.mvc;

import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;


/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/4 0004
 * Name:
 * OverView:MVC
 * okhttp联网的model基类;
 * Usage:用于okhttp使用httpparams传递的参数;
 */

public interface ModelImpl {
    void getModel(IOnHttpListener mListener, HttpParams mParams);
}
