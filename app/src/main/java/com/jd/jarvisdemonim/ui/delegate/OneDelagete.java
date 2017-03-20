package com.jd.jarvisdemonim.ui.delegate;

import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestBean;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/19 0019 59
 * Name:
 * OverView:
 * Usage:
 */
public class OneDelagete implements ItemViewDelegate {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.test1_item;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        if (item instanceof TestBean) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder, Object o, int position) {
        if (holder instanceof RecyViewHolder) {
            RecyViewHolder holder1 = (RecyViewHolder) holder;
            if (o instanceof TestBean) {
                TestBean bean = (TestBean) o;
                holder1.setText(R.id.txt_one, bean.getName());
                holder1.setText(R.id.txt_two, bean.getContent());
            }
        }
    }
}
