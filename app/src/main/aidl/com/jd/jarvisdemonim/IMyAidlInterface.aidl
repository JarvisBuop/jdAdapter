// IMyAidlInterface.aidl
package com.jd.jarvisdemonim;
import com.jd.jarvisdemonim.MyIpcBean;
// Declare any non-default types here with import statements

//数据类型在aidl中的声明
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<MyIpcBean> getBean();
        void addBean(in MyIpcBean bean);
}
