package com.example.chenguang.gankio.api;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.chenguang.gankio.application.MyApplication;
import com.example.chenguang.gankio.bean.GankBean;
import com.example.chenguang.gankio.bean.GankDayBean;
import com.example.chenguang.gankio.utils.ConstantValues;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by chenguang on 2017/7/25.
 */

public class GankApi {
    private static GankApi mDefaultInstance;
    private GankApiService service;

    private GankApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantValues.GANK_IO_API)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();

        service = retrofit.create(GankApiService.class);
    }

    public static GankApi getDefaultInstance() {
        if (mDefaultInstance == null) {
            mDefaultInstance = new GankApi(getDefaultHttpClient());
        }
        return mDefaultInstance;
    }

    protected static OkHttpClient getDefaultHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置Http缓存 ，缓存路径+缓存大小  cache/HttpCache + 10MB
        Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(), "gankCache"),
                1024 * 1024 * 10);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new MyCacheInterceptor())
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }


    public Observable<GankBean> getGankDataByType(@ConstantValues.GankType String type,
                                                  int count, int page) {
        return service.getGankDataByType(type, count, page);
    }

    public Observable<GankDayBean> getGankDataByDate(int year, int month,
                                                     int day) {
        return service.getGankDataByDate(year, month, day);
    }

    public Observable<GankBean> getGankDataByRandom(@ConstantValues.GankType String category, int
            count) {
        return service.getGankDataByRandom(category, count);
    }

    public Observable<GankBean> searchGankData(String key) {
        return service.searchGankData(key);
    }


    private static class MyCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时   单位是秒
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetworkUtils.isConnected()) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

}
