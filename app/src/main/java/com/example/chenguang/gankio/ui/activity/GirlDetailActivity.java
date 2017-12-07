package com.example.chenguang.gankio.ui.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseActivity;
import com.example.chenguang.gankio.bean.GirlItemData;
import com.example.chenguang.gankio.ui.fragment.GirlDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2017/10/30.
 */

public class GirlDetailActivity extends BaseActivity {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    String title = "";
    boolean isHiden = false;

    private List<GirlItemData> mData;
    private int position;
    private List<Fragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_girl;
    }


    @Override
    protected void initData() {
        mData = getIntent().getParcelableArrayListExtra("GirlItemDatas");
        position = getIntent().getIntExtra("position", 0);
        title = mData.get(position).getTitle();

        mFragments = new ArrayList<>();
        for (GirlItemData data : mData) {
            mFragments.add(GirlDetailFragment.newInstance(data.getUrl(), data.getTitle()));
        }

    }

    @Override
    protected void initView() {
        //toolbar
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.setAlpha(0.6f);
        //viewPager
        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
        view_pager.setCurrentItem(position);
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
    protected void onDestroy() {
        super.onDestroy();

    }

    public void switchBarMode() {
        if (!isHiden) {
            hideStatusBar();
            mUltimateBar.setColorStatusBar(Color.BLACK);
        } else {
            showStatusBar();
            mUltimateBar.setColorStatusBar(getResources().getColor(R
                    .color.colorPrimaryDark), 102);
        }
        ObjectAnimator.ofFloat(appBarLayout, "translationY",
                isHiden ? 0 : -toolbar.getHeight())
                .setDuration(300)
                .start();
        isHiden = !isHiden;
    }

}
