package com.example.chenguang.gankio;

import com.example.chenguang.gankio.bean.VideoBean;
import com.example.chenguang.gankio.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2017/11/4.
 */

public class JsoupTest {
    @Test
    public void test() throws Exception {
        List<VideoBean> videoBeans = new ArrayList<>();
        Document document = Jsoup.connect("https://news.uc.cn/c_shipin").timeout(5000).get();
        Element cate_videos = document.select("div.news-list").first();
        Elements videos = cate_videos.select("li.news-item");
        for (Element video : videos) {
            Element video_text = video.select("div.txt-area-title").select("a[href]").first();
            String url = video_text.attr("abs:href");
            System.out.println(url);
            Element video_main_body = Jsoup.connect(url).timeout(5000).get().select
                    ("div.sm-article-content").first();
            System.out.println(video_main_body);
            String image_url = video_main_body.attr("poster");
            String video_url = video_main_body.select("source").attr("src");
            String title = video_text.attr("title");

            Elements video_desc = video.select("div.txt-area-desc").select("span");
            String time = video_desc.get(0).text();
            String author = video_desc.get(1).text();
            String count = video_desc.get(2).select("a").text();

            System.out.println("video_url:" + video_url + ",image_url:" + image_url + ",title:" +
                    title + ",time:" + time + ",author:" +
                    author + ",count:" + count);
        }
        System.out.println("视频数量：" + videos.size());

    }

    @Test
    public void test2() throws Exception {
        String url = "https://news.uc.cn/a_14949778895634789467/";
        String aid = url.substring(url.indexOf("a_") + 2, url.length() - 1);
        Document document = Jsoup.connect(url).get();
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
        System.out.println(json);
        System.out.println(jsonObject.getString("message"));
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray videoList = data.getJSONArray("videoList");
        JSONArray fragment = videoList.getJSONObject(0).getJSONArray("fragment");
        String video_url = fragment.getJSONObject(0).getString("url");

        System.out.println(video_url);
    }

}
