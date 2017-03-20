package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.RetrofitTestBean;
import com.jd.jarvisdemonim.ui.controller.RetrofitTestImpl;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;

import butterknife.Bind;
import okhttp3.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/17 0017
 * Name:
 * OverView: 测试retrofit下载;
 * Usage:
 * @see RetrofitTestBean
 */

public class NormalTestRetrofitActivity extends DBaseActivity implements View.OnClickListener {
    @Bind(R.id.txt_net)
    TextView txtNet;
    @Bind(R.id.btn_net)
    Button btn;
    RetrofitTestImpl testImpl;
    private HttpParams mParams;

    @Override
    public int getContentViewId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        testImpl = new RetrofitTestImpl();
    }

    @Override
    protected void initVariable() {
//        mParams = new HttpParams();
//        mParams.put("api", "search");
//        mParams.put("action", "search");
//        mParams.put("page", "1");
//        mParams.put("pageSize", "3");
//        mParams.put("order", "C_Time");
//        mParams.put("sqlText", "Title=\"图书\"");
//        mParams.put("content", "图书");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        testImpl.getModel(new IOnHttpListener() {
            @Override
            public void onSuccess(Object o) {
                if (o instanceof RetrofitTestBean) {
                    RetrofitTestBean bean = (RetrofitTestBean) o;
                    txtNet.setText(bean.getData().get(0).getName() + "\n" + bean.toString());
                }
            }

            @Override
            public void onError(Response response, Exception e) {
                ToastUtils.showToast("下载出错");
            }

            @Override
            public void onCache(Object o) {

            }

            @Override
            public void onAfter() {
                dismissDialog();
            }

            @Override
            public void onBefore() {
                showDialog();
            }
        }, "search","search","1","3","C_Time","Title=\"图书\"","图书");
    }
}
