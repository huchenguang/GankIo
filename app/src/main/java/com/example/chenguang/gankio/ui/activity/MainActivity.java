package com.example.chenguang.gankio.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.application.MyApplication;
import com.example.chenguang.gankio.base.BaseActivity;
import com.example.chenguang.gankio.base.BaseFragment;
import com.example.chenguang.gankio.ui.fragment.GankIoFragment;
import com.example.chenguang.gankio.utils.ConstantValues;
import com.example.chenguang.gankio.widget.CircleImageView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.search_view)
    MaterialSearchView search_view;

    private ImageView iv_switch_mode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {
        mUltimateBar.setColorStatusBarForDrawer(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void setUpComponent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initDrawerLayout();
        initNavigationView();
        initSearchView();

        replace(GankIoFragment.newInstance());
        nav_view.setCheckedItem(R.id.nav_gank_io);
    }

    private void initSearchView() {
        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.putExtra("key", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void initDrawerLayout() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void initNavigationView() {
        View headerView = nav_view.getHeaderView(0);
        CircleImageView iv_header = (CircleImageView) headerView.findViewById(R.id.iv_header);
        iv_switch_mode = (ImageView) headerView.findViewById(R.id.iv_switch_mode);

        if (MyApplication.getSp().getBoolean(ConstantValues.IS_NIGHT)) {
            iv_switch_mode.setImageResource(R.mipmap.ic_switch_daily);
        } else {
            iv_switch_mode.setImageResource(R.mipmap.ic_switch_night);
        }

        iv_header.setOnClickListener(this);
        iv_switch_mode.setOnClickListener(this);

        nav_view.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_item_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        search_view.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.search:
//                startAnotherActivity(SearchActivity.class);
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gank_io) {
            replace(GankIoFragment.newInstance());
        } else if (id == R.id.nav_another) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {
            startAnotherActivity(AboutActivity.class);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void replace(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header:
                ToastUtils.showShort("你点击了头像！");
                break;
            case R.id.iv_switch_mode:
                boolean flag = MyApplication.getSp().getBoolean(ConstantValues.IS_NIGHT,
                        false);
                MyApplication.setMode(!flag);
                MyApplication.getSp().put(ConstantValues.IS_NIGHT, !flag);
                recreate();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
