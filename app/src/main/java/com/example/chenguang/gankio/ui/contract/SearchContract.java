package com.example.chenguang.gankio.ui.contract;

import com.example.chenguang.gankio.base.BaseContract;
import com.example.chenguang.gankio.bean.GankItemData;
import com.example.chenguang.gankio.ui.activity.SearchActivity;

import java.util.List;

/**
 * Created by chenguang on 2017/11/21.
 */

public interface SearchContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showSearchResult(List<GankItemData> gankData);
    }

    interface Presenter<T extends SearchActivity> extends BaseContract.BasePresenter<T> {
        void searchGankData(String key);
    }
}
