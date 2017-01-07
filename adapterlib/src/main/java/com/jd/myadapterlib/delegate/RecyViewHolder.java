package com.jd.myadapterlib.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jd.myadapterlib.dinterface.DOnItemChildCheckChangeListener;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;
import com.jd.myadapterlib.dinterface.DOnItemChildLongClickListener;
import com.jd.myadapterlib.dinterface.DOnItemChildTouchListener;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/16 0016 28
 * Name:
 * OverView: recyclerview viewholder
 * Usage:
 */
public class RecyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener,View.OnTouchListener,CompoundButton.OnCheckedChangeListener {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
//    private static RecyclerView mRecyclerView;

    public RecyViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static RecyViewHolder createViewHolder(Context context, View itemView) {
        RecyViewHolder holder = new RecyViewHolder(context, itemView);
        return holder;
    }

    public static RecyViewHolder createViewHolder(Context context,
                                                  ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RecyViewHolder holder = new RecyViewHolder(context, itemView);
        return holder;
    }

//    public static RecyViewHolder createViewHolder(RecyclerView recyclerview, ViewGroup parent, int layoutId) {
//        mRecyclerView = recyclerview;
//        View itemView = LayoutInflater.from(recyclerview.getContext()).inflate(layoutId, parent, false);
//        RecyViewHolder holder = new RecyViewHolder(recyclerview.getContext(), itemView);
//        return holder;
//    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RecyViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public RecyViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public RecyViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public RecyViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecyViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecyViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public RecyViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public RecyViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RecyViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RecyViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RecyViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RecyViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public RecyViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RecyViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public RecyViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public RecyViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RecyViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RecyViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RecyViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public RecyViewHolder setOnClickListener(int viewId,
                                             View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecyViewHolder setOnTouchListener(int viewId,
                                             View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public RecyViewHolder setOnLongClickListener(int viewId,
                                                 View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 点击事件;
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        this.getView(viewId).setOnClickListener(this);
    }

    private DOnItemChildClickListener mOnItemChildClickListener;

    public void setOnItemChildClickListener(DOnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 长按事件;
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        this.getView(viewId).setOnLongClickListener(this);
    }

    private DOnItemChildLongClickListener mOnItemChildLongClickListener;

    public void setOnItemChildLongClickListener(DOnItemChildLongClickListener mOnItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = mOnItemChildLongClickListener;
    }

    /**
     * 触摸事件;
     * @param viewId
     */
    public void setItemChildTouchListener(@IdRes int viewId) {
        this.getView(viewId).setOnTouchListener(this);
    }

    private DOnItemChildTouchListener mOnItemChildTouchListener;

    public void setOnItemChildTouchListener(DOnItemChildTouchListener mOnItemChildTouchListener) {
        this.mOnItemChildTouchListener = mOnItemChildTouchListener;
    }

    /**
     * 选中事件;
     * @param viewId
     */
    public void setItemChildCheckChangeListener(@IdRes int viewId) {
        View view = this.getView(viewId);
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setOnCheckedChangeListener(this);
        }
    }

    private DOnItemChildCheckChangeListener mOnItemChildCheckChangeListener;

    public void setOnItemChildTouchListener(DOnItemChildCheckChangeListener mOnItemChildCheckChangeListener) {
        this.mOnItemChildCheckChangeListener = mOnItemChildCheckChangeListener;
    }


    @Override
    public void onClick(View v) {
        if (this.mOnItemChildClickListener != null) {
//            Log.i("jarvisclick",getAdapterPosition()+"$"+getLayoutPosition()+"#"+getPosition());
            this.mOnItemChildClickListener.onItemChildClick(this.mConvertView, v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return this.mOnItemChildLongClickListener != null ?this.mOnItemChildLongClickListener.onLongClick(v, this.getAdapterPosition()): false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.mOnItemChildTouchListener !=null ?this.mOnItemChildTouchListener.onTouch(v,event,this.getAdapterPosition()):false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(mOnItemChildCheckChangeListener !=null){
            this.mOnItemChildCheckChangeListener.onCheckedChanged(buttonView,this.getAdapterPosition(),isChecked);
        }
    }
}
