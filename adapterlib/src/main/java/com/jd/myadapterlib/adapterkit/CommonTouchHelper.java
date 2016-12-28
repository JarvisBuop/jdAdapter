package com.jd.myadapterlib.adapterkit;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/22 0022
 * Name:
 * OverView: 自定义拖拽功能的回调;
 * Usage:
 */
public class CommonTouchHelper<T> extends ItemTouchHelper.Callback {
    public static final float ALPHA_FULL = 1.0f;
    RecyclerView.Adapter mAdapter;
    List<T> mDatas;

    public CommonTouchHelper(RecyclerView.Adapter mAdapter, List<T> mDatas) {
        this.mDatas = mDatas;
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        if (mAdapter != null && mDatas != null) {
            mDatas.add(target.getAdapterPosition(), mDatas.remove(viewHolder.getAdapterPosition()));
            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mAdapter != null && mDatas != null) {
            mDatas.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }

    /**
     * UNNECESSARY;
     */
    //是否长按触发;
    @Override
    public boolean isLongPressDragEnabled() {
//            return true;
        return false;
    }

    //是否滑动删除;
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    //设置子类滑动透明度变化;
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;
            float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
            ViewCompat.setAlpha(viewHolder.itemView, alpha);
        }
    }

    //可记录拖拽的item;
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setSelected(true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //处理拖拽过的item;
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
        viewHolder.itemView.setSelected(false);
    }
}
