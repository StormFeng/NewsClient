package com.midian.demo.retrofit;


import com.midian.demo.NewsBean;
import com.midian.demo.WeChatBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public interface HttpService {

    @GET("toutiao/index")
    Observable<HttpResult<NewsBean>> getNews(@QueryMap Map<String, String> options);

    @GET("weixin/query")
    Observable<HttpResult<WeChatBean>> getWeChat(@QueryMap Map<String, String> options);

}
