package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.system.NetworkUtil;
import com.jd.jdkit.elementkit.utils.system.ToastyUtils;
import com.jd.jdkit.reminder.ReminderItem;
import com.jd.jdkit.reminder.ReminderManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView: 即使网络的通知;
 * Usage:
 */

public class NormalTestNetWorkActivity extends DBaseActivity implements ReminderManager.UnreadNumChangedCallback {
    public static int RemindTag = 1;
    @Bind(R.id.txt_network)
    TextView txtNet;
    private MyNetWorkReceiver myNetWorkReceiver;

    @Override
    public int getContentViewId() {
        return R.layout.activity_network_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        myNetWorkReceiver = new MyNetWorkReceiver();
        registerMsgUnreadInfoObserver(true);//注册提醒观察者;
        registerNetWork(true);//注册网络广播;
    }

    @Override
    protected void initVariable() {
        ReminderManager.getInstance().setReminder(RemindTag);//添加一个提醒观察者;

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        switch (item.getUnread()) {
            case 0:
                txtNet.setText("手机网络不可用");
                break;
            case 1:
                txtNet.setText("wifi连接成功"+ NetworkUtil.wifiSSID(this));
                break;
            case 2:
                txtNet.setText("未知");
                break;
            case 3:
                txtNet.setText("WWAN");
                break;
            case 4:
                txtNet.setText("2G");
                break;
            case 5:
                txtNet.setText("3G");
                break;
            case 6:
                txtNet.setText("4G");
                break;
        }
    }

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 注册网络广播;
     */
    private void registerNetWork(boolean register) {
        if (register) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(myNetWorkReceiver, filter);
        } else {
            mContext.unregisterReceiver(myNetWorkReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
        registerNetWork(false);
    }

    @OnClick({R.id.toast1, R.id.toast2, R.id.toast3, R.id.toast4, R.id.toast5})
    void click(View v) {
        switch (v.getId()) {
            case R.id.toast1:
                ToastyUtils.showInfo("INFO");
                break;
            case R.id.toast2:
                ToastyUtils.showErr("ERR");
                break;
            case R.id.toast3:
                ToastyUtils.showWarning("WARNING");
                break;
            case R.id.toast4:
                ToastyUtils.showNormal("NORMAL");
                break;
            case R.id.toast5:
                ToastyUtils.showSuccess("SUCCESS");
                break;
        }
    }
}
