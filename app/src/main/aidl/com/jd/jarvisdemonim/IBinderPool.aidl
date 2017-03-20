// IBinderPool.aidl
package com.jd.jarvisdemonim;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
     * 这个就是使多个aidl接口通过querybinder转化多个binder的接口;.
     * 获取多种类的binder->ITestBinderPool1&ITestBinderPool2
     */
    IBinder queryBinder(int binderCode);
}
