package com.jd.jarvisdemonim.ui.adapter;

import android.content.Context;

import com.jd.jarvisdemonim.ui.delegate.DelegateContentForGreenDao;
import com.jd.jarvisdemonim.ui.delegate.DelegateTitleForgreenDao;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/15 0015
 * Name:
 * OverView:
 * Usage:
 */

public class GreenDaoMultiAdapter extends RecyMultiItemTypeAdapter {
    public GreenDaoMultiAdapter(Context context) {
        super(context);
        addItemViewDelegate(new DelegateTitleForgreenDao());
        addItemViewDelegate(new DelegateContentForGreenDao());
    }
}
