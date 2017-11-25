package com.example.chenguang.gankio.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.adapter.TypeAdapter;
import com.example.chenguang.gankio.api.GankApi;
import com.example.chenguang.gankio.base.BaseActivity;
import com.example.chenguang.gankio.bean.GankItemData;
import com.example.chenguang.gankio.ui.contract.SearchContract;
import com.example.chenguang.gankio.ui.presenter.SearchPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements SearchContract.View,
        RecyclerArrayAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;
    private SearchPresenter mPresenter;
    private String key;
    private TypeAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        key = getIntent().getStringExtra("key");
        mPresenter = new SearchPresenter(GankApi.getDefaultInstance());
    }

    @Override
    protected void initView() {
        initToolbar();
        initRecyclerView();
        mPresenter.attachView(this);
        mPresenter.searchGankData(key);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerDecoration(Color.LTGRAY, SizeUtils.px2dp(5),
                0, 0));
        mRecyclerView.setErrorView(R.layout.view_net_error);
        mRecyclerView.setAdapterWithProgress(mAdapter = new TypeAdapter(mActivity));
        LinearLayout mErrorView = (LinearLayout) mRecyclerView.getErrorView().findViewById(R.id
                .error_view);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchGankData(key);
            }
        });
        mAdapter.setOnItemClickListener(this);
    }

    private void initToolbar() {
        toolbar.setTitle(key);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showError() {
        mRecyclerView.showError();
    }

    @Override
    public void complete() {

    }


    @Override
    public void showSearchResult(List<GankItemData> gankData) {
        mAdapter.addAll(gankData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
