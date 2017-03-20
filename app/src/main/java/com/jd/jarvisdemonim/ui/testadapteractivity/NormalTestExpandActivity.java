package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.entity.TestChildBean;
import com.jd.jarvisdemonim.entity.TestGroupBean;
import com.jd.jarvisdemonim.ui.adapter.TestExpandAdapter;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:
 * Usage:
 */
public class NormalTestExpandActivity extends BaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    LinearLayoutManager llm;
    List<Object> beans;
    TestExpandAdapter adapter;
    private ArrayList<TestChildBean> list;
    private ArrayList<TestChildBean> list2;

    @Override
    public int getContentViewId() {
        return R.layout.test_recycler;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        beans = new ArrayList<>();
        adapter = new TestExpandAdapter(this);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void initVariable() {
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(llm);
        adapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case R.id.btn_test:
                        if (beans.get(pos) instanceof TestGroupBean) {
                            TestGroupBean bean = (TestGroupBean) beans.get(pos);
                            LogUtils.i("jarvisclick", pos + "+"+bean.getContentTitle());
                            if (bean.isExpand()) {//false
                                adapter.removeExpandAll(pos + 1, bean.getBean());
                                bean.setExpand(!bean.isExpand());
                            } else {
                                adapter.addExpandAll(pos + 1, bean.getBean());
                                bean.setExpand(!bean.isExpand());
                            }
                        }
                        break;
                    case R.id.txt_two:
                        if (beans.get(pos) instanceof TestChildBean) {
                            TestChildBean bean = (TestChildBean) beans.get(pos);
                            LogUtils.i("jarvisclick", pos + "-" + bean.getContent());
                        }
                        break;
                }
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list.add(new TestChildBean("name1", "content1"));
        list.add(new TestChildBean("name2", "content2"));
        list.add(new TestChildBean("name3", "content3"));
        TestGroupBean testGroupBean = new TestGroupBean(true, "title1", list);
        list2 = new ArrayList<>();
        list2.add(new TestChildBean("name11", "content11"));
        list2.add(new TestChildBean("name22", "content22"));
        list2.add(new TestChildBean("name33", "content33"));
        TestGroupBean testGroupBean2 = new TestGroupBean(true, "title2", list2);

        beans.add(testGroupBean);
        beans.addAll(testGroupBean.getBean());
        beans.add(testGroupBean2);
        beans.addAll(testGroupBean2.getBean());
        adapter.setDatas(beans);
    }
}
