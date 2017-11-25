package com.example.chenguang.gankio.bean;

/**
 * Created by chenguang on 2017/7/25.
 */

import java.util.List;

/**
 * {
 * "error": false,
 * "results": [
 * {
 * "_id": "5976f07c421aa90ca3bb6b66",
 * "createdAt": "2017-07-25T15:17:16.832Z",
 * "desc": "Android 面包屑导航。",
 * "images": [
 * "http://img.gank.io/d3283249-cb2d-4251-9ed2-ac5e08d18ed4"
 * ],
 * "publishedAt": "2017-07-25T15:25:42.391Z",
 * "source": "chrome",
 * "type": "Android",
 * "url": "https://github.com/fython/BreadcrumbsView",
 * "used": true,
 * "who": "代码家"
 * },
 */
public class GankBean {
    public boolean error;
    public List<GankItemData> results;


}
