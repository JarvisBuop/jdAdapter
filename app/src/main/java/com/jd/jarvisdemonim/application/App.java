package com.jd.jarvisdemonim.application;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.jd.jarvisdemonim.entity.DaoMaster;
import com.jd.jarvisdemonim.entity.DaoMaster.DevOpenHelper;
import com.jd.jarvisdemonim.entity.DaoSession;
import com.jd.jdkit.AppInitUtils;

import org.greenrobot.greendao.database.Database;

/**
 * 使用dex分包;
 */
public class App extends MultiDexApplication {
    //是否数据库加密;
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 分包初始化;
         */
        initDex();
        /**
         * 初始化其他三方库;
         */
        AppInitUtils.getInstance().setApplication(this)
                .initGson()
                .initOkHttp()
                //调试
                .isDebug()
                .initFreelineCore()
                .initLeakCanary()
                .initLogger()
                .create();
        //新建一个数据库;
        initGreenDao();
    }

    private void initDex() {
        MultiDex.install(this);
    }

    private void initGreenDao() {
        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
