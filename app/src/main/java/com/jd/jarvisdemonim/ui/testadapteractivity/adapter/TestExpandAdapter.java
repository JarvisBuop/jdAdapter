package com.jd.jarvisdemonim.ui.testadapteractivity.adapter;

import android.content.Context;

import com.jd.jarvisdemonim.ui.testadapteractivity.delegate.ChildDelegate;
import com.jd.jarvisdemonim.ui.testadapteractivity.delegate.GroupDelegate;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:
 * Usage:
 */
public class TestExpandAdapter extends RecyMultiItemTypeAdapter {

    public TestExpandAdapter(Context context) {
        super(context);
        addItemViewDelegate(new ChildDelegate());
        addItemViewDelegate(new GroupDelegate());
    }
}
