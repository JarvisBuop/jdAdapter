package com.jd.jdkit.elementkit.utils.log;

import android.util.Log;

import com.jd.jdkit.BaseConfig;
import com.orhanobut.logger.Logger;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/30 0030
 * Name:
 * OverView:
 * Usage:
 */
public class LogUtils {
    private static final String TAG = BaseConfig.httpTag;
    private static boolean isDebug = true;

    public static void setLog(boolean isLog) {
        isDebug = isLog;
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void ui(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void dLoger(String msg) {
        if (BaseConfig.isDebug)
            Logger.d(msg);
    }

    public static void eLoger(String msg) {
        if (BaseConfig.isDebug)
            Logger.e(msg);
    }

    public static void jsonLoger(String json) {
        if (BaseConfig.isDebug)
            Logger.json(json);
    }

}
