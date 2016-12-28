package com.jd.jarvisdemonim.utils;

import android.app.Application;

import com.jd.jarvisdemonim.application.App;


/**
 * 由于工具类中为了简化使用代码，直接把application写入，未避免工具类多了以后，替换包名不方便的问题，把几个关键变量在这里进行替换和修改包名
 */
public class BaseUtils {
    protected static Application application= App.getInstance();
    protected static boolean isDebug=true;
}
