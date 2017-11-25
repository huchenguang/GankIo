package com.example.chenguang.gankio.api;


import com.example.chenguang.gankio.bean.GankBean;
import com.example.chenguang.gankio.bean.GankDayBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by chenguang on 2017/7/25.
 */

public interface GankApiService {
    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * @param type
     * @param count
     * @param page
     * @return
     */
    @GET("/api/data/{type}/{count}/{page}")
    Observable<GankBean> getGankDataByType(@Path("type") String type, @Path("count") int count,
                                           @Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    @GET("/api/day/{year}/{month}/{day}")
    Observable<GankDayBean> getGankDataByDate(@Path("year") int year, @Path("month") int month,
                                              @Path("day") int day);

    /**
     * 随机数据：http://gank.io/api/random/data/分类/个数
     * <p>
     * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * 个数： 数字，大于0
     */
    @GET("/api/random/data/{category}/{count}")
    Observable<GankBean> getGankDataByRandom(@Path("category") String category, @Path("count")
            int count);

    /**
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("/api/search/query/{key}/category/all/count/10/page/1")
    Observable<GankBean> searchGankData(@Path("key") String key);
}
