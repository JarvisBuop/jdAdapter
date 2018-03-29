package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testfragment.TabCarFragment;
import com.jd.jarvisdemonim.views.BadgeLinearLayout;
import com.jd.jdkit.badgekit.BGABadgeable;
import com.jd.jdkit.badgekit.BGADragDismissDelegate;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.reminder.ReminderItem;
import com.jd.jdkit.reminder.ReminderManager;
import com.jd.jdkit.reminder.ReminderSettings;
import com.jd.jdkit.viewskit.MyFragmentTabHost;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/6 0006
 * Name:
 * OverView:测试日历/一般app的主页切换效果/tab的消息提醒;
 * Usage:
 * 1.普通日历;
 * 2.fragmenttabhost切换和普通消息提醒;
 * 3.tab的消息提醒和tab的拖拽效果仿qq;
 */

public class NormalTextCarlendarActivity extends DBaseActivity implements ReminderManager.UnreadNumChangedCallback {
    @Bind(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;

    private List<FragmentTabItem> tabItems;

    @Override
    public int getContentViewId() {
        return R.layout.activity_carlendar_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        registerMsgUnreadInfoObserver(true);
        initTabItem();
        initTabHost();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
    }

    private void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < tabItems.size(); i++) {
            FragmentTabItem tabItem = tabItems.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            Bundle bun = new Bundle();
            bun.putInt("pos", tabItem.getPosition());
            mTabHost.addTab(tabSpec, tabItem.getmFragmentClass(), bun);

            //给Tab按钮设置背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));

            //默认选中第一个tab
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String tabIdSele = tabId;
                if (tabId.equals(tabItems.get(2).getTitleString())) {
                    ToastUtils.showToast("弹框;");//选中中间框知识弹框;
                    mTabHost.setTransfer(false);
                } else {
                    mTabHost.setTransfer(true);
                }
                for (int i = 0; i < tabItems.size(); i++) {
                    FragmentTabItem tabitem = tabItems.get(i);
                    if (tabIdSele.equals(tabitem.getTitleString())) {
                        tabitem.setChecked(true);
                        /**
                         * 参数一:提醒的数字;
                         * 参数二:直接覆盖;
                         * 参数三:提醒的是哪个tab;
                         */
                        ReminderManager.getInstance().upDateUnReadNum(i, true, 0);
                        tabitem.getmLLBadge().hiddenBadge();//清除提醒;
                    } else {
                        tabitem.setChecked(false);
                    }
                }
            }
        });
    }

    private void initTabItem() {
        tabItems = new ArrayList<>();
        ReminderManager.getInstance().setReminder(0);//唯一tag;
        ReminderManager.getInstance().setReminder(1);
        ReminderManager.getInstance().setReminder(2);
        ReminderManager.getInstance().setReminder(3);
        ReminderManager.getInstance().setReminder(4);
        tabItems.add(new FragmentTabItem(0, TabCarFragment.class, R.drawable.head_icon_1, R.drawable.head_icon_2, "推荐"));
        tabItems.add(new FragmentTabItem(1, TabCarFragment.class, R.drawable.head_icon_1, R.drawable.head_icon_4, "发现"));
        tabItems.add(new FragmentTabItem(2, TabCarFragment.class, R.drawable.head_icon_1, R.drawable.head_icon_6, ""));
        tabItems.add(new FragmentTabItem(3, TabCarFragment.class, R.drawable.head_icon_1, R.drawable.head_icon_8, "我的"));
        tabItems.add(new FragmentTabItem(4, TabCarFragment.class, R.drawable.head_icon_1, R.drawable.head_icon_10, "订阅"));
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    private class FragmentTabItem {
        private int position;//位置
        private int imageNor;//正常时图片
        private int imagePes;//按压图片
        private String title;//标题;
        private int titleRes = 0;
        private Class<? extends Fragment> mFragmentClass;

        private View mView;//item视图;
        private TextView mTxtTitle;
        private ImageView mImgMain;
        private ImageView mImgBadge;
        private TextView mTxtBadge;
        private BadgeLinearLayout mLLBadge;
        private RecyViewHolder mHolder = null;
        private int mLayoutId = R.layout.tabitem_common_app;//item布局;

        public FragmentTabItem(int position, Class<? extends Fragment> mFragmentClass, int imageNor, int imagePes, int titleRes) {
            this.mFragmentClass = mFragmentClass;
            this.imagePes = imagePes;
            this.imageNor = imageNor;
            this.titleRes = titleRes;
            this.position = position;
        }

        public FragmentTabItem(int position, Class<? extends Fragment> mFragmentClass, int imageNor, int imagePes, String title) {
            this.mFragmentClass = mFragmentClass;
            this.imagePes = imagePes;
            this.imageNor = imageNor;
            this.position = position;
            this.title = title;
        }

        public int getImageNor() {
            return imageNor;
        }

        public int getImagePes() {
            return imagePes;
        }

        public int getPosition() {
            return position;
        }

        public int getTitleRes() {
            return titleRes;
        }

        public String getTitleString() {
            if (titleRes == 0) {
                if (!TextUtils.isEmpty(title)) {
                    return title;
                }
                return "";
            }
            if (TextUtils.isEmpty(title)) {
                title = getString(titleRes);
            }
            return title;
        }

        public Class<? extends Fragment> getmFragmentClass() {
            return mFragmentClass;
        }

        /**
         * 自定义设置view,完事用getConvertView获取view
         *
         * @return
         */
        public RecyViewHolder getViewHolder() {
            if (mLayoutId == 0) {
                throw new IllegalArgumentException("widget_provider must not be zero , please init for widget_provider!");
            }
            if (this.mView == null && mLayoutId != 0) {
                this.mView = getLayoutInflater().inflate(mLayoutId, null);
                mHolder = RecyViewHolder.createViewHolder(mContext, mView);
            }
            return mHolder;
        }

        public BadgeLinearLayout getmLLBadge() {
            if (mLLBadge != null) {
                return mLLBadge;
            } else {
                return null;
            }
        }

        /**
         * 默认布局,设置为无badge;
         *
         * @return
         */
        public View getView() {
            if (mLayoutId == 0) {
                throw new IllegalArgumentException("widget_provider must not be zero , please init for widget_provider!");
            }
            if (this.mView == null && mLayoutId != 0) {
                this.mView = getLayoutInflater().inflate(mLayoutId, null);
                this.mImgMain = (ImageView) this.mView.findViewById(R.id.tab_iv_image);
                this.mTxtTitle = (TextView) this.mView.findViewById(R.id.tab_tv_text);
                this.mImgBadge = (ImageView) this.mView.findViewById(R.id.tab_iv_badge);
                this.mTxtBadge = (TextView) this.mView.findViewById(R.id.tab_tv_badge);
                this.mLLBadge = (BadgeLinearLayout) this.mView.findViewById(R.id.ll_badge);


                if (TextUtils.isEmpty(getTitleString())) {
                    this.mTxtTitle.setVisibility(View.GONE);
                } else {
                    this.mTxtTitle.setVisibility(View.VISIBLE);
                    this.mTxtTitle.setText(getTitleString());
                }
                this.mImgMain.setImageResource(imageNor);
                mImgBadge.setVisibility(View.GONE);
                mTxtBadge.setVisibility(View.GONE);
            }
            return this.mView;
        }

        public void setBadgeIcon(boolean isShow) {
            if (mImgBadge != null) {
                if (isShow) {
                    mImgBadge.setVisibility(View.VISIBLE);
                } else {
                    mImgBadge.setVisibility(View.GONE);
                }
            }
        }

        public void setBadgeTxt(boolean isShow, int num) {
            if (mTxtBadge != null) {
                if (isShow) {
                    if (num > 0 && num <= ReminderSettings.MAX_UNREAD_SHOW_NUMBER) {
                        mTxtBadge.setVisibility(View.VISIBLE);
                        mTxtBadge.setText(String.valueOf(num));
                    } else if (num > ReminderSettings.MAX_UNREAD_SHOW_NUMBER) {
                        mTxtBadge.setVisibility(View.VISIBLE);
                        mTxtBadge.setText(ReminderSettings.MAX_UNREAD_SHOW_NUMBER + "+");
                    } else {
                        mTxtBadge.setVisibility(View.GONE);
                    }
                } else {
                    mTxtBadge.setVisibility(View.GONE);
                }
            }
        }


        public void setChecked(boolean isChecked) {
            if (mImgMain != null) {
                if (isChecked) {
                    mImgMain.setImageResource(imagePes);
                } else {
                    mImgMain.setImageResource(imageNor);
                }
            }
            if (mTxtTitle != null && !TextUtils.isEmpty(getTitleString())) {
                if (isChecked) {
                    mTxtTitle.setTextColor(getResources().getColor(R.color.orange_alpha));
                } else {
                    mTxtTitle.setTextColor(getResources().getColor(R.color.gray_tabtxt));
                }
            }
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

    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        int id = item.getId();
        LogUtil.i("jarvisclick", item.getUnread() + "  read");
        //获得相应的对象,改变其数据;
        FragmentTabItem fragmentTabItem = tabItems.get(id);
        BadgeLinearLayout badgeLinearLayout = fragmentTabItem.getmLLBadge();
        if (item.getUnread() > 0 && item.getUnread() <= ReminderSettings.MAX_UNREAD_SHOW_NUMBER) {
            badgeLinearLayout.showTextBadge(String.valueOf(item.getUnread()));
        } else if (item.getUnread() > ReminderSettings.MAX_UNREAD_SHOW_NUMBER) {
            badgeLinearLayout.showTextBadge("99+");
        } else {
            badgeLinearLayout.hiddenBadge();
        }
        badgeLinearLayout.setDragDismissDelegage(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                ToastUtils.showToast("消息单选按钮徽章拖动消失");
            }
        });
        if (id == 1) {
            fragmentTabItem.setBadgeTxt(true, item.getUnread());
        }
    }
}
