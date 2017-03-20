package com.jd.jarvisdemonim.binder;

import android.os.RemoteException;

import com.jd.jarvisdemonim.ITestBinderPool2;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/15 0015
 * Name:
 * OverView: 用于匹配对应的aidl接口,返回binder
 * Usage:
 */

public class ITestBinder2 extends ITestBinderPool2.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
