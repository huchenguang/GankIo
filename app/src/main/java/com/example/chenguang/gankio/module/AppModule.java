package com.example.chenguang.gankio.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenguang on 2017/9/14.
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
