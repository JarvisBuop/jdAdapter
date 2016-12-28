package com.jd.jarvisdemonim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBgaActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestExpandActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestPageActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity;
import com.netease.nim.uikit.common.util.log.LogUtil;

import butterknife.OnClick;

/**
 * Nim Demo 的使用;
 */
public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn1://baseadapter
                LogUtil.i("jarvisclick", "button");
                Intent intent = new Intent(this, NormalTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2://tuozhuai
                LogUtil.i("jarvisclick", "button");
                Intent intent2 = new Intent(this, NormalTestBgaActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn3:
                LogUtil.i("jarvisclick", "button");
                Intent intent3 = new Intent(this, NormalTestPageActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn4:
                LogUtil.i("jarvisclick", "button");
                Intent intent4 = new Intent(this, NormalTestExpandActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn5:
                LogUtil.i("jarvisclick", "button");
                Intent intent5 = new Intent(this, NormalTestStickyActivity.class);
                startActivity(intent5);
                break;
        }
    }

}
