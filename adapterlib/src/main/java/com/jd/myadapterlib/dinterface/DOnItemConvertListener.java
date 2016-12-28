package com.jd.myadapterlib.dinterface;

import com.jd.myadapterlib.delegate.RecyViewHolder;

public interface DOnItemConvertListener<T> {
    /**
     * @param viewHolder recyclerview viewholder
     * @param item Object data type
     * @param position click position
     */
    void itemConvert(RecyViewHolder viewHolder, T item, int position);
}
