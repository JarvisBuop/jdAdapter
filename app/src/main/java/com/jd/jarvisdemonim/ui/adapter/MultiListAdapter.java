package com.jd.jarvisdemonim.ui.adapter;

import android.content.Context;

import com.jd.jarvisdemonim.ui.delegate.DelegateContent;
import com.jd.jarvisdemonim.ui.delegate.DelegateTitle;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;

import java.util.List;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/22
 * 描　　述:
 * 备　　注:
 */
public class MultiListAdapter extends RecyMultiItemTypeAdapter<Object> {

    public MultiListAdapter(Context context, List<Object> datas) {
        super(context, datas);
        addItemViewDelegate(new DelegateTitle());
        addItemViewDelegate(new DelegateContent());
    }
}
