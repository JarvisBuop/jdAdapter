package com.jd.jarvisdemonim.ui.delegate;


import android.support.v7.widget.RecyclerView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.InnerDemo;
import com.jd.jarvisdemonim.entity.TestDemoBean2;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.delegate.ItemViewDelegate;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.delegate.ViewHolder;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/22
 * 描　　述:
 * 备　　注:
 */
public class DelegateContentForGreenDao implements ItemViewDelegate<Object> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_grid;
    }

    @Override
    public boolean isForViewType(Object o, int i) {
        return o instanceof TestDemoBean2;
    }

    @Override
    public void convert(ViewHolder viewHolder, Object o, int i) {

    }

    @Override
    public void convert(RecyclerView.ViewHolder holder1, Object o, int position) {
        if (holder1 instanceof RecyViewHolder) {
            RecyViewHolder holder = (RecyViewHolder) holder1;
            if (o instanceof TestDemoBean2) {
                TestDemoBean2 bean = (TestDemoBean2) o;

                InnerDemo demo = bean.getDemo();

                if (demo != null) {
                    if (demo.getTarget() != null) {
                        holder.setText(R.id.tv_content, demo.getTarget() + "");
                    } else {
                        ToastUtils.showToast("bean.getdemo.gettarget is null");
                    }
                } else {
                    ToastUtils.showToast("bean.getDemo is null");
                }
                holder.setItemChildClickListener(R.id.tv_content);
            }
        }
    }
}
