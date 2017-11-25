package com.example.chenguang.gankio.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.blankj.utilcode.util.SDCardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.chenguang.gankio.ui.activity.GirlDetailActivity;
import com.example.chenguang.gankio.ui.contract.GirlDetailContract;

import java.io.File;
import java.io.FileOutputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chenguang on 2017/10/31.
 */

public class GirlDetailPresenter extends RxPresenter<GirlDetailActivity> implements
        GirlDetailContract.Presenter<GirlDetailActivity> {
    @Override
    public void downloadPicture(final Context context, final String title, final String url) {
        Observable.create(new rx.Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context).load(url).asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    if (bitmap == null) {
                        subscriber.onError(new Exception("无法下载到图片"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).map(new Func1<Bitmap, String>() {
            @Override
            public String call(Bitmap bitmap) {
                File file;
                if (SDCardUtils.isSDCardEnable()) {//sd卡是否可用
                    File meizi = new File(Environment.getExternalStorageDirectory(), "meizi");
                    if (!meizi.exists()) {
                        meizi.mkdir();
                    }
                    String s = title.replace('/', '.') + ".jpg";
                    file = new File(meizi, s);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }

                } else {
                    return "请插入sd卡";
                }
                return "成功下载图片至" + file.getAbsolutePath();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showDownloadResult(s);
                    }
                });
    }
}
