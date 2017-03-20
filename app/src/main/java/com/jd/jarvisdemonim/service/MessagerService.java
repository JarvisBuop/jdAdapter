package com.jd.jarvisdemonim.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jd.jarvisdemonim.entity.MessengerBean;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.ArrayList;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/10 0010
 * Name:
 * OverView: 测试messenger的使用,服务;
 * Usage:messenger只能传递基本数据类型(bundle支持的);
 */

public class MessagerService extends Service {
    private String TAG = "MessagerTest2";
    public static final int MESSAGER_HELLO_SERVICE = 0;
    public static final int MESSAGER_HELLO_CLIENT = 1;
    public static final int MESSAGER_HELLO_NULL = -1;
    ArrayList<String> mTotalDatas;
    Messenger mMessenger = new Messenger(new MessagerHandler());

    private class MessagerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "client" + msg.what);
            switch (msg.what) {
                case MESSAGER_HELLO_SERVICE://服务端的信息处理,这个可以用自己的数据类型;
                    storageData(msg, "key");
                    //接受从客户端传来的消息
                    MessengerBean bean = msg.getData().getParcelable("key");

                    LogUtils.i(TAG, "handler service receiver1" + bean == null ? null : bean.toString());
                    //发送数据给客户端;
                    try {
                        //不能用原来的message;IllegalStateException
                        Message msgReply = Message.obtain();
                        msgReply.what = msg.what;
                        Bundle data = msg.getData();
                        msgReply.setData(data);
                        msg.replyTo.send(msgReply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                /**
                 * 另一个app传过来的数据;
                 * 这里有个问题,如果客户端传对象过来,拿不到构造对象的类(因为只能传递bundle支持的基本数据类型;);
                 * 所有只能传递参数了来构建一个新类来自己显示,因为不能得到并使用对方app的类;
                 * 不同包名导致的问题;保留一个脑洞(已解决bundle支持);
                 */
                case MESSAGER_HELLO_CLIENT://直接回给客户端,基本数据类型(bundle),引用的数据类型找不到类classnotfoundexception;
                    storageData(msg, "key2");
                    //接受从客户端传来的消息
                    //发送数据给客户端,完成列表的刷新;
                    LogUtils.i(TAG, "handler client receiver2");
                    try {
                        Message msgReply = Message.obtain();
                        msgReply.what = msg.what;
                        //这里没有另一个app的类,不获取对象,不然报BadParcelableException: ClassNotFoundException
                        Bundle bundle = msg.getData();
                        msgReply.setData(bundle);
                        msg.replyTo.send(msgReply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MESSAGER_HELLO_NULL://获取服务端回复的信息和客户端发送的信息,所有数据;
                    Message msgReply = Message.obtain();
                    msgReply.what = msg.what;
                    Bundle bundle = new Bundle();
                    //处理由于另一个app中没有服务端对象类的情况,将服务端的对象类改成string
                    bundle.putStringArrayList("list",mTotalDatas);
                    msgReply.setData(bundle);
                    try {
                        msg.replyTo.send(msgReply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    //存储所有数据;
    private void storageData(Message msg, String key) {
        //存另一个app的值;
        if(key.equals("key2")){
            Bundle data = msg.getData();
            data.setClassLoader(getClass().getClassLoader());
            LogUtils.i("test1111",data.getString("string")+"/"+data.getInt("int"));
            mTotalDatas.add(data.getString("string")+"/"+data.getInt("int"));
            return;
        }
        Bundle data = msg.getData();
        MessengerBean parcelable = data.getParcelable(key);
        mTotalDatas.add(parcelable.getContent()+"/"+parcelable.getMark());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTotalDatas = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mMessenger != null) {
            return mMessenger.getBinder();
        }
        return null;
    }
}
