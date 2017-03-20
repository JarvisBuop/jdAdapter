package com.jd.myadapterlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.ViewHolder;

import java.util.List;

/**
 * 一般适配器for listview or gridview
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }

            @Override
            public void convert(RecyclerView.ViewHolder holder, T t, int position) {

            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
