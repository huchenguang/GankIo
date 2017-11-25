package com.example.chenguang.gankio.ui.contract;

import com.example.chenguang.gankio.base.BaseContract;
import com.example.chenguang.gankio.bean.GankItemData;

import java.util.List;

/**
 * Created by chenguang on 2017/10/21.
 */

public interface GankTypeContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showGankItem(List<GankItemData> data, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getGankItemByPage(String type, int page);
    }
}
