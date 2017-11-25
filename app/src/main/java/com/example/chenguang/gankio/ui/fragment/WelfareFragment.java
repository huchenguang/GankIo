package com.example.chenguang.gankio.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.adapter.WelfareAdapter;
import com.example.chenguang.gankio.base.BaseFragment;
import com.example.chenguang.gankio.base.BaseRVFragment;
import com.example.chenguang.gankio.bean.GankItemData;
import com.example.chenguang.gankio.bean.GirlItemData;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.component.DaggerGankIoComponent;
import com.example.chenguang.gankio.services.GirlService;
import com.example.chenguang.gankio.ui.activity.GirlDetailActivity;
import com.example.chenguang.gankio.ui.contract.WelfareContract;
import com.example.chenguang.gankio.ui.presenter.WelfarePresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2017/10/18.
 */

public class WelfareFragment extends BaseRVFragment<WelfarePresenter> implements WelfareContract
        .View, RecyclerArrayAdapter.OnItemClickListener, RecyclerArrayAdapter.OnMoreListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;
    private WelfareAdapter mAdapter;
    int currPage = 1;
    String mType = "";
    boolean isRefresh = false;
    private LinearLayout mErrorView;

    public static BaseFragment newInstance(String type) {
        WelfareFragment welfareFragment = new WelfareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        welfareFragment.setArguments(bundle);
        return welfareFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
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
        mType = getArguments().getString("type");
        mAdapter = new WelfareAdapter(mActivity);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceDecoration(SizeUtils.dp2px(2)));
        mRecyclerView.setErrorView(R.layout.view_net_error);
        mRecyclerView.setAdapterWithProgress(mAdapter = new WelfareAdapter(mActivity));
        mErrorView = (LinearLayout) mRecyclerView.getErrorView().findViewById(R.id
                .error_view);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        //加载更多
        mAdapter.setNoMore(R.layout.view_no_more);
        mAdapter.setMore(R.layout.view_load_more, this);

        mAdapter.setError(R.layout.view_load_more_error, new RecyclerArrayAdapter.OnErrorListener
                () {
            @Override
            public void onErrorShow() {
                mAdapter.pauseMore();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        //下拉刷新
        mRecyclerView.setRefreshListener(this);

        //设置点击事件的监听
        mAdapter.setOnItemClickListener(this);

        onRefresh();
    }

    @Override
    public void showError() {
        //刷新 没有获取到数据
        if (currPage == 1 && mAdapter.getCount() < 1) {
            mRecyclerView.showError();
        } else {
            mAdapter.pauseMore();
        }
        ToastUtils.showShort("网络好像出错了(=.=)");
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void showWealData(List<GankItemData> gankData, boolean isRefresh) {
        System.out.println("GankData:" + gankData.toString());
        GirlService.startService(mActivity, gankData);
        this.isRefresh = isRefresh;

    }


    @Override
    public void onItemClick(int position) {
        GirlItemData item = mAdapter.getItem(position);
        Intent intent = new Intent(mActivity, GirlDetailActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("url", item.getUrl());
        mActivity.startActivity(intent);
    }

    @Override
    public void onMoreShow() {
        mPresenter.getWealDataByPage(mType, currPage);
    }

    @Override
    public void onMoreClick() {
        mPresenter.getWealDataByPage(mType, currPage);
    }

    @Override
    public void onRefresh() {
        mRecyclerView.setRefreshing(true);
        currPage = 1;
        mPresenter.getWealDataByPage(mType, currPage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GirlEvent(List<GirlItemData> data) {
        System.out.println("GirlData:" + data.toString());
        if (this.isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(data);
        currPage += 1;
    }
}
