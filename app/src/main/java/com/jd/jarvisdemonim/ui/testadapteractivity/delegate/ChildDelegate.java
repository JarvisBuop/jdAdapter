package com.jd.jarvisdemonim.ui.testadapteractivity.delegate;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestChildBean;
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
public class ChildDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.test_item_child;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        if(item instanceof TestChildBean){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void convert(RecyViewHolder holder, Object o, int position) {
        if(o instanceof TestChildBean){
            TestChildBean bean = (TestChildBean) o;
            holder.setText(R.id.txt_one,bean.getName());
            holder.setText(R.id.txt_two,bean.getContent());
            holder.setItemChildClickListener(R.id.txt_two);
        }
    }
}
