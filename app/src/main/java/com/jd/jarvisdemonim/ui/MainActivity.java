package com.jd.jarvisdemonim.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.ExcellectViewActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestAidlActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestAnimationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestApkDlActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBgaActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBinderPoolActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBottomNavigationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestCalendarAdapterActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestContentProviderActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestExpandActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestGreenDaoActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestIndexStickyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestJUnitActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestLazyFragmentActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestLoad1Activity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMDActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMessengerActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMultiGreenDaoActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivityNet;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvpActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestNetWorkActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestNotifcationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestPageActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestRemoteViewActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestRetrofitActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestRichTextActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestScrollConflictActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestSenserActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestSocketActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestSocketUDPActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestTabReminderActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestToolbarActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestXRecyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextCarlendarActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView.NormalTestCustomTouchActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView.NormalTestCustomViewActivity;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.permissionkit.CheckPermissionsForAPI23;
import com.jd.jdkit.permissionkit.PermissionsActivity;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestApkDlActivity.EXTRA_DEX_PATH;

/**
 * Nim Demo 的使用;
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.recylcer_bottom)
    RecyclerView mRecy;
    LinearLayoutManager manager;
    List<String> mList;
    private MyAdapter myAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        manager = new LinearLayoutManager(mContext);
        myAdapter = new MyAdapter(mRecy, android.R.layout.simple_list_item_1, mList);
    }

    @Override
    protected void initVariable() {
        doLoadData();
    }

    private void doLoadData() {
        mList.add("测试联网和Toasty");
        mList.add("测试单布局加载更多");
        mList.add("测试fragment的懒加载");
        mList.add("测试GreenDao的使用");
        mList.add("测试GreenDao多布局的使用");
        mList.add("测试Retrofit下载数据");
        mList.add("测试MVP模式");
        mList.add("测试约束CoordernatorLayout布局及简单MD设计");
        mList.add("测试XRecyclerView,好看的刷新和加载UI");
        mList.add("测试自定义View的使用");
        mList.add("测试事件分发的过程");
        mList.add("测试IPC机制:Aidl进程间的通信,一个app启动另一个app");
        mList.add("测试IPC机制:Messager进程间的通信,实现不同app间的通信(基本数据类型)");
        mList.add("测试IPC机制:ContentProvider的数据共享");
        mList.add("测试IPC机制:Socket 的进程间通信 TCP Server");
        mList.add("测试IPC机制:Socket 的进程间通信 UDP Server");
        mList.add("测试IPC机制AIDL优化:Binder连接池,实现一个service和多个aidl;");
        mList.add("测试滑动冲突的优化,事件分发\n(暂未测试);");
        mList.add("测试插件化app,打开apk中的类");
        mList.add("测试通知栏的消息通知/remoteViews的使用/添加桌面插件/桌面悬浮框");
        mList.add("测试RemoteView实现进程间的通信和刷新不同进程的UI\n(暂未测试)");
        mList.add("测试优秀自定义控件");
        mList.add("测试单元测试(看代码)");
        mList.add("测试传感器");
        mList.add("测试富文本加载");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(myAdapter);
//        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDraw(c, parent, state);
//                c.drawColor(Color.BLUE);//需要item的布局设置背景色;
//
//            }
//
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0, 1, 0, 1);
//            }
//        });

        myAdapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case android.R.id.text1:
                        switch (pos) {
                            case 0:
                                startActivity(new Intent(mContext, NormalTestNetWorkActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(mContext, NormalTestLoad1Activity.class));
                                break;
                            case 2:
                                startActivity(new Intent(mContext, NormalTestLazyFragmentActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(mContext, NormalTestGreenDaoActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(mContext, NormalTestMultiGreenDaoActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(mContext, NormalTestRetrofitActivity.class));
                                break;
                            case 6:
                                startActivity(new Intent(mContext, NormalTestMvpActivity.class));
                                break;
                            case 7:
                                startActivity(new Intent(mContext, NormalTestMDActivity.class));
                                break;
                            case 8:
                                startActivity(new Intent(mContext, NormalTestXRecyActivity.class));
                                break;
                            case 9:
                                startActivity(new Intent(mContext, NormalTestCustomViewActivity.class));
                                break;
                            case 10:
                                startActivity(new Intent(mContext, NormalTestCustomTouchActivity.class));
                                break;
                            case 11:
                                startActivity(new Intent(mContext, NormalTestAidlActivity.class));
                                break;
                            case 12:
                                startActivity(new Intent(mContext, NormalTestMessengerActivity.class));
                                break;
                            case 13:
                                startActivity(new Intent(mContext, NormalTestContentProviderActivity.class));
                                break;
                            case 14:
                                startActivity(new Intent(mContext, NormalTestSocketActivity.class));
                                break;
                            case 15:
                                startActivity(new Intent(mContext, NormalTestSocketUDPActivity.class));
                                break;
                            case 16:
                                startActivity(new Intent(mContext, NormalTestBinderPoolActivity.class));
                                break;
                            case 17:
                                startActivity(new Intent(mContext, NormalTestScrollConflictActivity.class));
                                break;
                            case 18:
                                //调用另一个未安装的app的界面;
                                String apkName = "plugin.apk";
                                Intent intent = new Intent(mContext, NormalTestApkDlActivity.class);
                                intent.putExtra(EXTRA_DEX_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + apkName);
                                startActivity(intent);
                                break;
                            case 19:
                                startActivity(new Intent(mContext, NormalTestNotifcationActivity.class));
                                break;
                            case 20:
                                startActivity(new Intent(mContext, NormalTestRemoteViewActivity.class));
                                break;
                            case 21://自定义集合展示;
                                startActivity(new Intent(mContext, ExcellectViewActivity.class));
                                break;
                            case 22:
                                startActivity(new Intent(mContext, NormalTestJUnitActivity.class));
                                break;
                            case 23:
                                startActivity(new Intent(mContext, NormalTestSenserActivity.class));
                                break;
                            case 24:
                                startActivity(new Intent(mContext, NormalTestRichTextActivity.class));
                                break;
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.txt_nim, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7
            , R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.txt_nim:
                Intent intent0 = new Intent(this, NimActivity.class);
                startActivity(intent0);
                break;
            case R.id.btn1://baseadapter
                LogUtil.i("jarvisclick", "button");
                Intent intent = new Intent(this, NormalTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2://tuozhuai
                LogUtil.i("jarvisclick", "button");
                Intent intent2 = new Intent(this, NormalTestBgaActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn3:
                LogUtil.i("jarvisclick", "button");
                Intent intent3 = new Intent(this, NormalTestPageActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn12:
                Intent intent12 = new Intent(this, NormalTestIndexStickyActivity.class);
                startActivity(intent12);
                break;
            case R.id.btn4:
                LogUtil.i("jarvisclick", "button");
                Intent intent4 = new Intent(this, NormalTestExpandActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn5:
                LogUtil.i("jarvisclick", "button");
                Intent intent5 = new Intent(this, NormalTestStickyActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn6:
                LogUtil.i("jarvisclick", "button");
                Intent intent6 = new Intent(this, NormalTextActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn7:
                LogUtil.i("jarvisclick", "button");
                Intent intent7 = new Intent(this, NormalTestMvcActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn8:
                LogUtil.i("jarvisclick", "button");
                Intent intent8 = new Intent(this, NormalTestMvcActivityNet.class);
                startActivity(intent8);
                break;
            case R.id.btn9:
                LogUtil.i("jarvisclick", "button");
                Intent intent9 = new Intent(this, NormalTestTabReminderActivity.class);
                startActivity(intent9);
                break;
            case R.id.btn10:
                LogUtil.i("jarvisclick", "button");
                Intent intent10 = new Intent(this, NormalTextCarlendarActivity.class);
                startActivity(intent10);
                break;
            case R.id.btn11:
                LogUtil.i("jarvisclick", "button");
                Intent intent11 = new Intent(this, NormalTestBottomNavigationActivity.class);
                startActivity(intent11);
                break;
            case R.id.btn13:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    new CheckPermissionsForAPI23(this);
                    ToastUtils.showToast("android-23测试权限");
                } else {
                    ToastUtils.showToast("android-23 以下没有此功能;");
                }
                break;
            case R.id.btn14:
                LogUtil.i("jarvisclick", "button");
                Intent intent14 = new Intent(this,
                        NormalTestCalendarAdapterActivity.class);
                startActivity(intent14);
                break;
            case R.id.btn15:
                Intent intent15 = new Intent(this,
                        NormalTestToolbarActivity.class);
                startActivity(intent15);
                break;
            case R.id.btn16:
                startActivity(new Intent(this, NormalTestAnimationActivity.class));
                break;

        }
    }

    private void checkPermissionsResult(int requestCode, int resultCode) {
        //sdk23+用 如果用户拒绝赋予你所需要的权限 则直接退出app，if内方法可自行斟酌
        if (requestCode == PermissionsActivity.REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            ToastUtils.showToast("拒绝赋予权限");
        } else if (requestCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            ToastUtils.showToast("全部权限已授权");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermissionsResult(requestCode, resultCode);
    }

    private static class MyAdapter extends RecyCommonAdapter {

        public MyAdapter(RecyclerView mRecycler, int layoutId, List datas) {
            super(mRecycler, layoutId, datas);
        }

        @Override
        protected void convert(RecyViewHolder viewHolder, Object item, int position) {
            if (item instanceof String) {
                viewHolder.setText(android.R.id.text1, (String) item);
                viewHolder.setItemChildClickListener(android.R.id.text1);
            }
        }
    }
}
