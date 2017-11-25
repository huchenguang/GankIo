package com.example.chenguang.gankio.module;

import com.example.chenguang.gankio.api.GankApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenguang on 2017/9/14.
 */
@Module
public class GankIoApiModule {
    @Provides
    protected GankApi provideGankIoService() {
        return GankApi.getDefaultInstance();
    }
}
