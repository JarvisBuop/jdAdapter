package com.jd.myadapterlib.dinterface;

import android.view.View;

public interface DOnItemChildClickListener {
    /**
     * @param convertView  itemview
     * @param view target view
     * @param pos click position
     */
    void onItemChildClick(View convertView, View view, int pos);
}
