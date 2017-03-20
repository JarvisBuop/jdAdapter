package com.jd.jarvisdemonim.http;

import com.jd.jdkit.BaseConfig;
import com.jd.jdkit.elementkit.utils.system.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/17 0017
 * Name:
 * OverView: retrofit 的下载工具类;
 * Usage:
 */

public class RetrofitHelper {
    private static RetrofitHelper mInstance = null;
    private OkHttpClient mOkHttpClient;


    public static RetrofitHelper getInstance() {
        synchronized (RetrofitHelper.class) {
            if (mInstance == null)
                mInstance = new RetrofitHelper();
        }
        return mInstance;
    }
    private RetrofitHelper(){
        initOkHttp();
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cacheFile = new File(BaseConfig.application.getCacheDir().getAbsolutePath());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetAvailable(BaseConfig.application)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetAvailable(BaseConfig.application)) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
//        builder.addInterceptor(new ChuckInterceptor(Injection.provideContext()));//添加chuck的拦截器;
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
//         builder.retryOnConnectionFailure(true);

        //以上设置结束，才能build(),不然设置白搭
        mOkHttpClient = builder.build();
    }

    public  <T> T getApiService(String baseUrl, Class<T> clz,OkHttpClient client) {
        if(client==null) client=mOkHttpClient;
        if(baseUrl==null) baseUrl = HttpUtils.mainTestUrl;
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意，baseUrl必须后缀"/"
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//添加json的解析;
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加RXjava的支持;
                .build();
        return retrofit.create(clz);
    }

}
