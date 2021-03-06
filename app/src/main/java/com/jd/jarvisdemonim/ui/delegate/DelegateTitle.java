package com.jd.jarvisdemonim.ui.delegate;


import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.model.TitleBean;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/22
 * 描　　述:
 * 备　　注:
 */
public class DelegateTitle implements ItemViewDelegate<Object> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_title;
    }

    @Override
    public boolean isForViewType(Object o, int i) {
        return o instanceof TitleBean;
    }

    @Override
    public void convert(ViewHolder viewHolder, Object o, int i) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder, Object o, int position) {
        if(holder instanceof RecyViewHolder){
            RecyViewHolder holder1 = (RecyViewHolder) holder;
            holder1.setText(R.id.tv_title,((TitleBean)o).getName());
            holder1.setItemChildClickListener(R.id.tv_more);
        }
    }
}
