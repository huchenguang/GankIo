package com.example.chenguang.gankio.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.bean.GankItemData;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by chenguang on 2017/10/21.
 */

public class TypeAdapter extends RecyclerArrayAdapter<GankItemData> {
    public TypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent, R.layout.item_gank);
    }

    class MyViewHolder extends BaseViewHolder<GankItemData> {

        private final TextView tv_title;
        private final TextView tv_time;
        private final TextView tv_author;
        private final TextView tv_type;
        private final ImageView iv_image;

        public MyViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            tv_title = $(R.id.tv_title);
            tv_time = $(R.id.tv_time);
            tv_author = $(R.id.tv_author);
            iv_image = $(R.id.iv_image);
            tv_type = $(R.id.tv_type);
        }

        @Override
        public void setData(GankItemData data) {
            super.setData(data);
            tv_title.setText(data.desc);
            tv_author.setText(data.who);
            tv_type.setText(data.type);
            tv_time.setText(data.publishedAt.substring(0, 10));
            if (data.images != null) {
                iv_image.setVisibility(View.VISIBLE);
                Glide.with(getContext())
                        .load(data.images.get(0))
                        .fitCenter()
                        .placeholder(R.mipmap.jiazaizhong)
                        .error(R.mipmap.error)
                        .into(iv_image);
            } else {
                iv_image.setVisibility(View.GONE);
            }

        }
    }
}
