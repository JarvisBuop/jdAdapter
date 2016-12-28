package com.jd.jarvisdemonim.ui.testadapteractivity.delegate;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestBean2;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/19 0019 44
 * Name:
 * OverView:
 * Usage:
 */
public class TwoDelagete implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.test2_item;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        if (item instanceof TestBean2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void convert(RecyViewHolder holder, Object o, int position) {
        if (o instanceof TestBean2) {
            TestBean2 bean2 = (TestBean2) o;
            holder.setText(R.id.txt_two, bean2.getContent());
            holder.setItemChildClickListener(R.id.img_one);
        }
    }
}
