package com.example.chenguang.gankio.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.component.DaggerAppComponent;
import com.example.chenguang.gankio.module.AppModule;
import com.example.chenguang.gankio.module.GankIoApiModule;
import com.example.chenguang.gankio.utils.ConstantValues;

/**
 * Created by chenguang on 2017/6/30.
 */

public class MyApplication extends Application {


    private static AppComponent appComponent;
    private static SPUtils sp_settings;

    public static MyApplication getContext() {
        return context;
    }

    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
//        initCrashHandler();

        context = this;
        initComponent();
        sp_settings = SPUtils.getInstance("settings");
        initPrefs();
    }

    private void initPrefs() {
        boolean is_night = sp_settings.getBoolean(ConstantValues.IS_NIGHT, false);
        setMode(is_night);
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .gankIoApiModule(new GankIoApiModule())
                .build();

    }

    private void initCrashHandler() {
        //设置该CrashHandler为程序的默认处理器
        CrashHandler catchExcep = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static SPUtils getSp() {
        return sp_settings;
    }

    public static void setMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
