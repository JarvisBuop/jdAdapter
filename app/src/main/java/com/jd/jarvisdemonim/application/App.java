package com.jd.jarvisdemonim.application;

import android.app.Application;

import com.jd.jdkit.AppInitUtils;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInitUtils.getInstance().setApplication(this)
                .initGson()
                .initOkHttp()
                //调试
                .isDebug()
                .initFreelineCore()
                .initLeakCanary()
                .initLogger()
                .create();
    }
}
