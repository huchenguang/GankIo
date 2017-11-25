package com.example.chenguang.gankio.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.chenguang.gankio.bean.GankItemData;
import com.example.chenguang.gankio.bean.GirlItemData;
import com.example.chenguang.gankio.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2017/10/28.
 */

public class GirlService extends IntentService {
    public GirlService() {
        super("");
    }

    public static void startService(Context context, List<GankItemData> gankItemDatas) {
        Intent intent = new Intent(context, GirlService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) gankItemDatas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        List<GankItemData> gankItemDatas = intent.getParcelableArrayListExtra("data");
        handleGirlItemData(gankItemDatas);
    }

    private void handleGirlItemData(List<GankItemData> datas) {
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        ArrayList<GirlItemData> girlItemDatas = new ArrayList<>();
        for (GankItemData data : datas) {
            GirlItemData girlItemData = new GirlItemData();
            Bitmap load = ImageLoader.load(this, data.url);
            if (load != null) {
                girlItemData.setHeight(load.getHeight());
                girlItemData.setWidth(load.getWidth());
            }
            girlItemData.setUrl(data.url);
            girlItemData.setTitle(data.desc);
            girlItemDatas.add(girlItemData);
        }
        EventBus.getDefault().post(girlItemDatas);
    }
}
