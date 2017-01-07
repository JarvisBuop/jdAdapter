package com.jd.jarvisdemonim.application;

import android.app.Application;

import com.jd.jdkit.AppInitUtils;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInitUtils.getInstance().setApplication(this).isDebug()
                .initGson()
                .initOkHttp()
                //调试
                .initFreelineCore()
                .initLeakCanary()
                .initLogger()
                .create();
    }
}
