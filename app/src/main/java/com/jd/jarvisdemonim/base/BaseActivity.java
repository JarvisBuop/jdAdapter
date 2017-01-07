package com.jd.jarvisdemonim.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.views.CustomProgress;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import butterknife.ButterKnife;


/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private CustomProgress pd;
    protected Context mContext = this;
    private boolean cancelable = true;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        TAG = this.getClass().getSimpleName();
        LogUtils.d(TAG, "-isCreate");
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initVariable();
        processLogic(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "-isDestroy");
        ButterKnife.unbind(this);
    }

    public abstract int getContentViewId();

    /********************
     * 设置title;
     *********************/
    protected void setBack() {
        ImageView back = (ImageView) findViewById(R.id.back);
        if (back == null)
            return;
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    protected void setTitle(String titleName) {
        TextView title = (TextView) findViewById(R.id.title);
        if (title == null)
            return;
        title.setText(titleName);
    }

    protected View setSubmit(){
        TextView submit = (TextView) findViewById(R.id.submit);
        if(submit == null){
            return  null;
        }else {
            submit.setVisibility(View.VISIBLE);
            return submit;
        }
    }

    protected View setSearch() {
        ImageView right = (ImageView) findViewById(R.id.search);
        if (right == null) {
            return null;
        } else {
            right.setVisibility(View.VISIBLE);
            return right;
        }
    }

    protected View setHome() {
        ImageView right = (ImageView) findViewById(R.id.home);
        if (right == null) {
            return null;
        } else {
            right.setVisibility(View.VISIBLE);
            return right;
        }
    }


    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化变量
     */
    protected abstract void initVariable();

    /**
     * 给View控件添加事件监听器
     */
//    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 调用接口时，显示dialog
     */
    public void initProgressDialog() {
        if (pd == null) {
            pd = CustomProgress.show(mContext, mContext.getResources().getString(R.string.loading),
                    cancelable, cancelable ? new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
//                            HttpUtils.cancelPost();
                        }
                    } : null);

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    /**
     * 处理接口返回时，隐藏dialog
     */
    public void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
}
