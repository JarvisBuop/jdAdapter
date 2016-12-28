package com.jd.myadapterlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

import java.util.List;

/**
 * 通用版本一般适配器;
 * 直接设置适配器;
 *
 * @param <T>
 */
public abstract class RecyCommonAdapter<T> extends RecyMultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public RecyCommonAdapter(RecyclerView mRecycler, final int layoutId, List<T> datas) {
        super(mRecycler.getContext(), datas);
        mContext = mRecycler.getContext();
        mInflater = LayoutInflater.from(mContext);
        mLayoutId = layoutId;
        mDatas = datas;

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
            }

            @Override
            public void convert(RecyViewHolder holder, T t, int position) {
                RecyCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(RecyViewHolder viewHolder, T item, int position);
}
