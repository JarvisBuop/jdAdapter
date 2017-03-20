package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.SocketBean;
import com.jd.jarvisdemonim.service.SocketTestService;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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

public class NormalTestSocketActivity extends DBaseActivity {
    private String TAG = "SocketTestService1";
    @Bind(R.id.recycler)
    RecyclerView mrecycler;
    @Bind(R.id.edt_input)
    EditText edtText;
    List<SocketBean> mDatas;
    private RecyCommonAdapter recyCommonAdapter;

    private Socket mSocket;
    private PrintWriter mPrintWriter;
    private BufferedReader mBufferedReader;
    private int port = 8080;
    private String destIp = "localhost";

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
        Intent intent = new Intent(mContext, SocketTestService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
//        startService(intent);
        findViewById(R.id.btn_send).setEnabled(false);//没连接,设置不能按;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        if (mSocket != null) {
            try {
                mSocket.close();
                mSocket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mSocket = null;
    }

    @OnClick({R.id.btn_lianjie, R.id.btn_send})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_lianjie:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        attachService();
                    }
                }).start();
                break;
            case R.id.btn_send:
                sendMsg();
                break;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(TAG,"测试socket的服务连接");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(TAG,"测试socket的服务断开");
        }
    };

    //通过socket发送消息;
    private void sendMsg() {
        if (TextUtils.isEmpty(edtText.getText().toString())) {
            ToastUtils.showToast("空");
            return;
        }
        SocketBean socketBean = new SocketBean(edtText.getText().toString(), MESSAGER_HELLO_CLIENT);
        mHandler.obtainMessage(SOCKET_SEND, socketBean).sendToTarget();
        mPrintWriter.println(edtText.getText().toString());
    }

    //建立tcp socket连接;
    private void attachService() {
        while (mSocket == null) {
            //向服务端发送信息初始化;
            try {
                mSocket = new Socket(destIp, port);
                //需要设置自动刷新;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),true);
                mHandler.sendEmptyMessage(SOCKET_CAN_SEND);
            } catch (IOException e) {
                SystemClock.sleep(1000);//防止超时重连机制太快;
                ToastUtils.showToast("正在重试...");
                e.printStackTrace();
            }

            //发送后接受服务端回传的数据;
            try {
                mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                while (!NormalTestSocketActivity.this.isFinishing()) {//一致监听服务器的刷新数据
                    String s = mBufferedReader.readLine();
                    LogUtils.i(TAG,"服务端回传数据:"+s);
                    if (s != null) {
                        LogUtils.i("jarvistest",s+"");
                        SocketBean socketBean = new SocketBean(s, MESSAGER_HELLO_SERVICE);
                        Message message = mHandler.obtainMessage(SOCKET_BIND, socketBean);
                        message.sendToTarget();
                    }
                }
                mBufferedReader.close();
                mPrintWriter.close();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private static final int SOCKET_BIND = 0;
    private static final int SOCKET_SEND = 1;
    private static final int SOCKET_CAN_SEND = 2;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOCKET_BIND://绑定的,主要刷新的是服务端的数据;
                    SocketBean sb = (SocketBean) msg.obj;
                    LogUtils.i(TAG, sb.toString());
                    mDatas.add(sb);
                    recyCommonAdapter.setDatas(mDatas);
                    break;
                case SOCKET_SEND://发送的,主要刷新客户端自己的数据;
                    SocketBean sb2 = (SocketBean) msg.obj;
                    LogUtils.i(TAG, sb2.toString());
                    mDatas.add(sb2);
                    recyCommonAdapter.setDatas(mDatas);
                    break;
                case SOCKET_CAN_SEND:
                    findViewById(R.id.btn_send).setEnabled(true);
                    ToastUtils.showToast("socket 已连接");
                    break;
            }
        }
    };

}
