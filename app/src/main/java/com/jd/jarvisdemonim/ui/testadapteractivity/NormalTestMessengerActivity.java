package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.MessengerBean;
import com.jd.jarvisdemonim.service.MessagerService;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_CLIENT;
import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_NULL;
import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_SERVICE;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/10 0010
 * Name:
 * OverView: 测试messager的使用;此为服务端自己实现;
 * Usage:
 */

public class NormalTestMessengerActivity extends DBaseActivity {
    private String TAG = "MessagerTest1";
    @Bind(R.id.edt_msg)
    EditText mEditText;
    @Bind(R.id.btn_left)
    Button btnLeft;
    @Bind(R.id.btn_right)
    Button btnRight;
    @Bind(R.id.recycler)
    RecyclerView mRecy;
    List<Object> mDatas;
    Myadaper mAdapter;
    Messenger mMessenger;//服务的messenger;
    Messenger mClientMessenger;//本客户端的messenger

    //handler处理命令;
    public class ClientMessenger extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "client" + msg.what);
            switch (msg.what) {
                case MESSAGER_HELLO_SERVICE://服务端的发送信息;
                    mDatas.add(msg.getData().getParcelable("key"));
                    refreshDatas();
                    break;
//                case MESSAGER_HELLO_CLIENT:
//                    mDatas.add(msg.getData().getParcelable("key2"));
//                    refreshDatas();
//                    break;
                case MESSAGER_HELLO_NULL:
                    mDatas.clear();
                    Bundle data = msg.getData();
                    /**
                     * android的classLoader分为apkclassLoader(默认,设置再次为这个)和framework classloader,
                     */
                    data.setClassLoader(getClass().getClassLoader());
                    ArrayList<String> list = data.getStringArrayList("list");
                    for (int i = 0; i < list.size(); i++) {
                        String s = list.get(i);
                        MessengerBean bean = new MessengerBean();
                        String[] split = s.split("/");
                        bean.setContent(split[0]);
                        bean.setMark(Integer.parseInt(split[1]));
                        mDatas.add(bean);
                    }
                    refreshDatas();
                    break;
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(TAG, "绑定成功");
            mMessenger = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(TAG, "绑定失败");
            mMessenger = null;
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_messager_ipc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定服务;(和客户端绑定同一个服务;)
        doBindService();
    }

    private void doBindService() {
        Intent intent = new Intent(mContext, MessagerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void initVariable() {
        mDatas = new ArrayList<>();
        mClientMessenger = new Messenger(new ClientMessenger());
        mRecy.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new Myadaper(mRecy, R.layout.item_left, mDatas);
        mRecy.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMessenger != null) {
                    LogUtils.i(TAG, "" + mDatas.size());
                    if (TextUtils.isEmpty(mEditText.getText().toString())) {
                        ToastUtils.showToast("输入不能为空");
                        return;
                    }

                    Message obtain = Message.obtain();
                    obtain.what = MESSAGER_HELLO_SERVICE;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("key", new MessengerBean(mEditText.getText().toString(), MESSAGER_HELLO_SERVICE));
                    obtain.setData(bundle);
                    obtain.replyTo = mClientMessenger;//此处就是服务中调用的reply;
                    try {
                        mMessenger.send(obtain);
                    } catch (RemoteException e) {
                        ToastUtils.showToast("远程异常");
                        e.printStackTrace();
                    }
                } else {
                    doBindService();
                    ToastUtils.showToast("服务未连接,正在尝试重连...");
                }
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMessenger != null) {
                    //显示刷新列表;
                    Message obtain = Message.obtain();
                    obtain.what = MESSAGER_HELLO_NULL;
                    obtain.replyTo = mClientMessenger;
                    try {
                        mMessenger.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    doBindService();
                    ToastUtils.showToast("服务未连接,正在尝试重连...");
                }
            }
        });
    }

    private void refreshDatas() {
        mAdapter.setDatas(mDatas);
    }

    class Myadaper extends RecyCommonAdapter {
        public Myadaper(RecyclerView mRecycler, int layoutId, List datas) {
            super(mRecycler, layoutId, datas);
        }

        @Override
        protected void convert(RecyViewHolder viewHolder, Object item, int position) {
            if (item instanceof MessengerBean) {
                MessengerBean bean = (MessengerBean) item;
                TextView view = viewHolder.getView(R.id.txt_title);
                if (bean.getMark() == MESSAGER_HELLO_SERVICE) {
                    view.setGravity(Gravity.LEFT);
                    view.setText("服务端(主App):" + bean.getContent());
                } else if (bean.getMark() == MESSAGER_HELLO_CLIENT) {
                    view.setGravity(Gravity.RIGHT);
                    view.setText("客户端(副App):" + bean.getContent());
                }
            }
        }
    }
}
