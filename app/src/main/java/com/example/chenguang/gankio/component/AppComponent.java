package com.example.chenguang.gankio.component;

import android.content.Context;

import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.module.AppModule;
import com.example.chenguang.gankio.module.GankIoApiModule;

import dagger.Component;

/**
 * Created by chenguang on 2017/9/14.
 */
@Component(modules = {AppModule.class, GankIoApiModule.class})
public interface AppComponent {
    Context getContext();

    GankApi getGankApi();
}
