package com.example.chenguang.gankio.api;

import com.example.chenguang.gankio.bean.VideoBean;
import com.example.chenguang.gankio.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by chenguang on 2017/11/4.
 */

public class VideoApi {


    public static Observable<List<VideoBean>> getVideoData() {

        return Observable.create(new Observable.OnSubscribe<List<VideoBean>>() {
            @Override
            public void call(Subscriber<? super List<VideoBean>> subscriber) {
                List<VideoBean> videoData = null;
                try {
                    videoData = getVideoDataByRandom();
                    if (videoData.size() < 5) {//保证每次能推荐至少5个视频
                        videoData.addAll(getVideoDataByRandom());
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(videoData);
                subscriber.onCompleted();
            }
        });
    }

    private static List<VideoBean> getVideoDataByRandom() throws IOException {
        List<VideoBean> videoData = new ArrayList<>();
        Document document = null;

        document = Jsoup.connect("https://news.uc.cn/c_shipin").timeout(5000).get();
        Element cate_videos = document.select("div.news-list").first();
        Elements videos = cate_videos.select("li.news-item");
        for (Element video : videos) {
            VideoBean videoBean = new VideoBean();
            Element video_text = video.select("div.txt-area-title").select("a[href]")
                    .first();
            String url = video_text.attr("abs:href");
            String[] videoUrlAndImageUrl = getVideoUrlAndImageUrl(url);

            videoBean.url = videoUrlAndImageUrl[0];
            videoBean.imageUrl = videoUrlAndImageUrl[1];

            String title = video_text.attr("title");
            videoBean.title = title;

            Elements video_desc = video.select("div.txt-area-desc").select("span");
            String time = video_desc.get(0).text();
            String author = video_desc.get(1).text();
            String count = video_desc.get(2).select("a").text();
            videoBean.time = time;
            videoBean.author = author;
            videoBean.commentCount = count;
            videoData.add(videoBean);
        }
        return videoData;
    }

    private static String[] getVideoUrlAndImageUrl(String url) {
        String imageUrl = "";
        String video_url = "";
        try {
            Document document = null;
            //        String url = "https://news.uc.cn/a_14949778895634789467/";
            String aid = url.substring(url.indexOf("a_") + 2, url.length() - 1);
            document = Jsoup.connect(url).get();
            String html = document.html();
            int fromIndex = html.indexOf("aid=" + aid);
            int startIndex = html.indexOf("original_url", fromIndex) + "original_url=".length();
            String original_url = html.substring(startIndex, html.indexOf("&", startIndex));

            startIndex = html.indexOf("url_sign", startIndex) + "url_sign\": \"".length() - 1;
            String url_sign = html.substring(startIndex, html.indexOf("\"", startIndex));
            String address = "https://iflow.uczzd" +
                    ".cn/iflow/api/v1/article/video/parse?app=UCtoutiaoPC-iflow&pageUrl=" +
                    original_url + "&url_sign=" + url_sign;
            String json = HttpUtil.sendHttpRequest(address);
            JSONObject jsonObject = new JSONObject(json);
//        System.out.println(json);
//        System.out.println(jsonObject.getString("message"));
            JSONObject data = (JSONObject) jsonObject.get("data");
            //image_url
            JSONArray posterList = data.getJSONArray("posterList");
            imageUrl = posterList.getJSONObject(0).getString("url");
            //video_url
            JSONArray videoList = data.getJSONArray("videoList");
            JSONArray fragment = videoList.getJSONObject(0).getJSONArray("fragment");
            video_url = fragment.getJSONObject(0).getString("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{video_url, imageUrl};

    }
}
