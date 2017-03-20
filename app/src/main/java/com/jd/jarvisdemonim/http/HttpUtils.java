package com.jd.jarvisdemonim.http;


import com.jd.jarvisdemonim.entity.RetrofitTestBean;
import com.jd.jarvisdemonim.ui.model.RegisterUuid4AppBean;
import com.jd.jdkit.elementkit.utils.common.BaseHttpUtils;
import com.jd.jdkit.okhttp.ResultCallback;
import com.lzy.okhttputils.model.HttpParams;

/**
 *
 */
public class HttpUtils extends BaseHttpUtils {

    private static class SingleTon {
        private static HttpUtils getInstance = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return SingleTon.getInstance;
    }

    private HttpUtils() {
    }

    private String host = "http://www.onemorecloset.com:8081/omcserver1";
    private String registerUuid4App = "/app/base/registerUuid4App.action";
    public static String mainTestUrl = "http://api.hzzhwhy.cn:8211/";
    public static String mainTestUrlForOkhttp = "http://api.hzzhwhy.cn:8211/api.axd";

    public void registerUuid4App(HttpParams httpParams, ResultCallback<RegisterUuid4AppBean> callback) {
        httpPost(host, registerUuid4App, httpParams, callback);
    }

    public void getMainTestUrl(HttpParams httpParams, ResultCallback<RetrofitTestBean> callback) {
        httpPost(mainTestUrlForOkhttp, "", httpParams, callback);
    }

}



