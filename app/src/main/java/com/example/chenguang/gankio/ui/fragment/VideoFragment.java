package com.example.chenguang.gankio.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.adapter.VideoAdapter;
import com.example.chenguang.gankio.base.BaseRVFragment;
import com.example.chenguang.gankio.bean.VideoBean;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.component.DaggerGankIoComponent;
import com.example.chenguang.gankio.ui.contract.VideoContract;
import com.example.chenguang.gankio.ui.presenter.VideoPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2017/11/3.
 */

public class VideoFragment extends BaseRVFragment<VideoPresenter> implements SwipeRefreshLayout
        .OnRefreshListener, VideoContract.View {
    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;
    VideoAdapter mAdapter;
    private LinearLayout mErrorView;
    boolean isFirstLoad = true;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
        DaggerGankIoComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerDecoration(Color.LTGRAY, SizeUtils.px2dp(5),
                0, 0));
        mRecyclerView.setErrorView(R.layout.view_net_error);
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoAdapter(mActivity));

        mErrorView = (LinearLayout) mRecyclerView.getErrorView().findViewById(R.id.error_view);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        //下拉刷新
        mRecyclerView.setRefreshListener(this);

        mRecyclerView.getRecyclerView().setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoAdapter.MyViewHolder) holder).nv_player;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance()
                        .getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });

        onRefresh();
        isFirstLoad = true;

    }


    @Override
    public void onRefresh() {
        if (!isFirstLoad) {
            mRecyclerView.setRefreshing(true);
        }
        isFirstLoad = false;
        mPresenter.getVideoData();
    }

    @Override
    public void showError() {
        ToastUtils.showShort("网络异常。");
        if (mAdapter.getCount() < 1) {
            mRecyclerView.showError();
        }
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void showVideoData(List<VideoBean> videoData) {
        //将数据添加到头部
        mAdapter.insertAll(videoData, 0);
        mRecyclerView.scrollToPosition(0);
        ToastUtils.showShort("为你推荐了" + videoData.size() + "个视频.");

    }
}
