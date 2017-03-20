package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.database.CPDataHelper;
import com.jd.jarvisdemonim.entity.ContentProviderBean;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_CLIENT;
import static com.jd.jarvisdemonim.service.MessagerService.MESSAGER_HELLO_SERVICE;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/13 0013
 * Name:
 * OverView: 测试contentProvider的数据共享;
 * Usage:
 */

public class NormalTestContentProviderActivity extends DBaseActivity {
    public static int TYPE_ONE = 0;
    public static int TYPE_TWO = 1;
    @Bind(R.id.recycler)
    RecyclerView mrecycler;
    @Bind(R.id.edt_input)
    EditText edtText;
    List<ContentProviderBean> mDatas;
    public static String contentUri = "content://com.jd.jarvisdemonim.test.provider.Jprovider/testBean";
    private RecyCommonAdapter recyCommonAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_contentprovider;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mrecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recyCommonAdapter = new RecyCommonAdapter(mrecycler, R.layout.item_left, mDatas) {
            @Override
            protected void convert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof ContentProviderBean) {
                    ContentProviderBean bean = (ContentProviderBean) item;
                    TextView view = viewHolder.getView(R.id.txt_title);
                    if (bean.getMark() == MESSAGER_HELLO_SERVICE) {
                        view.setGravity(Gravity.LEFT);
                        view.setText("服务端(主App):" + "id" + bean.getId() + "说:" + bean.getContent());
                    } else if (bean.getMark() == MESSAGER_HELLO_CLIENT) {
                        view.setGravity(Gravity.RIGHT);
                        view.setText("客户端(副App):" + "id" + bean.getId() + "说:" + bean.getContent());
                    }
                }
            }
        };
        mrecycler.setAdapter(recyCommonAdapter);
        getCPDatas();//获取contentprovider的信息;
    }

    private void getCPDatas() {
        Cursor query = getContentResolver().query(Uri.parse(contentUri), null, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                ContentProviderBean bean = new ContentProviderBean();
                String content = query.getString(query.getColumnIndex(CPDataHelper.PARAM_ONE));
                int mark = query.getInt(query.getColumnIndex(CPDataHelper.PARAM_TWO));
                int id = query.getInt(query.getColumnIndex("_id"));
                bean.setContent(content);
                bean.setMark(mark);
                bean.setId(id);
                mDatas.add(bean);
            }
            query.close();
        }
        recyCommonAdapter.setDatas(mDatas);
    }

    /**
     * @param view
     */
    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_updata, R.id.btn_query})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (TextUtils.isEmpty(edtText.getText().toString())) {
                    ToastUtils.showToast("空");
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(CPDataHelper.PARAM_ONE, edtText.getText().toString());
                values.put(CPDataHelper.PARAM_TWO, TYPE_ONE);
                Uri insert = getContentResolver().insert(Uri.parse(contentUri), values);
                break;
            case R.id.btn_delete://删除第一个;
                /**
                 * 自定义方法查询contentprovider的大小;
                 */
                Bundle getCount1 = getContentResolver().call(Uri.parse(contentUri), "getCount", contentUri, null);
                long count1 = getCount1.getLong("long");
                LogUtils.i("testreflext", count1 + "");

                int delete = getContentResolver().delete(Uri.parse(contentUri), CPDataHelper.PARAM_ONE+" =?", new String[]{"全改了?"});
                if (delete > 0) {
                    ToastUtils.showToast("删除成功");
                } else {
                    ToastUtils.showToast("删除失败");
                }
                break;
            case R.id.btn_updata://修改最后一个;
                ContentValues values1 = new ContentValues();
                values1.put(CPDataHelper.PARAM_ONE, "全改了?");
                values1.put(CPDataHelper.PARAM_TWO, TYPE_ONE);
                /**
                 * 自定义方法查询contentprovider的大小;
                 */
                Bundle getCount = getContentResolver().call(Uri.parse(contentUri), "getCount", contentUri, null);
                long count = getCount.getLong("long");
                LogUtils.i("testreflext", count + "");
                int update = getContentResolver().update(Uri.parse(contentUri), values1, "_id =?", new String[]{count-1+""});
                if (update > 0) {
                    ToastUtils.showToast("更新成功");
                } else {
                    ToastUtils.showToast("更新失败");
                }
                break;
            case R.id.btn_query:
                Cursor query = getContentResolver().query(Uri.parse(contentUri), null, null, null, null);
                if (query != null) {
                    ToastUtils.showToast("查询成功");
                }
                ArrayList<ContentProviderBean> list = new ArrayList();
                while (query.moveToNext()) {
                    int columnIndex = query.getColumnIndex(CPDataHelper.PARAM_ONE);
                    String content = query.getString(columnIndex);
                    int mark = query.getInt(query.getColumnIndex(CPDataHelper.PARAM_TWO));
                    int id = query.getInt(query.getColumnIndex("_id"));
                    ContentProviderBean bean = new ContentProviderBean(content, mark);
                    bean.setId(id);
                    list.add(bean);
                }
                query.close();
                mDatas.clear();
                mDatas.addAll(list);
                recyCommonAdapter.setDatas(mDatas);
                break;
        }
    }


}
