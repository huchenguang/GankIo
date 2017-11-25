package com.example.chenguang.gankio.base;

import javax.inject.Inject;

/**
 * Created by chenguang on 2017/9/14 .
 */

public abstract class BaseRVFragment<T extends BaseContract.BasePresenter> extends BaseFragment {
    @Inject
    protected T mPresenter;
    /**
     * 不可再重写
     */
    @Override
    protected void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
