package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/29 0029
 * Name:
 * OverView: 单元测试 - application时必须方法名使用test-
 * 方法测试时,@test ;
 *
 * Usage:
 * activity 的单元测试:
 * ActivityUnitTestCase 不会产生一个activity的界面,底层处理,不能有数据依赖和交互依赖,
 * 测试按键事件或者是启动当前activity的intent,code;
 * ,
 * ActivityInstrumentationTestCase2 类的构造函数必须是无参的，当传递按键前我们要屏蔽activity的touch事件
 * 测试按键,直接测试方法就行,测试view前需要获取焦点;
 */

public class NormalTestJUnitActivity extends DBaseActivity {
    @Bind(R.id.btn_test)
    Button btn;
    @Bind(R.id.textView)
    TextView txt;
    @Bind(R.id.editText)
    EditText editText;


    @Override
    public int getContentViewId() {
        return R.layout.activity_jnuit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTest();
            }
        });
    }

    public void doTest() {
        txt.setText(editText.getText().toString() + "");
    }
}
