package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.SocketBean;
import com.jd.jarvisdemonim.service.UdpSocketService;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_CLIENT;
import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_SERVICE;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/14 0014
 * Name:
 * OverView: 测试socket的通信,需要网络权限;
 * Usage: 此处模拟客户端;
 * 注意:必须设置自动刷新,服务端才可返回数据;
 */

public class NormalTestSocketUDPActivity extends DBaseActivity {
    private String TAG = "SocketTestService1";
    @Bind(R.id.recycler)
    RecyclerView mrecycler;
    @Bind(R.id.edt_input)
    EditText edtText;
    List<SocketBean> mDatas;
    private RecyCommonAdapter recyCommonAdapter;

    private int port = 8081;
    InetAddress local = null;
    private DatagramPacket dp;
    private DatagramSocket ds;
    private ArrayList<String> list;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UdpSocketService.MARK:
                    Bundle data = msg.getData();
                    list = data.getStringArrayList("list");
                    mDatas.clear();
                    for (int i = 0; i < list.size(); i++) {
                        SocketBean sb = new SocketBean(list.get(i), MESSAGER_HELLO_SERVICE);
                        mDatas.add(sb);
                    }
                    recyCommonAdapter.setDatas(mDatas);
                    break;
            }
        }
    };
    private Messenger clientMessenger = new Messenger(mHandler);

    private Messenger serviceMessenger = null;


    @Override
    public int getContentViewId() {
        return R.layout.activity_socket;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
        mDatas = new ArrayList<>();
        mrecycler.setLayoutManager(new LinearLayoutManager(mContext));
        recyCommonAdapter = new RecyCommonAdapter(mrecycler, R.layout.item_left, mDatas) {
            @Override
            protected void convert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof SocketBean) {
                    SocketBean bean = (SocketBean) item;
                    TextView view = viewHolder.getView(R.id.txt_title);
                    if (bean.getMark() == MESSAGER_HELLO_SERVICE) {
                        view.setGravity(Gravity.LEFT);
                        view.setText("服务端(主App):" + "id" + bean.getId() + "说:" + bean.getContent());
                    } else if (bean.getMark() == MESSAGER_HELLO_CLIENT) {
                        view.setGravity(Gravity.RIGHT);
                        view.setText("客户端(副App):" + "id" + bean.getId() + "说:" + bean.getContent());
                    }
                }
            }
        };
        mrecycler.setAdapter(recyCommonAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Intent intent = new Intent(mContext, UdpSocketService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @OnClick({R.id.btn_lianjie, R.id.btn_send})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_lianjie:
                ((Button) findViewById(R.id.btn_lianjie)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //下划线
                ((Button) findViewById(R.id.btn_lianjie)).getPaint().setAntiAlias(true);//抗锯齿
                //查看
                if (serviceMessenger != null) {
                    Message msg = Message.obtain();
                    msg.what = UdpSocketService.MARK;
                    msg.replyTo = clientMessenger;
                    try {
                        serviceMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendMsg();
                    }
                }).start();
                break;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(TAG, "测试socket的服务连接");
            if (service != null) {
                serviceMessenger = new Messenger(service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(TAG, "测试socket的服务断开");
        }
    };

    //通过socket发送消息;发送UDP信息;
    private void sendMsg() {
        if (TextUtils.isEmpty(edtText.getText().toString())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("kong");
                }
            });
            return;
        }
        byte[] test = edtText.getText().toString().getBytes();
        try {
            local = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (local == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("ip 为空");
                }
            });
            return;
        }
        dp = new DatagramPacket(test, test.length, local, port);

        /**
         * 发送的udp socket 最好不用写端口号,它会自动找可用的端口;
         */
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (ds == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("ds为空");
                }
            });
            return;
        }
        if (dp == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("Dp为空");
                }
            });
            return;
        }
        try {
            ds.send(dp);
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
