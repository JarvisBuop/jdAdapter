package com.jd.jarvisdemonim.ui.controller;

import com.jd.jarvisdemonim.entity.TestBean;
import com.jd.jarvisdemonim.entity.TestBean2;
import com.jd.jdkit.IInterface.mvc.ModelImpl;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/4 0004
 * Name:
 * OverView: 数据上啦加载更多的加载类;NormalTestLoad1Activity
 * Usage:
 */

public class LoadMultiImpl implements ModelImpl {
    private List<Object> beans;
    int i = 0;

    public LoadMultiImpl() {
        beans = new ArrayList<>();
    }

    @Override
    public void getModel(IOnHttpListener mListener, HttpParams mParams) {
        beans.add(new TestBean("11", "content1"));
        beans.add(new TestBean("15", "content2"));
        beans.add(new TestBean2("21"));
        beans.add(new TestBean("12", "content2"));
        beans.add(new TestBean("13", "content2"));
        beans.add(new TestBean2("22"));
        beans.add(new TestBean("14", "content2"));
        beans.add(new TestBean2("23"));
        beans.add(new TestBean2("24"));
        mListener.onSuccess(beans);
    }

    public List<Object> loadMore() {
        i++;
        if (i >= 5) {
            return beans;
        }
        beans.add(new TestBean("**", "^^"));
        beans.add(new TestBean2("##"));
        return beans;
    }
}
