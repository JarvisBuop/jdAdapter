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
    private static boolean isDebug = BaseConfig.isDebug;
    private static final String FastTestTag = "jarvistest";

    public static void setLog(boolean isLog) {
        isDebug = isLog;
    }

    /**
     * 使用自定义的tag;
     * @param tag
     * @param msg
     */
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

    //使用配置的tag;
    public static void ui(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    /**
     * 使用固定tag;
     * @param msg
     */
    public static void d(CharSequence msg) {
        if (isDebug)
            Log.d(FastTestTag, msg.toString());
    }

    public static void i(CharSequence msg) {
        if (isDebug)
            Log.i(FastTestTag, msg.toString());
    }

    public static void e(CharSequence msg) {
        if (isDebug)
            Log.e(FastTestTag, msg.toString());
    }

    public static void v(CharSequence msg) {
        if (isDebug)
            Log.v(FastTestTag, msg.toString());
    }

    public static void w(CharSequence msg) {
        if (isDebug)
            Log.w(FastTestTag, msg.toString());
    }

    /**
     * 使用三方打印工具类;
     * @param msg
     */
    public static void dLoger(String msg) {
        if (BaseConfig.isEnableLogger)
            Logger.d(msg);
    }

    public static void eLoger(String msg) {
        if (BaseConfig.isEnableLogger)
            Logger.e(msg);
    }

    public static void jsonLoger(String json) {
        if (BaseConfig.isEnableLogger)
            Logger.json(json);
    }

    public static void dLoger(String tag, String msg) {
        if (BaseConfig.isEnableLogger)
            Logger.t(tag).d(msg);
    }

    public static void eLoger(String tag, String msg) {
        if (BaseConfig.isEnableLogger)
            Logger.t(tag).e(msg);
    }

    public static void jsonLoger(String tag, String json) {
        if (BaseConfig.isEnableLogger)
            Logger.t(tag).json(json);
    }
}
