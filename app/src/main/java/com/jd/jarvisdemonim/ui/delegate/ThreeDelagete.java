package com.jd.jarvisdemonim.ui.delegate;

import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestBean3;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/22 0022 03
 * Name:
 * OverView:
 * Usage:
 */
public class ThreeDelagete implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.test3_item;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        if (item instanceof TestBean3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder1, Object o, int position) {
        if(holder1 instanceof RecyViewHolder){
            RecyViewHolder holder = (RecyViewHolder) holder1;
            if(o instanceof TestBean3){
                TestBean3 bean = (TestBean3) o;
                holder.setText(R.id.txtthree,bean.getContent());
                holder.setText(R.id.txtthree2,bean.getContent2());
            }
        }
    }

}
