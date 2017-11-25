package com.example.chenguang.gankio.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    private boolean flag = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_skip.postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        }, 2000);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mUltimateBar.setHideBar(true);
        }
    }
}
