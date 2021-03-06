package com.jd.jarvisdemonim.ui.delegate;


import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.model.ContentBean;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/22
 * 描　　述:
 * 备　　注:
 */
public class DelegateContent implements ItemViewDelegate<Object> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_grid;
    }

    @Override
    public boolean isForViewType(Object o, int i) {
        return o instanceof ContentBean;
    }

    @Override
    public void convert(ViewHolder viewHolder, Object o, int i) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder, Object o, int position) {
        if(holder instanceof RecyViewHolder){
            RecyViewHolder viewHolder = (RecyViewHolder) holder;
            viewHolder.setText(R.id.tv_content,((ContentBean)o).getContent());
            viewHolder.setItemChildClickListener(R.id.tv_content);
        }
    }
}
