package com.example.chenguang.gankio.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chenguang on 2017/9/13.
 */

public class ConstantValues {
    public static final String GANK_IO_API = "http://gank.io";
    //定义常量
    public static final String ALL = "all";
    public static final String IOS = "iOS";
    public static final String ANDROID = "Android";
    public static final String REST = "休息视频";
    public static final String EXTENDS = "拓展资源";
    public static final String FULI = "福利";
    public static final String WEB = "前端 ";
    public static final String[] TYPES = new String[]{ALL, IOS, ANDROID, REST, EXTENDS, FULI, WEB};

    //定义注解
    @StringDef({FULI, ANDROID, IOS, REST, EXTENDS, WEB, ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GankType {
    }

    public static final String IS_NIGHT = "is_night";
}
