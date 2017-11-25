package com.example.chenguang.gankio.ui.contract;

import com.example.chenguang.gankio.base.BaseContract;
import com.example.chenguang.gankio.bean.GankItemData;

import java.util.List;

/**
 * Created by chenguang on 2017/9/15.
 */

public interface WelfareContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showWealData(List<GankItemData> gankData, boolean isRefresh);
    }

    interface Presenter<T extends BaseContract.BaseView> extends BaseContract.BasePresenter<T> {
        void getWealDataByPage(String type, int page);
    }
}
