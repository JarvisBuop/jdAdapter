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

import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/14 0014
 * Name:
 * OverView:
 * Usage:
 */

public class UdpSocketService extends Service {
    private ArrayList<String> mdata;
    public static final int MARK = 0;
    private String TAG = "UdpSocket";

    private boolean isServiceConnection = true;//判断服务端是否存货;
    //模拟服务端的对客户端的回复;
    private String[] responseArray = new String[]{
            "你好!", "你是?", "你谁!", "我是人工智能", "小M", "今天天气正不错", "还有什么?"
    };

    byte[] message = new byte[80];//接受的客户端的字节大小;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MARK://回传到客户端的message;
                    Message msgClient = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("list",mdata);
                    msgClient.setData(bundle);
                    msgClient.what = MARK;
                    try {
                        msg.replyTo.send(msgClient);//这个是发送给客户端运行的;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    Messenger messenger = new Messenger(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    /**
     * 建立服务时开启socket;异步开启,可能耗时;
     */
    private class SocketRunnable implements Runnable {

        @Override
        public void run() {
            DatagramSocket datagramSocket = null;
            DatagramPacket dp = null;
            int port = 8081;
            try {
                datagramSocket = new DatagramSocket(port);
                datagramSocket.setBroadcast(true);
                dp = new DatagramPacket(message, message.length);
                LogUtils.e(TAG, "udp server sussess");
            } catch (SocketException e) {
                e.printStackTrace();
                return;
            }
            //获取客户端请求;
            while (isServiceConnection) {
                //准备接受数据;
                String str = null;
                try {
                    datagramSocket.receive(dp);
                    byte[] data = dp.getData();
                    str = new String(data).trim();
                    LogUtils.e(TAG, dp.getAddress()
                            .getHostAddress().toString()
                            + ":" + str);
                    if (str != null)
                        mdata.add(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mdata = new ArrayList<>();
        new Thread(new SocketRunnable()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceConnection = false;

    }
}
