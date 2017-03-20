package com.jd.jarvisdemonim.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.jd.jarvisdemonim.IBinderPool;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/15 0015
 * Name:
 * OverView:binder连接池,接口,用于根据不同的标志获取对应的binder对象;
 * 本次优化:只是将原本aidl接口,继承于stub(即binder)的binder转化为接口;
 * 来根据不同的bindercode来获取不同的binder对象;实现一个service可以绑定多种binder(使用querybinder转化不同的aidl接口)
 * Usage:
 */

public class BinderPool {
    public static final int BINDER_CODE_TEST1 = 0;
    public static final int BINDER_CODE_TEST2 = 1;
    private Context applicationContext;
    private Class mServiceClass;
    IBinderPool mIBinderPool;//线程池接口,用于获取binder;
    private static volatile BinderPool mInstance;//本单例类,要求编译器每次都重新赋值;

    /**
     * 含阻塞方法,同步辅助类,异步变为同步;
     */
    private CountDownLatch countDownLatch;

    private BinderPool() {

    }

    private BinderPool(Context mcontext, Class mServiceClass) {
        applicationContext = mcontext.getApplicationContext();
        this.mServiceClass = mServiceClass;
        connectionBinderPoolService(mServiceClass);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = BinderPoolImpl.asInterface(service);//获取bidner连接池的接口binder;现还获取没有具体的binder
            LogUtils.e("BinderPool", "服务连接");
            /**
             * 设置死亡代理;
             */
            try {
                mIBinderPool.asBinder().linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        LogUtils.e("deadrecipient", "binder dead");
                        mIBinderPool.asBinder().unlinkToDeath(this, 0);
                        mIBinderPool = null;
                        connectionBinderPoolService(mServiceClass);
                    }
                }, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();//countDown减为0,await的地方可以唤醒;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //连接binder连接池的服务;等待服务连接;
    private synchronized void connectionBinderPoolService(Class mServiceClass) {
        countDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(applicationContext, mServiceClass);
        applicationContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        try {
            countDownLatch.await();//阻塞线程,等待countdownlatch减为一;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mContext      context;
     * @param mServiceClass 服务类;
     * @return
     */
    public static BinderPool getInstance(Context mContext, Class mServiceClass) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null)
                    mInstance = new BinderPool(mContext, mServiceClass);
            }
        }
        return mInstance;
    }


    //此类中获取binder
    public IBinder queryBinderMethod(int binderCode) {
        IBinder binder = null;
        if (mIBinderPool != null) {
            try {
                binder = mIBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    //此处继承aidl的接口自动生成的代码,binder连接池接口的实现类;获取具体需要的binder对象;
    //该类是IBinderPool接口的实现类(子类;)
    public static class BinderPoolImpl extends IBinderPool.Stub {
        public BinderPoolImpl() {
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder mBinder = null;
            switch (binderCode) {
                case BINDER_CODE_TEST1:
                    mBinder = new ITestBinder1();
                    break;
                case BINDER_CODE_TEST2:
                    mBinder = new ITestBinder2();
                    break;
            }
            return mBinder;
        }
    }
}
