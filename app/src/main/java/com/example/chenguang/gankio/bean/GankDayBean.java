package com.example.chenguang.gankio.bean;

import java.util.List;

/**
 * Created by chenguang on 2017/7/26.
 */

/**
 * {
 * "category": [
 * "iOS",
 * "Android",
 * "瞎推荐",
 * "拓展资源",
 * "福利",
 * "休息视频"
 * ],
 * "error": false,
 * "results":
 */
public class GankDayBean {
    public List<String> category;
    public boolean error;
    public CategoryData results;

    /**
     * "results": {
     * "Android": [
     * {
     * "_id": "56cc6d23421aa95caa707a69",
     * "createdAt": "2015-08-06T07:15:52.65Z",
     * "desc": "类似Link Bubble的悬浮式操作设计",
     * "publishedAt": "2015-08-07T03:57:48.45Z",
     * "type": "Android",
     * "url": "https://github.com/recruit-lifestyle/FloatingView",
     * "used": true,
     * "who": "mthli"
     * },
     * <p>
     * "iOS": [
     * {
     * "_id": "56cc6d1d421aa95caa707769",
     * "createdAt": "2015-08-07T01:32:51.588Z",
     * "desc": "LLVM 简介",
     * "publishedAt": "2015-08-07T03:57:48.70Z",
     * "type": "iOS",
     * "url": "http://adriansampson.net/blog/llvm.html",
     * "used": true,
     * "who": "CallMeWhy"
     * },....
     */
    public class CategoryData {
        List<GankItemData> Android;
        List<GankItemData> iOS;
        List<GankItemData> 休息视频;
        List<GankItemData> 拓展资源;
        List<GankItemData> 瞎推荐;
        List<GankItemData> 福利;
    }
}
