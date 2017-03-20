package com.jd.jarvisdemonim.ui.presenter;

import com.jd.jdkit.IInterface.mvp.BasePresenter;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView: NormalTestMvpActivity的presenter,基准的persenter中添加抽象方法;
 * Usage:   MVP通过此接口在model和view间建立联系;
 * @see NormalTestMvpActivity
 */

public interface NTestPresenter<T> extends BasePresenter {
    void onItemClick(int pos, T t);
    void loadMore();
}
