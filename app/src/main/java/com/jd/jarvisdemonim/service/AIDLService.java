package com.jd.jarvisdemonim.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jd.jarvisdemonim.IMyAidlInterface;
import com.jd.jarvisdemonim.MyIpcBean;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/9 0009
 * Name:
 * OverView:
 * Usage:
 */

public class AIDLService extends Service {
    public final String TAG = this.getClass().getSimpleName();
    private List<MyIpcBean> mBeanList;
    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public List<MyIpcBean> getBean() throws RemoteException {
            synchronized (MyIpcBean.class) {
                LogUtils.i("AIDLService", "获取运行");
                return mBeanList;
            }
        }

        @Override
        public void addBean(MyIpcBean bean) throws RemoteException {
            synchronized (MyIpcBean.class) {
                if (mBeanList != null && !mBeanList.contains(bean)) {
                    LogUtils.i("AIDLService", "添加运行");
                    mBeanList.add(bean);
                }
            }
        }
    };

    public class mybinder extends Binder {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBeanList = new ArrayList<>();//可以考虑从缓存中获取;
        LogUtils.i("AIDLService", "service oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //startService运行;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("AIDLService", "service ondestory");
    }
}
