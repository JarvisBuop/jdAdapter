package com.jd.myadapterlib;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView:使用于加载更多的适配器,多布局或单布局都可;
 * Usage: 特别注意外层是scrollview的时候,设置加载更多没有作用;
 */

public abstract class RecyLoadAdapter<T> extends RecyMultiItemTypeAdapter<T> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
    protected Context mContext;
    protected RecyclerView mRecyclerView;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mLayoutId;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private boolean isEnable = false;
    private boolean isCompleted = false;

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0 && !isCompleted;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= super.getItemCount());//应该使用父类中的0-9,这里适配器数目为10,父类中的为9;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;//添加了一个布局,且不需代理;
        } else {
            return super.getItemViewType(position);
        }
    }

    /**
     * 单类型,convert使用接口;
     *
     * @param mRecycler
     * @param layoutId
     */
    public RecyLoadAdapter(RecyclerView mRecycler, final int layoutId) {
        super(mRecycler.getContext());
        mRecyclerView = mRecycler;
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
            public void convert(RecyclerView.ViewHolder holder, T t, int position) {
                if (mListener != null) {
                    if (holder instanceof RecyViewHolder)
                        mListener.itemConvert((RecyViewHolder) holder, t, position);
                }
            }
        });
    }

    private DOnItemConvertListener mListener;

    /**
     * 单类型需要设置
     *
     * @param listener
     */
    public void setDOnItemConvertListener(DOnItemConvertListener listener) {
        this.mListener = listener;
    }

    /**
     * 用于多类型,convert在代理类中;
     *
     * @param mRecycler
     * @param datas
     */
    public RecyLoadAdapter(RecyclerView mRecycler, List<T> datas) {
        super(mRecycler.getContext(), datas);
        this.mContext = mRecycler.getContext();
        mRecyclerView = mRecycler;
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = datas;
    }

    @Override
    public RecyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            RecyViewHolder holder;
            if (mLoadMoreView != null) {
                holder = RecyViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = RecyViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        //设置加载更多的适配器只能是recyviewholder可以用;
        return (RecyViewHolder) super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyViewHolder) {
            if (isShowLoadMore(position)) {
                if (mOnLoadMoreListener != null) {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnLoadMoreListener.onLoadMoreRequested();
                        }
                    });
                }
                return;
            }
            super.onBindViewHolder(holder, position);
        } else {
            throw new ClassCastException("holder can not convert to RecyViewHolder");
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isShowLoadMore(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public RecyLoadAdapter setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public RecyLoadAdapter setLoadMoreView(boolean isdefault) {
        isEnable = isdefault;
        if (isEnable) {
            mLoadMoreView = LayoutInflater.from(mContext).inflate(R.layout.default_loading, mRecyclerView, false);
        } else {
            mLoadMoreView = null;
            mLoadMoreLayoutId = 0;
        }
        return this;
    }

    //    public RecyLoadAdapter setLoadMoreView(View loadMoreView) {
//        mLoadMoreView = loadMoreView;
//        return this;
//    }
//
//    public RecyLoadAdapter setLoadMoreView(int layoutId) {
//        mLoadMoreView = null;
//        mLoadMoreLayoutId = layoutId;
//        return this;
//    }

    public void setLoadCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        if (isEnable) {//可用刷新;
            if (mLoadMoreView == null) {
                throw new IllegalArgumentException("mLoadMoreView must be init");
            }
            View noMore = mLoadMoreView.findViewById(R.id.load_more_load_end_view);
            View hasMore = mLoadMoreView.findViewById(R.id.load_more_view);
            if (isCompleted) {
                hasMore.setVisibility(View.GONE);
                noMore.setVisibility(View.VISIBLE);
            } else {
                noMore.setVisibility(View.GONE);
                hasMore.setVisibility(View.VISIBLE);
            }
        } else {
            return;
        }
    }

}
