package com.jd.jarvisdemonim.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/24 0024
 * Name:
 * OverView:
 * Usage:
 */

public class XRecyAdapter extends RecyCommonAdapter {
    public XRecyAdapter(RecyclerView mRecycler, int layoutId, List datas) {
        super(mRecycler, layoutId, datas);
    }

    @Override
    protected void convert(RecyViewHolder viewHolder, Object item, int position) {
        if (item instanceof String) {
            String str = (String) item;
            viewHolder.setText(R.id.txt_two, str);
        }
    }
}
