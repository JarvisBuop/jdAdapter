package com.jd.jarvisdemonim.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jd.jarvisdemonim.binder.BinderPool;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/15 0015
 * Name:
 * OverView: 测试binderpool的服务,实现对多个aidl的对接;
 * Usage: binder连接池获取对应的binder,onbinder中返回需要的binder对象;
 * 真正实现的就是:将原本的aidl接口binder,换成抽象接口,扩展功能,多种binder;
 */

public class BinderPoolService extends Service {
    Binder mbinder = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }
}
