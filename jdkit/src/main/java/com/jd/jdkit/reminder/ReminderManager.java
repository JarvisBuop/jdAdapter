package com.jd.jdkit.reminder;

import android.util.SparseArray;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TAB红点提醒管理器
 */
public class ReminderManager {
    private final static String TAG = "jarvisReminder";

    // callback 接口回调改变数据;
    public interface UnreadNumChangedCallback {
        void onUnreadNumChanged(ReminderItem item);
    }

    // singleton
    private static ReminderManager instance;

    public static synchronized ReminderManager getInstance() {
        if (instance == null) {
            instance = new ReminderManager();
        }

        return instance;
    }

    // observers 提醒对象;
    private SparseArray<ReminderItem> items = new SparseArray<>();
    //提醒接口回调
    private List<UnreadNumChangedCallback> unreadNumChangedCallbacks = new ArrayList<>();

    private ReminderManager() {
    }

    /**
     * 设置需要监听的id(tag)
     * reminder 即是用于标志不同的提醒对象;
     * 设置的tag需要唯一,不然相同tag会同时提醒;
     */
    public void setReminder(int tag) {
        items.put(tag, new ReminderItem(tag));
        LogUtils.i(TAG, items.size() + "");
    }


    /**
     * 更新对应位置的tab;
     *
     * @param unread   修改可读数;
     * @param isCover  是否覆盖;
     * @param reminderId 相应位置;
     */
    public final void upDateUnReadNum(int unread, boolean isCover, int reminderId) {
        updateUnreadMessageNum(unread, !isCover, reminderId);
    }

    /**
     * 注册消息提醒回调;
     *
     * @param cb
     */
    public void registerUnreadNumChangedCallback(UnreadNumChangedCallback cb) {
        if (unreadNumChangedCallbacks.contains(cb)) {
            return;
        }

        unreadNumChangedCallbacks.add(cb);
    }

    /**
     * 解注册消息提醒回调
     *
     * @param cb
     */
    public void unregisterUnreadNumChangedCallback(UnreadNumChangedCallback cb) {
        if (!unreadNumChangedCallbacks.contains(cb)) {
            return;
        }

        unreadNumChangedCallbacks.remove(cb);
    }

    /**
     * 更新数据;
     *
     * @param unreadNum  添加目标数;
     * @param delta      是否叠加;是叠加,不是设置;
     * @param reminderId 提醒的id
     */
    private final void updateUnreadMessageNum(int unreadNum, boolean delta, int reminderId) {
        ReminderItem item = items.get(reminderId);
        if (item == null) {
            LogUtils.i(TAG, reminderId + " this reminder not found;");
            return;
        }

        int num = item.getUnread();

        // 增量
        if (delta) {
            num = num + unreadNum;
            if (num < 0) {
                num = 0;
            }
        } else {
            num = unreadNum;
        }

//        num = Math.min(ReminderSettings.MAX_UNREAD_SHOW_NUMBER, num);//设置提醒的最大值;

        item.setUnread(num);
        item.setIndicator(false);

        for (UnreadNumChangedCallback cb : unreadNumChangedCallbacks) {
            cb.onUnreadNumChanged(item);
        }
    }


    /**
     * 使用方法:
     * 1.初始化tab的地方先注册;
     * onCreate 注册registerMsgUnreadInfoObserver(true);
     * onDestory 解注册;
     /*private void registerMsgUnreadInfoObserver(boolean register) {
     if (register) {
     ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
     } else {
     ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
     }
     }l
     2.实现接口;
     implements ReminderManager.UnreadNumChangedCallback
     对tablayotu的修改:
     @Override public void onUnreadNumChanged(ReminderItem item) {
     TabLayout.Tab tab = mTablayout.getTabAt(item.getId());
     if (tab != null) {
     View customView = tab.getCustomView();
     ImageView viewById = (ImageView) customView.findViewById(R.id.tab_new_indicator);
     TextView txt2 = (TextView) customView.findViewById(R.id.tab_new_msg_label);
     txt2.setText(item.getUnread() + "");
     txt2.setVisibility(item.getUnread() > 0 ? View.VISIBLE : View.GONE);
     tab.setCustomView(customView);
     }
     }
     初始化tablayout时
     for (int i = 0; i < mTablayout.getTabCount(); i++) {
     TabLayout.Tab tab = mTablayout.getTabAt(i);
     ReminderManager.getInstance().setReminder(i);//设置需要的监听;
     tab.setCustomView(mAdapter.getTabView(i, R.layout.tab_layout_main));
     }
     fragmentPagerAdapter适配器需要设置自定义View布局
     mAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), this);

     设置fragmentpageradapter的参数string[]为空,设置自定义布局方法;
     自定义设置参数;
     mAdapter.setDOnItemConvertListener(new DOnItemConvertListener() {
     @Override public void itemConvert(RecyViewHolder viewHolder, Object item, int position) {
     viewHolder.setText(R.id.tab_title_label, arr[position]);
     //                viewHolder.setVisible(R.id.tab_new_indicator,true);
     }
     });
     */


}
