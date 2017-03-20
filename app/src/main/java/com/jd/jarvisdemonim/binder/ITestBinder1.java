package com.jd.jarvisdemonim.binder;

import android.os.RemoteException;

import com.jd.jarvisdemonim.ITestBinderPool1;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/15 0015
 * Name:
 * OverView: 具体应用的binder,其中一个aidl接口生成的binder对象;
 * Usage: 用于binderpool 按照给定的bindercode 去选择的binder;
 */

public class ITestBinder1 extends ITestBinderPool1.Stub {
    @Override
    public String addFunc(String content) throws RemoteException {
        return content+"66666666666666";
    }

    @Override
    public String delFunc(String content) throws RemoteException {
        int i = content.indexOf("66666666666666");
        String str = content.substring(0,i);
        LogUtils.i("string",i+"/"+str);
        return str;
    }
}
