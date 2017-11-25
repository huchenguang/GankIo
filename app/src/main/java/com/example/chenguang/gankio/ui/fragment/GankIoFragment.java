package com.example.chenguang.gankio.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseFragment;
import com.example.chenguang.gankio.component.AppComponent;
import com.example.chenguang.gankio.utils.ConstantValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2017/9/12.
 */

public class GankIoFragment extends BaseFragment {
    @BindView(R.id.tl_home)
    TabLayout tl_home;
    @BindView(R.id.vp_home)
    ViewPager vp_home;

    private List<String> mTabName;
    private List<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_io;
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
    }


    @Override
    protected void attachView() {

    }

    @Override
    protected void initData() {
        mTabName = new ArrayList<>();
        Collections.addAll(mTabName, getResources().getStringArray(R.array.gank_type));

        mFragments = new ArrayList<>();

        for (int i = 0; i < mTabName.size(); i++) {
            if (i == 3) {
                mFragments.add(VideoFragment.newInstance());
                continue;
            }

            if (i == 5) {
                mFragments.add(WelfareFragment.newInstance(ConstantValues.TYPES[i]));
                continue;
            }

            mFragments.add(GankTypeFragment.newInstance(ConstantValues.TYPES[i]));
        }
    }

    @Override
    protected void initView() {
        vp_home.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabName.get(position);
            }
        });
        tl_home.setupWithViewPager(vp_home);
        tl_home.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /**
             * 当tab第一次选择时候调用
             * @param tab
             */

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            /**
             * 当tab从 选择 ->未选择
             * @param tab
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            /**
             * 当tab已是选择状态时，继续点击调用此方法
             * @param tab
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                BaseFragment fragment = (BaseFragment) mFragments.get(tab.getPosition());
                if (fragment != null) {
                    fragment.onRefresh();
                }
            }
        });
    }

    public static BaseFragment newInstance() {
        return new GankIoFragment();
    }
}
