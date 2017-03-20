package com.jd.jdkit.IInterface.mvp;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView:MVP presenter
 * Usage: 保留方法 onAction();
 */

public interface BasePresenter<T> {
    void onStart();

    void onResume();

    void onStop();

    void onDestroy();
}
