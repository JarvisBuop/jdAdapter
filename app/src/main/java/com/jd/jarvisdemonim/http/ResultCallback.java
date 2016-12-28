package com.jd.jarvisdemonim.http;

import android.support.annotation.Nullable;

import com.google.gson.internal.$Gson$Types;
import com.jd.jarvisdemonim.application.App;
import com.lzy.okhttputils.callback.AbsCallback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 自定义Callback
 * 网络请求返回 解析为实体
 */

public abstract class ResultCallback<T> extends AbsCallback<T> {
    Type mType;

    public ResultCallback() {
        this.mType = getSuperClassTypeParameter(getClass());
    }

    private static Type getSuperClassTypeParameter(Class<?> subClass) {
        Type superclass = subClass.getGenericSuperclass();
        if (superclass instanceof Class) {
            try {
                throw new Exception("缺少实体");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }


    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        return App.getInstance().getGson().fromJson(string, mType);
    }

    @Override
    public abstract void onAfter(@Nullable T t, @Nullable Exception e);

    @Override
    public abstract void onCacheSuccess(T t, Call call);
}
