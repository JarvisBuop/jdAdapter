package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.RetrofitTestBean;
import com.jd.jarvisdemonim.ui.presenter.MvpPresenterImpl;
import com.jd.jarvisdemonim.ui.presenter.NTestMvpView;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.myadapterlib.RecyLoadAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/21 0021
 * Name:
 * OverView: mvp模式
 * Usage: model层,view层,presenter层;
 */

public class NormalTestMvpActivity extends DBaseActivity implements NTestMvpView, DOnItemConvertListener,
        RecyLoadAdapter.OnLoadMoreListener, DOnItemChildClickListener, View.OnClickListener {
    @Bind(R.id.edt_click)
    EditText edtClick;
    @Bind(R.id.btn_click)
    Button btnClick;
    @Bind(R.id.txt_click)
    TextView txtClick;
    @Bind(R.id.recycler_load)
    RecyclerView mRecycler;
    MvpLoadAdapter mAdapter;
    MvpPresenterImpl mPresenter;//presenter 用于处理逻辑,将model层和view层分离;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new MvpPresenterImpl(this);
        mAdapter = new MvpLoadAdapter(mRecycler, R.layout.test2_item);
    }

    @Override
    protected void initVariable() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(true);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAdapter.setDOnItemConvertListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemChildClickListener(this);
        btnClick.setOnClickListener(this);
        txtClick.requestFocus();
    }

    /**
     * View interface
     */
    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void dismissProgress() {
        dismissDialog();
    }

    @Override
    public void adapterNotify(List mlist) {
        if (mlist != null) {
            mAdapter.setDatas(mlist);
        }
    }

    @Override
    public void adapterNotify(Object o) {
        if (o instanceof RetrofitTestBean) {
            RetrofitTestBean bean = (RetrofitTestBean) o;
            txtClick.setText(bean.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    /**
     * 适配器的方法;
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void itemConvert(RecyViewHolder viewHolder, Object item, int position) {
        if (item instanceof String) {
            String str = (String) item;
            viewHolder.setText(R.id.txt_two, str);
            viewHolder.setItemChildClickListener(R.id.bg_ll);
        }
    }

    @Override
    public void onItemChildClick(View convertView, View view, int pos) {
        switch (view.getId()) {
            case R.id.bg_ll:
                LogUtils.i("Item click" + pos);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(edtClick.getText().toString())) {
            btnClick.setText(edtClick.getText().toString());
        } else {
            edtClick.setError("请输入数据");
        }
    }

    public static class MvpLoadAdapter extends RecyLoadAdapter {
        public MvpLoadAdapter(RecyclerView mRecycler, int layoutId) {
            super(mRecycler, layoutId);
        }
    }
}
