package com.midian.demo.retrofit;


import com.midian.demo.bean.NewsBean;
import com.midian.demo.bean.WeChatBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class HttpMethods {
    public static final String KEY = "a407a740e1e2a7f0ac0f1bf7f21280b0";
    public static final String WECHATKEY = "6137d567e4bb6d3f07f87c2d088f2b7b";
    public static final String BASE_URL = "http://v.juhe.cn/";
    private static int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private HttpService httpService;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new LogInterceptor());
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        httpService = retrofit.create(HttpService.class);
    }

    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{
        @Override
        public T call(HttpResult<T> tHttpResult) {
            if(!"0".equals(tHttpResult.getError_code())){
                try {
                    throw new Exception(tHttpResult.getReason());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return tHttpResult.getResult();
        }
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    public void getNews(Subscriber<NewsBean> subscriber,String type){
        Map<String,String> map=new HashMap<>();
        map.put("key",KEY);
        map.put("type",type);
        Observable<NewsBean> observable = httpService.getNews(map)
                .map(new HttpResultFunc<NewsBean>());
        toSubscribe(observable,subscriber);
    }

    public void getWeChat(Subscriber<WeChatBean> subscriber,String pno){
        Map<String,String> map=new HashMap<>();
        map.put("key",WECHATKEY);
        map.put("pno",pno);
        Observable<WeChatBean> observable = httpService.getWeChat(map)
                .map(new HttpResultFunc<WeChatBean>());
        toSubscribe(observable,subscriber);
    }
}
