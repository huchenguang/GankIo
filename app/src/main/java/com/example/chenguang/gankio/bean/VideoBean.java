package com.example.chenguang.gankio.bean;

/**
 * Created by chenguang on 2017/11/3.
 */

public class VideoBean {
    public String title;
    public String url;
    public String imageUrl;
    public String time;
    public String author;
    public String commentCount;

    public VideoBean() {
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }

    public VideoBean(String title, String url, String imageUrl, String time, String author, String
            commentCount) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.time = time;
        this.author = author;
        this.commentCount = commentCount;
    }
}
