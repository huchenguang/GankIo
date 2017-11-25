package com.example.chenguang.gankio.ui.presenter;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.bean.GankBean;
import com.example.chenguang.gankio.ui.contract.WelfareContract;
import com.example.chenguang.gankio.ui.fragment.WelfareFragment;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenguang on 2017/10/18.
 */

public class WelfarePresenter extends RxPresenter<WelfareFragment> implements WelfareContract
        .Presenter<WelfareFragment> {
    private GankApi mGankApi;

    @Inject
    public WelfarePresenter(GankApi gankApi) {
        this.mGankApi = gankApi;
    }

    @Override
    public void getWealDataByPage(String type, final int page) {
        Subscription subscribe = mGankApi.getGankDataByType(type, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBean>() {
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
                        boolean isRefresh = page == 0 ? true : false;
                        mView.showWealData(gankBean.results, isRefresh);
                    }
                });
        addSubscribe(subscribe);
    }


}
