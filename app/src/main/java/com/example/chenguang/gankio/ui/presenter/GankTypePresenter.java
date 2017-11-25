package com.example.chenguang.gankio.ui.presenter;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.bean.GankBean;
import com.example.chenguang.gankio.ui.contract.GankTypeContract;
import com.example.chenguang.gankio.ui.fragment.GankTypeFragment;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenguang on 2017/10/21.
 */

public class GankTypePresenter extends RxPresenter<GankTypeFragment> implements GankTypeContract
        .Presenter<GankTypeFragment> {
    GankApi mGankApi;

    @Inject
    public GankTypePresenter(GankApi gankApi) {
        this.mGankApi = gankApi;
    }

    @Override
    public void getGankItemByPage(String type, final int page) {
        Subscription subscribe = mGankApi.getGankDataByType(type, 10, page)
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
                        if (!NetworkUtils.isConnected()) {
                            mView.showError();
                        }
                        boolean isRefresh = page == 1 ? true : false;
                        mView.showGankItem(gankBean.results, isRefresh);
                    }
                });
        addSubscribe(subscribe);
    }
}
