package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.application.App;
import com.jd.jarvisdemonim.entity.DaoSession;
import com.jd.jarvisdemonim.entity.InnerDemo;
import com.jd.jarvisdemonim.entity.InnerDemoDao;
import com.jd.jarvisdemonim.entity.TestDemoBean;
import com.jd.jarvisdemonim.entity.TestDemoBean2;
import com.jd.jarvisdemonim.entity.TestDemoBean2Dao;
import com.jd.jarvisdemonim.entity.TestDemoBeanDao;
import com.jd.jarvisdemonim.ui.adapter.GreenDaoMultiAdapter;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/15 0015
 * Name:
 * OverView: 测试数据库(单布局);
 * Usage:
 */

public class NormalTestMultiGreenDaoActivity extends DBaseActivity {
    @Bind(R.id.recycler_display)
    RecyclerView mDisPlay;
    private Query<TestDemoBean> mQuery;
    private Query<TestDemoBean2> mQuery2;
    private Query<InnerDemo> mQueryInner;
    private TestDemoBeanDao testDemoBeanDao;
    private TestDemoBean2Dao testDemoBeanDao2;
    private GreenDaoMultiAdapter adapter;
    private int i;
    List<Object> mDatas;
    private long j;
    private InnerDemoDao innerDemoDao;

    @Override
    public int getContentViewId() {
        return R.layout.activity_greendao2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        adapter = new GreenDaoMultiAdapter(mContext);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
//        DaoSession daoSession2 = ((App) getApplication()).getDaoSession2();
//        DaoSession daoSessioninner = ((App) getApplication()).getDaoSessionInner();

        testDemoBeanDao = daoSession.getTestDemoBeanDao();
        mQuery = testDemoBeanDao.queryBuilder().orderAsc(TestDemoBeanDao.Properties.Id).build();

        testDemoBeanDao2 = daoSession.getTestDemoBean2Dao();
        mQuery2 = testDemoBeanDao2.queryBuilder().orderAsc(TestDemoBean2Dao.Properties.Id).build();
//        List<TestDemoBean2> demoBean2s = testDemoBeanDao2.queryDeep(null, null);
//        testDemoBeanDao2.queryRawCreate()
        innerDemoDao = daoSession.getInnerDemoDao();
        mQueryInner = innerDemoDao.queryBuilder().orderAsc(InnerDemoDao.Properties.Id).build();
    }

    @Override
    protected void initVariable() {
        mDisPlay.setHasFixedSize(true);
        mDisPlay.setLayoutManager(new LinearLayoutManager(mContext));
        mDisPlay.setAdapter(adapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        adapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case R.id.tv_more:
                        ToastUtils.showToast("layout1删除" + pos);
                        if (adapter.getItemObject(pos) instanceof TestDemoBean) {
                            TestDemoBean bean = (TestDemoBean) adapter.getItemObject(pos);
                            testDemoBeanDao.deleteByKey(bean.getId());
                            ToastUtils.showToast(bean.getName() + "被删除" + bean.getId());
                            loadDbDatas();
                        }
                        break;
                    case R.id.tv_content:
                        ToastUtils.showToast("layout2删除" + pos);
                        if (adapter.getItemObject(pos) instanceof TestDemoBean2) {
                            TestDemoBean2 bean = (TestDemoBean2) adapter.getItemObject(pos);
                            testDemoBeanDao2.deleteByKey(bean.getId());
                            ToastUtils.showToast(bean.getDemo().getTarget() + "被删除" + bean.getId());
                            loadDbDatas();
                        }
                        break;
                }
            }
        });
        loadDbDatas();
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_update, R.id.btn_query, R.id.btn_add2, R.id.btn_delete2, R.id.btn_update2, R.id.btn_query2})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                TestDemoBean zhangsan = new TestDemoBean("zhangsan", i++);
                testDemoBeanDao.insert(zhangsan);
                LogUtils.d("Inserted new note, ID: " + zhangsan.getId());
                loadDbDatas();
                break;
            case R.id.btn_delete:
                //删除第一个数据;
                List<TestDemoBean> list = mQuery.list();
                if (list.size() != 0) {
                    TestDemoBean bean = list.get(0);
                    testDemoBeanDao.deleteByKey(bean.getId());
                }
                loadDbDatas();
                break;
            case R.id.btn_update:
                //修改第一个数据;
                List<TestDemoBean> list2 = mQuery.list();
                if (list2.size() != 0) {
                    TestDemoBean bean2 = list2.get(0);
                    if (bean2.getName().startsWith("1")) {
                        bean2.setName("我勒个擦");
                    } else {
                        bean2.setName("1我是修改了的");
                    }
                    testDemoBeanDao.update(bean2);
                }
                loadDbDatas();
                break;
            case R.id.btn_query:
                List<TestDemoBean> list1 = testDemoBeanDao.queryBuilder()
                        .where(TestDemoBeanDao.Properties.Age.ge(5), TestDemoBeanDao.Properties.Age.le(10))
                        .orderAsc(TestDemoBeanDao.Properties.Id)
                        .list();
                adapter.setDatas(list1);
                break;
//*********************************************************
            case R.id.btn_add2:
                j++;
                InnerDemo green = new InnerDemo(j, "layout" + j, "green");
                TestDemoBean2 demoBean2 = new TestDemoBean2(null, j);
                demoBean2.setDemo(green);
                innerDemoDao.insertOrReplace(green);
                testDemoBeanDao2.insert(demoBean2);
                LogUtils.d("Inserted new note, ID: " + demoBean2.getId());
                loadDbDatas();
                break;
            case R.id.btn_delete2:
                //删除第一个数据;
                List<TestDemoBean2> demoBean2s = mQuery2.list();
                if (demoBean2s.size() != 0) {
                    TestDemoBean2 bean = demoBean2s.get(0);
                    testDemoBeanDao2.deleteByKey(bean.getId());
                }
                loadDbDatas();
                break;
            case R.id.btn_update2:
                //修改第一个数据;
                List<TestDemoBean2> demoBean2s1 = mQuery2.list();
                if (demoBean2s1.size() != 0) {
                    TestDemoBean2 bean2 = demoBean2s1.get(0);
                    if (bean2.getDemo().getTarget().startsWith("1")) {
                        bean2.getDemo().setTarget("我勒个擦");
                    } else {
                        bean2.getDemo().setTarget("1我是修改了的");
                    }
                    testDemoBeanDao2.update(bean2);
                }
                loadDbDatas();
                break;
            case R.id.btn_query2:
                List<TestDemoBean2> demoBeen = testDemoBeanDao2.queryBuilder()
                        .where(TestDemoBean2Dao.Properties.Id.ge(2), TestDemoBean2Dao.Properties.Id.le(4))
                        .orderAsc(TestDemoBean2Dao.Properties.Id)
                        .list();
                adapter.setDatas(demoBeen);
                break;

        }
    }

    private void loadDbDatas() {
        mDatas.clear();
        List<TestDemoBean> list = mQuery.list();
        LogUtils.e("total num " + list.size());
//        List<TestDemoBean2> list2 = testDemoBeanDao2.loadAll();
        List<TestDemoBean2> list2 = mQuery2.list();
        LogUtils.e("total num2 " + list2.size());
        mDatas.addAll(list);
        mDatas.addAll(list2);
        adapter.setDatas(mDatas);
    }
}
