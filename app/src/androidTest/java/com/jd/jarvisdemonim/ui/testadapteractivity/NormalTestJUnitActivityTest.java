package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/30 0030
 * Name:
 * OverView: 测试activity的交互;3处测试;
 * Usage:
 */
public class NormalTestJUnitActivityTest extends ActivityInstrumentationTestCase2<NormalTestJUnitActivity> {
    private Instrumentation mInstrumentation;
    private NormalTestJUnitActivity normalTestJUnitActivity;
    Button btn;
    TextView txt;
    EditText editText;

    public NormalTestJUnitActivityTest() {
        super(NormalTestJUnitActivity.class);
    }

    public NormalTestJUnitActivityTest(Class<NormalTestJUnitActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        /**这个程序中需要输入用户信息和密码，也就是说需要发送key事件，
         * 所以，必须在调用getActivity之前，调用下面的方法来关闭
         * touch模式，否则key事件会被忽略
         */
        //关闭touch模式
        setActivityInitialTouchMode(false);
        mInstrumentation = getInstrumentation();
        normalTestJUnitActivity = getActivity();
        btn = (Button) normalTestJUnitActivity.findViewById(R.id.btn_test);
        txt = (TextView) normalTestJUnitActivity.findViewById(R.id.textView);
        editText = (EditText) normalTestJUnitActivity.findViewById(R.id.editText);

        assertNotNull(btn);
        assertNotNull(txt);
        assertNotNull(editText);
    }


    public void testdoTest() throws Exception {
        // 开新线程，并通过该线程在实现在UI线程上执行操作，这里是在主线程中的操作
        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btn.requestLayout();
                btn.performClick();

            }
        });
        assertEquals("Say hello!", btn.getText().toString());
    }



    public void input() {
        assertEquals("Enter your name here",editText.getHint().toString());
        normalTestJUnitActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.requestLayout();
                editText.performClick();
            }
        });
/*
         * 由于测试用例在单独的线程上执行，所以此处需要同步application，
         * 调用waitForIdleSync等待测试线程和UI线程同步，才能进行输入操作。
         * waitForIdleSync和sendKeys不允许在UI线程里运行
         */
        mInstrumentation.waitForIdleSync();

        //调用sendKeys方法，输入字符传。这里输入的是TEST123
        sendKeys(KeyEvent.KEYCODE_SHIFT_LEFT, KeyEvent.KEYCODE_T, KeyEvent.KEYCODE_E,
                KeyEvent.KEYCODE_S,/* KeyEvent.KEYCODE_S,*/
                KeyEvent.KEYCODE_T, KeyEvent.KEYCODE_1,
                KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_3);

    }
    public void testInput(){
        input();
        assertEquals("test123", editText.getText().toString());
    }

}