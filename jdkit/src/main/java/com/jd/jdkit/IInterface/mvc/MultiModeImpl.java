package com.jd.jdkit.IInterface.mvc;

import com.jd.jdkit.okhttp.IOnHttpListener;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/4 0004
 * Name:
 * OverView:MVC
 * okhttp联网的model基类,包含多种数据参数;
 * Usage: 用于直接传递的参数;
 */

public interface MultiModeImpl {
    void getModel(IOnHttpListener mListener, String... mParams);
}
