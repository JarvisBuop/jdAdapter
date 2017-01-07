package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.entity.TestBean;
import com.jd.jarvisdemonim.entity.TestBean2;
import com.jd.jarvisdemonim.entity.TestBean3;
import com.jd.jarvisdemonim.ui.testadapteractivity.adapter.TestAdapter;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/16 0016 11
 * Name:
 * OverView:
 * Usage:
 */
public class NormalTestActivity extends BaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    TestAdapter mAdapter;
    List<Object> beans;

    @Override
    public int getContentViewId() {
        return R.layout.test1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        beans = new ArrayList<>();
        mAdapter = new TestAdapter(this, null);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAdapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                LogUtils.i("jarvisclick", position + "");
//                if (position == 0) {
//                    beans.clear();
//                    beans.add(new TestBean("设置为commonAdapter", "test"));
//                    beans.add(new TestBean("设置为commonAdapter", "test"));
//                    beans.add(new TestBean("设置为commonAdapter", "test"));
//                    RecyCommonAdapter<Object> recyCommonAdapter = new RecyCommonAdapter<Object>(mRecycler, R.layout.test1_item, beans) {
//                        @Override
//                        protected void convert(RecyViewHolder viewHolder, Object item, int position) {
//                            if (item instanceof TestBean) {
//                                TestBean bean = (TestBean) item;
//                                viewHolder.setText(R.id.txt_one, bean.getName());
//                                viewHolder.setText(R.id.txt_two, bean.getContent());
//                                viewHolder.setItemChildClickListener(R.id.txt_one);
//                            }
//                        }
//                    };
//                    mRecycler.setAdapter(recyCommonAdapter);
//                    recyCommonAdapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                            LogUtils.i("jarvisclickchange", position + "");
//                        }
//
//                        @Override
//                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                            return false;
//                        }
//                    });
//                    recyCommonAdapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
//                        @Override
//                        public void onItemChildClick(View convertView, View view, int pos) {
//                            switch (view.getId()) {
//                                case R.id.img_one:
//                                    LogUtils.i("jarvisclickimg11111", pos + "");
//                                    break;
//                                case R.id.txt_one:
//                                    LogUtils.i("jarvisclick2222",pos+"");
//                            }
//                        }
//                    });
//                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                LogUtils.i("jarvisclick", position + "");
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case R.id.img_one:
                        LogUtils.i("jarvisclickimg11111", pos + "");
                        break;
                    case R.id.txt_one:
                        LogUtils.i("jarvisclick2222",pos+"");
                }
            }
        });
        doPost();
    }

    private void doPost() {
        beans.add(new TestBean("设置为commonAdapter", "content1"));
        beans.add(new TestBean("title2", "content2"));
        beans.add(new TestBean2("title2"));
        beans.add(new TestBean2("title2"));
        beans.add(new TestBean3("title1", "title2"));
        beans.add(new TestBean3("title1", "title2"));
        mAdapter.setDatas(beans);
    }
}
