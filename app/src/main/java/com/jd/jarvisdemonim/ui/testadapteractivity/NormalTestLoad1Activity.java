package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testadapteractivity.adapter.LoadMultiAdapter;
import com.jd.jarvisdemonim.ui.testadapteractivity.model.LoadMultiImpl;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.jd.myadapterlib.RecyLoadAdapter;

import java.util.List;

import butterknife.Bind;
import okhttp3.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView: 加载更多的测试;使用的多布局;
 * Usage:
 */

public class NormalTestLoad1Activity extends DBaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    List<Object> beans;//列表初始化在加载类中;
    LoadMultiImpl mPost;
    LoadMultiAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.test1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new LoadMultiAdapter(mRecycler, null);
    }

    @Override
    protected void initVariable() {
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mPost = new LoadMultiImpl();//联网加载类;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAdapter.setLoadMoreView(true);
        mAdapter.setOnLoadMoreListener(new RecyLoadAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                beans = mPost.loadMore();
                LogUtils.i("jarvis8", "loading loading loading" + beans.size());
                mAdapter.setDatas(beans);
            }
        });

        mPost.getModel(new IOnHttpListener() {
            @Override
            public void onSuccess(Object o) {
                if (o instanceof List) {
                    beans = (List<Object>) o;
                    LogUtils.i("jarvis8length", beans.size() + "");
                    mAdapter.setDatas(beans);
                }
            }

            @Override
            public void onError(Response response, Exception e) {

            }

            @Override
            public void onCache(Object o) {

            }

            @Override
            public void onAfter() {

            }

            @Override
            public void onBefore() {

            }
        }, null);
    }


}