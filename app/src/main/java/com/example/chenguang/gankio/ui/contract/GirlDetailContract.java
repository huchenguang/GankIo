package com.example.chenguang.gankio.ui.contract;

import android.content.Context;

import com.example.chenguang.gankio.base.BaseContract;

/**
 * Created by chenguang on 2017/10/31.
 */

public interface GirlDetailContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showDownloadResult(String result);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void downloadPicture(Context context, String title, String url);
    }
}
