package com.jd.jarvisdemonim;

import android.test.ApplicationTestCase;
import android.util.Log;

import com.jd.jarvisdemonim.application.App;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<App> {
    private App AppTest;

    public ApplicationTest() {
        super(App.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //建立一个app
        createApplication();
        //获取待测试的app;
        AppTest = getApplication();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testgetTestString() throws Exception {
        assertEquals("单元测试", AppTest.getTestString());
        Log.i("apptest", "单元测试");
    }
}