package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.entity.TestBean;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.myadapterlib.RecyInitCommonAdapter;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;
import com.jd.myadapterlib.adapterkit.CommonTouchHelper;
import com.jd.myadapterlib.adapterkit.ItemTouchHelperUtils;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/22 0022
 * Name:
 * OverView: 可拖拽;头2
 * Usage:
 */
public class NormalTestBgaActivity extends BaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    List<Object> beans;
    RecyInitCommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.test1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        beans = new ArrayList<>();
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyInitCommonAdapter(mRecycler, R.layout.test4_item);
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initVariable() {
        ItemTouchHelperUtils.InitHelper(mRecycler, new CommonTouchHelper(adapter, beans));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        adapter.setDOnItemConvertListener(new DOnItemConvertListener() {
            @Override
            public void itemConvert(final RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof TestBean) {
                    TestBean bean = (TestBean) item;
                    viewHolder.setText(R.id.txt_two, bean.getContent());
                    ItemTouchHelperUtils.setTargetView(viewHolder.getView(R.id.img_one),viewHolder);
                }
            }
        });
        adapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                LogUtils.i("jarvisclick", position + "/"+ beans.get(position).toString());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        doPost();
    }

    private void doPost() {
        beans.add(new TestBean("设置为commonAdapter", "content1"));
        beans.add(new TestBean("设置为commonAdapter", "content2"));
        beans.add(new TestBean("设置为commonAdapter", "content3"));
        beans.add(new TestBean("设置为commonAdapter", "content4"));
        beans.add(new TestBean("设置为commonAdapter", "content5"));
        beans.add(new TestBean("设置为commonAdapter", "content6"));
        beans.add(new TestBean("设置为commonAdapter", "content1"));
        beans.add(new TestBean("设置为commonAdapter", "content1"));
        beans.add(new TestBean("设置为commonAdapter", "content1"));
        adapter.setDatas(beans);
    }

}
