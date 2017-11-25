package com.example.chenguang.gankio.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.adapter.TypeAdapter;
import com.example.chenguang.gankio.base.BaseFragment;
import com.example.chenguang.gankio.base.BaseRVFragment;
import com.example.chenguang.gankio.bean.GankItemData;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.component.DaggerGankIoComponent;
import com.example.chenguang.gankio.ui.activity.GankDetailActivity;
import com.example.chenguang.gankio.ui.contract.GankTypeContract;
import com.example.chenguang.gankio.ui.presenter.GankTypePresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2017/10/21.
 */

public class GankTypeFragment extends BaseRVFragment<GankTypePresenter> implements
        GankTypeContract.View, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout
        .OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview)
    public EasyRecyclerView mRecyclerView;
    private TypeAdapter mAdapter;
    int currPage = 1;
    String mType = "";
    private LinearLayout mErrorView;

    public static BaseFragment newInstance(String type) {
        GankTypeFragment gankTypeFragment = new GankTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        gankTypeFragment.setArguments(bundle);
        return gankTypeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_type;
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
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerDecoration(Color.LTGRAY, SizeUtils.px2dp(1)));
        mRecyclerView.setErrorView(R.layout.view_net_error);//没有缓存，也没有网络
        mRecyclerView.setAdapterWithProgress(mAdapter = new TypeAdapter(mActivity));

        mErrorView = (LinearLayout) mRecyclerView.getErrorView().findViewById(R.id
                .error_view);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        //加载更多
        mAdapter.setNoMore(R.layout.view_no_more);//没有更多。。。
        mAdapter.setMore(R.layout.view_load_more, this);//正在加载更多。。。

        mAdapter.setError(R.layout.view_load_more_error, new RecyclerArrayAdapter.OnErrorListener
                () {//加载更多失败，请重试
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
    public void showGankItem(List<GankItemData> data, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            if (data.size() < 1) {
                mRecyclerView.showEmpty();
                return;
            }
        }
        mAdapter.addAll(data);
        currPage++;
    }


    @Override
    public void onMoreShow() {
        mPresenter.getGankItemByPage(mType, currPage);
    }

    @Override
    public void onMoreClick() {
        mPresenter.getGankItemByPage(mType, currPage);
    }

    @Override
    public void onRefresh() {
        mRecyclerView.setRefreshing(true);
        currPage = 1;
        mPresenter.getGankItemByPage(mType, currPage);
    }

    @Override
    public void onItemClick(int position) {
        GankItemData item = mAdapter.getItem(position);
        Intent intent = new Intent(mActivity, GankDetailActivity.class);
        intent.putExtra("url", item.url);
        intent.putExtra("title", item.desc);
        mActivity.startActivity(intent);
    }
}
