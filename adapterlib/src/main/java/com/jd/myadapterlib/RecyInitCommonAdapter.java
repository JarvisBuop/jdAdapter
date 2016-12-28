package com.jd.myadapterlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import java.util.List;

/**
 * 先初始化,后赋值;
 * 一般适配器
 *
 * @param <T>
 */
public class RecyInitCommonAdapter<T> extends RecyMultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public RecyInitCommonAdapter(RecyclerView mRecycler, final int layoutId, List<T> datas) {
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
                if (mListener != null) {
                    mListener.itemConvert(holder, t, position);
                }
            }
        });
    }

    public RecyInitCommonAdapter(RecyclerView mRecycler, final int layoutId) {
        super(mRecycler.getContext());
        mContext = mRecycler.getContext();
        mInflater = LayoutInflater.from(mContext);
        mLayoutId = layoutId;

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
                if (mListener != null) {
                    mListener.itemConvert(holder, t, position);
                }
            }
        });
    }

    private DOnItemConvertListener mListener;

    public void setDOnItemConvertListener(DOnItemConvertListener listener) {
        this.mListener = listener;
    }

}
