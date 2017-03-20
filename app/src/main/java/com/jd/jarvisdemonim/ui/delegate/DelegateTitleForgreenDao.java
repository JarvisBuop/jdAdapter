package com.jd.jarvisdemonim.ui.delegate;


import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestDemoBean;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/22
 * 描　　述:
 * 备　　注:
 */
public class DelegateTitleForgreenDao implements ItemViewDelegate<Object> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_title;
    }

    @Override
    public boolean isForViewType(Object o, int i) {
        return o instanceof TestDemoBean;
    }

    @Override
    public void convert(ViewHolder viewHolder, Object o, int i) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder1, Object o, int position) {
        if(holder1 instanceof RecyViewHolder){
            RecyViewHolder holder = (RecyViewHolder) holder1;
            if (o instanceof TestDemoBean) {
                TestDemoBean bean = (TestDemoBean) o;
                holder.setText(R.id.tv_title, bean.getName()+"/"+bean.getId());
                holder.setItemChildClickListener(R.id.tv_more);
            }
        }
    }
}
