package com.example.chenguang.gankio.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.chenguang.gankio.R;

import org.zackratos.ultimatebar.UltimateBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mActivity;
    private Unbinder unbind;
    protected UltimateBar mUltimateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mUltimateBar = new UltimateBar(this);
        mActivity = this;
        unbind = ButterKnife.bind(this);
        //设置透明状态栏
        setStatusBar();
        setUpComponent();
        initData();
        initView();
    }


    public abstract int getLayoutId();

    protected void setStatusBar() {
        mUltimateBar.setColorStatusBar(getResources().getColor(R.color.colorPrimaryDark)
                , 0);
    }

    protected void setUpComponent() {

    }

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        if (unbind != null) {
            unbind.unbind();
        }
        super.onDestroy();
    }

    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
/*        if (statusBarView != null) {
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }*/
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        /*if (statusBarView != null) {
            statusBarView.setBackgroundColor(statusBarColor);
        }*/
    }

    protected void startAnotherActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }
}
