package com.jd.jarvisdemonim.ui.adapter;

import android.content.Context;

import com.jd.jarvisdemonim.ui.delegate.OneDelagete;
import com.jd.jarvisdemonim.ui.delegate.ThreeDelagete;
import com.jd.jarvisdemonim.ui.delegate.TwoDelagete;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/19 0019 51
 * Name:
 * OverView:
 * Usage:
 */
public class TestAdapter<T> extends RecyMultiItemTypeAdapter<T> {

    public TestAdapter(Context context, List<T> datas) {
        super(context, datas);
        addItemViewDelegate(new OneDelagete());
        addItemViewDelegate(new TwoDelagete());
        addItemViewDelegate(new ThreeDelagete());
    }
}
