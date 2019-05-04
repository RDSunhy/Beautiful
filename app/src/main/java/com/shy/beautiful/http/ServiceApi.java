package com.shy.beautiful.http;

import com.shy.beautiful.bean.ImagesBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ServiceApi {

    /*@GET("/api/v3/ranklist")
    Observable<Bean> getData(@QueryMap Map<String, String> map);*/
    //https://uploadbeta.com/api/pictures/search?key=%E6%8E%A8%E5%A5%B3%E9%83%8E&start=99&offset=200
    @GET("/api/pictures/search")
    Observable<List<ImagesBean>> getImages(@QueryMap Map<String, String> map);
}
