package com.example.chenguang.gankio.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseFragment;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.ui.activity.GirlDetailActivity;
import com.example.chenguang.gankio.ui.contract.GirlDetailContract;
import com.example.chenguang.gankio.ui.presenter.GirlDetailPresenter;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by chenguang on 2017/12/7.
 */

public class GirlDetailFragment extends BaseFragment implements GirlDetailContract.View {
    @BindView(R.id.pv_girl)
    PhotoView pv_girl;
    String url = "";
    String title = "";
    GirlDetailPresenter mPresenter = new GirlDetailPresenter();

    public static GirlDetailFragment newInstance(String url, String title) {
        GirlDetailFragment girlDetailFragment = new GirlDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        girlDetailFragment.setArguments(bundle);
        return girlDetailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl_detail;
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        url = getArguments().getString("url");
        title = getArguments().getString("title");
    }

    @Override
    protected void initView() {
        //photoView
        Glide.with(this)
                .load(url)
                .into(pv_girl);
        pv_girl.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ((GirlDetailActivity) mActivity).switchBarMode();
            }
        });
        pv_girl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDownloadDialog();
                return false;
            }
        });
    }

    private void showDownloadDialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle("下载")
                .setMessage("确定要下载该图片吗")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.downloadPicture
                                (mActivity, title, url);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showError() {
        ToastUtils.showShort("下载出错了...");
    }

    @Override
    public void complete() {

    }

    @Override
    public void showDownloadResult(String result) {
        ToastUtils.showShort(result);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
