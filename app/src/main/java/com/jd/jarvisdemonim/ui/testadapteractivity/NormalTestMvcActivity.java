package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.adapter.MultiListAdapter;
import com.jd.jarvisdemonim.ui.model.ContentBean;
import com.jd.jarvisdemonim.ui.controller.MultiListImpl;
import com.jd.jarvisdemonim.ui.controller.MultiListModel;
import com.jd.jarvisdemonim.ui.model.TitleBean;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/3 0003
 * Name:
 * OverView: 多分类mvc 测试;
 * Usage: 开始测试,写了3个抽象方法;
 */

public class NormalTestMvcActivity extends BaseActivity {

    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Bind(R.id.btn_retry)
    Button btnRetry;
    MultiListAdapter multiListAdapter;
    GridLayoutManager gridLayoutManager;
    private int spanCount;
    List<Object> beans;
    private MultiListModel multiListModel;
//    private ModelImpl testImpl;

    @Override
    public int getContentViewId() {
        return R.layout.activity_multi_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
        multiListModel = new MultiListImpl();
//        testImpl = new RegisModelImpl();
        spanCount = 3;
        gridLayoutManager = new GridLayoutManager(mContext, spanCount, LinearLayoutManager.VERTICAL, false);
        //模拟数据获取失败
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recycler.setLayoutManager(gridLayoutManager);
        multiListAdapter = new MultiListAdapter(mContext, beans);
        recycler.setAdapter(multiListAdapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (beans.get(position) instanceof TitleBean) ? spanCount : 1;
            }
        });
        multiListAdapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case R.id.tv_more:
                        ToastUtils.showToast(((TitleBean) beans.get(pos)).getName());
                        multiListAdapter.setDatas(multiListModel.clearDatas());
                        break;
                    case R.id.tv_content:
                        ToastUtils.showToast(((ContentBean) beans.get(pos)).getContent());
                        break;
                }
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                beans = multiListModel.LoadMoreDatas();
                multiListAdapter.setDatas(beans);
                swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @OnClick(R.id.btn_retry)
    public void onClick() {
        beans = multiListModel.getMultiListBeans();
        btnRetry.setVisibility(View.GONE);
        multiListAdapter.setDatas(beans);
    }

}
