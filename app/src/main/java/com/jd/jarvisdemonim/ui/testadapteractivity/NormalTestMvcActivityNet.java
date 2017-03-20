package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.controller.RegisModelImpl;
import com.jd.jarvisdemonim.ui.model.RegisterUuid4AppBean;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.IInterface.mvc.ModelImpl;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/3 0003
 * Name:
 * OverView: MVC联网测试;
 * Usage:
 */

public class NormalTestMvcActivityNet extends BaseActivity {

    @Bind(R.id.txt_test)
    TextView txtTest;
    @Bind(R.id.btn_retry)
    Button btnRetry;
    private ModelImpl testImpl;

    @Override
    public int getContentViewId() {
        return R.layout.activity_multi_list_net;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
        testImpl = new RegisModelImpl();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    /**
     * 此处与mvp模式的区别:
     * mvp 是presenter通过model的接口下载到的数据,presenter中再通过view的接口去处理,直接传给activity的设置view层,将view层和model层分离;
     * mvc 是controller通过model的接口下载数据,直接回传到activity,view层和model层亦可以交互;
     */
    @OnClick(R.id.btn_retry)
    public void onClick() {
        btnRetry.setVisibility(View.GONE);
        HttpParams params = new HttpParams();
        params.put("uuid","123456");
        testImpl.getModel(new IOnHttpListener() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.i("jarvisdong","000000");
                if(o instanceof RegisterUuid4AppBean){
                    LogUtils.i("jarvisdong","0000");
                    RegisterUuid4AppBean bean = (RegisterUuid4AppBean) o;
                    txtTest.setText(bean.toString());
                }
            }

            @Override
            public void onError(Response response, Exception e) {
                LogUtils.i("jarvisdong","1111");
            }

            @Override
            public void onCache(Object o) {
                LogUtils.i("jarvisdong","2222");
            }

            @Override
            public void onAfter() {
                LogUtils.i("jarvisdong","3333");
            }

            @Override
            public void onBefore() {
                LogUtils.i("jarvisdong","4444");
            }
        }, params);
    }

}
