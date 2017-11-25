package com.example.chenguang.gankio.ui.activity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseActivity;
import com.example.chenguang.gankio.ui.contract.GirlDetailContract;
import com.example.chenguang.gankio.ui.presenter.GirlDetailPresenter;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.bumptech.glide.Glide.with;

/**
 * Created by chenguang on 2017/10/30.
 */

public class GirlDetailActivity extends BaseActivity implements GirlDetailContract.View {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.pv_girl)
    PhotoView pv_girl;
    String title = "";
    String url = "";
    boolean isHiden = false;
    GirlDetailPresenter mPresenter = new GirlDetailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_girl;
    }


    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        //toolbar
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.setAlpha(0.6f);
        //photoView
        with(this)
                .load(url)
                .into(pv_girl);
        pv_girl.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                switchBarMode();
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
                        mPresenter.downloadPicture(mActivity, title, url);
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void switchBarMode() {
        if (!isHiden) {
            hideStatusBar();
            mUltimateBar.setColorStatusBar(Color.BLACK);
        } else {
            showStatusBar();
            mUltimateBar.setColorStatusBar(getResources().getColor(R.color.colorPrimaryDark), 102);
        }
        ObjectAnimator.ofFloat(appBarLayout, "translationY", isHiden ? 0 : -toolbar.getHeight())
                .setDuration(300)
                .start();
        isHiden = !isHiden;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
