package com.example.chenguang.gankio.ui.presenter;

import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.bean.GankBean;
import com.example.chenguang.gankio.ui.activity.SearchActivity;
import com.example.chenguang.gankio.ui.contract.SearchContract;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenguang on 2017/11/21.
 */

public class SearchPresenter extends RxPresenter<SearchActivity> implements SearchContract
        .Presenter<SearchActivity> {
    private GankApi mGankApi;

    public SearchPresenter(GankApi gankApi) {
        this.mGankApi = gankApi;
    }

    @Override
    public void searchGankData(String key) {
        Subscription subscribe = mGankApi.searchGankData(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onNext(GankBean gankBean) {
                        mView.showSearchResult(gankBean.results);
                    }
                });
        addSubscribe(subscribe);
    }
}
