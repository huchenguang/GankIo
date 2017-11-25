package com.example.chenguang.gankio.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private Toolbar toolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
