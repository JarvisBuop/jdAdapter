package com.jd.jarvisdemonim.ui.presenter;

import com.jd.jdkit.IInterface.mvp.BaseViewImpl;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView:MVP NormalTestMvpActivity设置在presenter中控制activity中的view层的控制;
 * Usage: 可添加额外抽象方法;
 *
 * MVP主要是通过此接口将View设置数据;
 *
 * @see NormalTestMvpActivity
 */

public interface NTestMvpView<T> extends BaseViewImpl {
    void adapterNotify(List<T> mlist);
    void adapterNotify(T t);
}
