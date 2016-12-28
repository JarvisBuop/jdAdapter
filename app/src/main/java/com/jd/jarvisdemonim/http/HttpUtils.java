package com.jd.jarvisdemonim.http;


import com.lzy.okhttputils.OkHttpUtils;

/**
 *
 */
public class HttpUtils {
    private final static String TAG = "HttpUtils";
    private final static String MIYAO = "O]dWJ,[*g)%k\"?q~g6Co!`cQvV>>Ilvw";// 秘钥
    public final static String base_url = "http://122.224.228.167:80";
    /**
     * 停止http调用
     */
    public static void cancelPost() {
        OkHttpUtils.getInstance().cancelTag(TAG);
    }
}



