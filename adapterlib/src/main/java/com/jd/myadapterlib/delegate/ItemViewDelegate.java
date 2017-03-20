package com.jd.myadapterlib.delegate;


import android.support.v7.widget.RecyclerView;

/**
 * 代理类;用于不同类型布局的适配器;
 */
public interface ItemViewDelegate<T> {

    /**
     * 获取item的布局;
     *
     * @return
     */
    public abstract int getItemViewLayoutId();

    /**
     * 是否使用此代理类的样式显示;
     *
     * @param item
     * @param position
     * @return
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * ListView or GridView
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convert(ViewHolder holder, T t, int position);

    /**
     * RecyclerView
     *
     * @param holder   RecyclerView 使用的ViewHolder;
     * @param t        数据类型;
     * @param position 相应位置;
     */
    public abstract void convert(RecyclerView.ViewHolder holder, T t, int position);


}
