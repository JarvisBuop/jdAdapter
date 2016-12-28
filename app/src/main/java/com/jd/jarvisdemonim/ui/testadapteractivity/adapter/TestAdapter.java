package com.jd.jarvisdemonim.ui.testadapteractivity.adapter;

import android.content.Context;

import com.jd.jarvisdemonim.ui.testadapteractivity.delegate.OneDelagete;
import com.jd.jarvisdemonim.ui.testadapteractivity.delegate.ThreeDelagete;
import com.jd.jarvisdemonim.ui.testadapteractivity.delegate.TwoDelagete;
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
