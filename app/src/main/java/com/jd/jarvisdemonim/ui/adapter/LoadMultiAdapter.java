package com.jd.jarvisdemonim.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.ui.delegate.OneDelagete;
import com.jd.jarvisdemonim.ui.delegate.TwoDelagete;
import com.jd.myadapterlib.RecyLoadAdapter;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView:多布局加载;
 * Usage:
 */

public class LoadMultiAdapter extends RecyLoadAdapter{

    public LoadMultiAdapter(RecyclerView mRecycler, int layoutId) {
        super(mRecycler, layoutId);
    }

    public LoadMultiAdapter(RecyclerView mRecycler, List datas) {
        super(mRecycler, datas);
        addItemViewDelegate(new OneDelagete());
        addItemViewDelegate(new TwoDelagete());
    }
}
