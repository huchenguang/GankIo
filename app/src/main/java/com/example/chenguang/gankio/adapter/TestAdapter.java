package com.example.chenguang.gankio.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.bean.VideoBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by chenguang on 2017/11/3.
 */

public class TestAdapter extends RecyclerArrayAdapter<VideoBean> {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent, R.layout.item_test);
    }

    class MyViewHolder extends BaseViewHolder<VideoBean> {


        private final TextView tv;

        public MyViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            tv = $(R.id.tv_comment);
        }

        @Override
        public void setData(VideoBean data) {
            super.setData(data);
            tv.setText(data.commentCount + "");
        }
    }
}
