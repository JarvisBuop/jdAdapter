package com.jd.jarvisdemonim.ui.delegate;

import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestGroupBean;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:
 * Usage:
 */
public class GroupDelegate implements ItemViewDelegate {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.test_item_group;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        if (item instanceof TestGroupBean) {
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
            if (o instanceof TestGroupBean) {
                TestGroupBean bean = (TestGroupBean) o;
                holder.setText(R.id.txt_test, bean.getContentTitle());
                holder.setItemChildClickListener(R.id.btn_test);
            }
        }
    }
}
