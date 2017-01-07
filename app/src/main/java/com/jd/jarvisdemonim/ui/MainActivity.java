package com.jd.jarvisdemonim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBgaActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestBottomNavigationActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestExpandActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestMvcActivityNet;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestPageActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestTabReminderActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTextCarlendarActivity;
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

    @OnClick({R.id.txt_nim, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7
            , R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.txt_nim:
                Intent intent0 = new Intent(this, NimActivity.class);
                startActivity(intent0);
                break;
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
            case R.id.btn6:
                LogUtil.i("jarvisclick", "button");
                Intent intent6 = new Intent(this, NormalTextActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn7:
                LogUtil.i("jarvisclick", "button");
                Intent intent7 = new Intent(this, NormalTestMvcActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn8:
                LogUtil.i("jarvisclick", "button");
                Intent intent8 = new Intent(this, NormalTestMvcActivityNet.class);
                startActivity(intent8);
                break;
            case R.id.btn9:
                LogUtil.i("jarvisclick", "button");
                Intent intent9 = new Intent(this, NormalTestTabReminderActivity.class);
                startActivity(intent9);
                break;
            case R.id.btn10:
                LogUtil.i("jarvisclick", "button");
                Intent intent10 = new Intent(this, NormalTextCarlendarActivity.class);
                startActivity(intent10);
                break;
            case R.id.btn11:
                LogUtil.i("jarvisclick", "button");
                Intent intent11 = new Intent(this, NormalTestBottomNavigationActivity.class);
                startActivity(intent11);
                break;
        }
    }

}
