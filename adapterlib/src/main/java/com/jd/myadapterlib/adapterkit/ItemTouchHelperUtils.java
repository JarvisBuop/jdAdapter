package com.jd.myadapterlib.adapterkit;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/22 0022
 * Name:
 * OverView: itemtouch的触摸工具类;
 * Usage:
 */
public class ItemTouchHelperUtils {
    private static ItemTouchHelper itemTouchHelper;

    private ItemTouchHelperUtils() {
    }

    public static void InitHelper(RecyclerView mRecycler, ItemTouchHelper.Callback callback) {
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecycler);
    }

    public static void setTargetView(View view, final RecyclerView.ViewHolder viewHolder) {
        if (view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper.startDrag(viewHolder);
                    }
                    return false;
                }
            });
        }

    }
}
