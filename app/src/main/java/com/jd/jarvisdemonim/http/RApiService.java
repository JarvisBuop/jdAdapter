package com.jd.jarvisdemonim.http;

import com.jd.jarvisdemonim.entity.RetrofitTestBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/20 0020
 * Name:
 * OverView:    retrofit联网服务请求;
 * Usage:
 */

public interface RApiService {
    @POST("api.axd")//这里是跟在baseurl后面的，拼接起来完整的
    Call<RetrofitTestBean> getDatasByRetrofit(@Query("api") String api,
                                              @Query("action") String action,
                                              @Query("page") String page,
                                              @Query("pageSize") String pageSize,
                                              @Query("order") String order,
                                              @Query("sqlText") String sqlText,
                                              @Query("content") String content);

}
