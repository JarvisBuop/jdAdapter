package com.jd.jarvisdemonim.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestAnimationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBgaActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBottomNavigationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestCalendarAdapterActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestExpandActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestIndexStickyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestLoad1Activity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivityNet;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestNetWorkActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestPageActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestTabReminderActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestToolbarActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextCarlendarActivity;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.permissionkit.CheckPermissionsForAPI23;
import com.jd.jdkit.permissionkit.PermissionsActivity;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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
        mList.add("测试联网");
        mList.add("测试单布局加载更多");
        mList.add("测试多布局加载更多");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(myAdapter);
//        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDraw(c, parent, state);
//                c.drawColor(Color.WHITE);
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
                                startActivity(new Intent(mContext,NormalTestLoad1Activity.class));
                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.txt_nim, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7
            , R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15,R.id.btn16})
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
