package com.example.chenguang.gankio.ui.presenter;

import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.api.VideoApi;
import com.example.chenguang.gankio.bean.VideoBean;
import com.example.chenguang.gankio.ui.contract.VideoContract;
import com.example.chenguang.gankio.ui.fragment.VideoFragment;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenguang on 2017/11/3.
 */

public class VideoPresenter extends RxPresenter<VideoFragment> implements VideoContract
        .Presenter<VideoFragment> {
    GankApi mGankApi;

    @Inject
    public VideoPresenter(GankApi gankApi) {
        this.mGankApi = gankApi;
    }

    @Override
    public void getVideoData() {
        VideoApi.getVideoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<VideoBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError();
                    }

                    @Override
                    public void onNext(List<VideoBean> videoData) {
//                        System.out.println(videoData.toString());
                        mView.showVideoData(videoData);
                    }
                });
    }
}
