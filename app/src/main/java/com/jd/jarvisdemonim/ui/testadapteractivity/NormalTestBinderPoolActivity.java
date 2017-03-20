package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.binder.BinderPool;
import com.jd.jarvisdemonim.binder.ITestBinder1;
import com.jd.jarvisdemonim.binder.ITestBinder2;
import com.jd.jarvisdemonim.service.BinderPoolService;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import butterknife.Bind;

import static com.jd.jarvisdemonim.binder.BinderPool.BINDER_CODE_TEST1;
import static com.jd.jarvisdemonim.binder.BinderPool.BINDER_CODE_TEST2;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/15 0015
 * Name:
 * OverView: 一个binder连接池,实现多个aidl接口和一个binder连接池,转换为需要的binder对象,然后与服务绑定;
 * Usage: 实现功能是可以优化一个aidl接口产生一个binder,连接一个service,因service占有系统资源,不能大量创建,
 * 故使用binder连接池可创建多个aidl接口连接一个service;
 */

public class NormalTestBinderPoolActivity extends DBaseActivity {
    @Bind(R.id.btn_one)
    Button btnOne;
    @Bind(R.id.btn_two)
    Button btnTwo;
    @Bind(R.id.txt_content)
    TextView txtContent;
    BinderPool mBinderPool;
    String str = "content";

    //子线程不能更改UI;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    txtContent.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_binderpool;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        oneAIDL();
                    }
                }).start();
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        twoAIDL();
                    }
                }).start();
            }
        });
    }

    public void oneAIDL() {
        mBinderPool = BinderPool.getInstance(mContext, BinderPoolService.class);
        if (mBinderPool != null) {
            LogUtils.e("content", "11111111111111111111111");
            IBinder iBinder = mBinderPool.queryBinderMethod(BINDER_CODE_TEST1);//获取需要的binder;
            ITestBinder1 iTestBinderPool1 = (ITestBinder1) ITestBinder1.asInterface(iBinder);//获取需要的aidl接口;
            StringBuilder sb = new StringBuilder();
            sb.append("原来的字符串:" + str + "\n");
            try {
                String s = iTestBinderPool1.addFunc(str);
                sb.append("运行第一个aidl接口方法一的返回值:" + s + "\n");

                String s1 = iTestBinderPool1.delFunc(s);
                sb.append("运行第一个aidl接口方法二的返回值:" + s1 + "\n");

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            handler.obtainMessage(0, sb.toString()).sendToTarget();
            LogUtils.e("content", "11111111111111111111111" + sb.toString());
        }
    }

    public void twoAIDL() {
        mBinderPool = BinderPool.getInstance(mContext, BinderPoolService.class);
        if (mBinderPool != null) {
            LogUtils.e("content", "11111111111111111111111");
            IBinder iBinder = mBinderPool.queryBinderMethod(BINDER_CODE_TEST2);
            ITestBinder2 iTestBinderPool2 = (ITestBinder2) ITestBinder2.asInterface(iBinder);
            StringBuilder sb = new StringBuilder();
            int a = 2;
            int b = 4;
            sb.append("原来的字符串:" + a + "/" + b + "\n");
            try {
                int add = iTestBinderPool2.add(a, b);
                sb.append("运行第二个aidl接口方法的返回值:" + add + "\n");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            handler.obtainMessage(0, sb.toString()).sendToTarget();
            LogUtils.e("content", "11111111111111111111111" + sb.toString());
        }
    }
}
