package com.example.chenguang.gankio.ui.contract;

import com.example.chenguang.gankio.base.BaseContract;
import com.example.chenguang.gankio.bean.VideoBean;

import java.util.List;

/**
 * Created by chenguang on 2017/11/3.
 */

public interface VideoContract extends BaseContract {
    interface View extends BaseView {
        public void showVideoData(List<VideoBean> videoData);
    }

    interface Presenter<T> extends BasePresenter<T> {
        public void getVideoData();
    }
}
