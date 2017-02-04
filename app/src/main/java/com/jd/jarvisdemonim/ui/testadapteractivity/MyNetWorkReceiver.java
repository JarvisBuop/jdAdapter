package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jd.jdkit.elementkit.utils.system.NetworkUtil;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.reminder.ReminderManager;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView:监听广播,监听网络;
 * Usage:
 */

public class MyNetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtil.isNetAvailable(context)) {
            if (!NetworkUtil.isWifi(context)) {
                switch (NetworkUtil.getNetworkTypeForLink(context)) {
                    case 0:
                        ToastUtils.showToast("未知");
                        ReminderManager.getInstance().upDateUnReadNum(2,true,NormalTestNetWorkActivity.RemindTag);
                        break;
                    case 1:
                        break;
                    case 2:
                        ToastUtils.showToast("WWAN");
                        ReminderManager.getInstance().upDateUnReadNum(3,true,NormalTestNetWorkActivity.RemindTag);
                        break;
                    case 3:
                        ToastUtils.showToast("2G");
                        ReminderManager.getInstance().upDateUnReadNum(4,true,NormalTestNetWorkActivity.RemindTag);
                        break;
                    case 4:
                        ToastUtils.showToast("3G");
                        ReminderManager.getInstance().upDateUnReadNum(5,true,NormalTestNetWorkActivity.RemindTag);
                        break;
                    case 5:
                        ToastUtils.showToast("4G");
                        ReminderManager.getInstance().upDateUnReadNum(6,true,NormalTestNetWorkActivity.RemindTag);
                        break;
                }
            } else {
                ToastUtils.showToast("wifi连接成功");
                ReminderManager.getInstance().upDateUnReadNum(1,true,NormalTestNetWorkActivity.RemindTag);
            }
        } else {
            ToastUtils.showToast("手机网络不可用");
            ReminderManager.getInstance().upDateUnReadNum(0,true,NormalTestNetWorkActivity.RemindTag);
        }
    }
}
