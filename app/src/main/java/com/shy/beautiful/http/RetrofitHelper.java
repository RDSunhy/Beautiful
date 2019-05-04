package com.shy.beautiful.http;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import com.shy.beautiful.bean.ImagesBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetrofitHelper {

    Context mContext;

    public RetrofitHelper(Context context){
        mContext = context;
    }
    //http://baobab.kaiyanapp.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .removeHeader("User-Agent")//移除旧的
                            .addHeader("User-Agent", WebSettings.getDefaultUserAgent(mContext))
                            .build();
                    return chain.proceed(request);
                }
            })
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build();
    //http://baobab.kaiyanapp.com/
    //https://uploadbeta.com/api/
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://uploadbeta.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    ServiceApi serviceApi = retrofit.create(ServiceApi.class);//创建对象

    public Observable<List<ImagesBean>> getImages(Map<String,String> map){
        return serviceApi.getImages(map);
    }
}
