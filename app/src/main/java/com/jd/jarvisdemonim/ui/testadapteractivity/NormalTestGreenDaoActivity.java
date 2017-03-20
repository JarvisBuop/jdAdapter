package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.application.App;
import com.jd.jarvisdemonim.entity.DaoSession;
import com.jd.jarvisdemonim.entity.TestDemoBean;
import com.jd.jarvisdemonim.entity.TestDemoBeanDao;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyInitCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myadapterlib.dinterface.DOnItemChildClickListener;
import com.jd.myadapterlib.dinterface.DOnItemConvertListener;

import org.greenrobot.greendao.query.Query;

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

public class NormalTestGreenDaoActivity extends DBaseActivity {
    @Bind(R.id.recycler_display)
    RecyclerView mDisPlay;
    private Query<TestDemoBean> mQuery;
    private TestDemoBeanDao testDemoBeanDao;
    private RecyInitCommonAdapter recyCommonAdapter;
    private int i;

    @Override
    public int getContentViewId() {
        return R.layout.activity_greendao;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyCommonAdapter = new RecyInitCommonAdapter(mDisPlay, R.layout.test2_item);
        // get the note DAO
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        testDemoBeanDao = daoSession.getTestDemoBeanDao();
        // query all notes, sorted a-z by their text
        mQuery = testDemoBeanDao.queryBuilder().orderAsc(TestDemoBeanDao.Properties.Id).build();

    }

    @Override
    protected void initVariable() {
        mDisPlay.setHasFixedSize(true);
        mDisPlay.setLayoutManager(new LinearLayoutManager(mContext));
        mDisPlay.setAdapter(recyCommonAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recyCommonAdapter.setDOnItemConvertListener(new DOnItemConvertListener() {
            @Override
            public void itemConvert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof TestDemoBean) {
                    TestDemoBean bean = (TestDemoBean) item;
                    viewHolder.setText(R.id.txt_two, bean.getName() + ":" + bean.getAge());
                    viewHolder.setItemChildClickListener(R.id.bg_ll);
                }
            }
        });
        recyCommonAdapter.setOnItemChildClickListener(new DOnItemChildClickListener() {
            @Override
            public void onItemChildClick(View convertView, View view, int pos) {
                switch (view.getId()) {
                    case R.id.bg_ll:
                        ToastUtils.showToast("删除" + pos);
                        if (recyCommonAdapter.getItemObject(pos) instanceof TestDemoBean) {
                            TestDemoBean bean = (TestDemoBean) recyCommonAdapter.getItemObject(pos);
                            testDemoBeanDao.deleteByKey(bean.getId());
                            ToastUtils.showToast(bean.getName() + "被删除" + bean.getId());
                            loadDbDatas();
                        }
                        break;
                }
            }
        });
        loadDbDatas();
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
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
                TestDemoBean bean = list.get(0);
                testDemoBeanDao.deleteByKey(bean.getId());
                loadDbDatas();
                break;
            case R.id.btn_update:
                //修改第一个数据;
                List<TestDemoBean> list2 = mQuery.list();
                TestDemoBean bean2 = list2.get(0);
                if (bean2.getName().startsWith("1")) {
                    bean2.setName("我勒个擦");
                } else {
                    bean2.setName("1我是修改了的");
                }
                testDemoBeanDao.update(bean2);
                loadDbDatas();
                break;
            case R.id.btn_query:
                List<TestDemoBean> list1 = testDemoBeanDao.queryBuilder()
                        .where(TestDemoBeanDao.Properties.Age.ge(5), TestDemoBeanDao.Properties.Age.le(10))
                        .orderAsc(TestDemoBeanDao.Properties.Id)
                        .list();
                recyCommonAdapter.setDatas(list1);
                break;

        }
    }

    private void loadDbDatas() {
        List<TestDemoBean> list = mQuery.list();
        LogUtils.e("total num " + list.size());
        recyCommonAdapter.setDatas(list);
    }
}
