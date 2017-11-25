package com.example.chenguang.gankio.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.bean.GirlItemData;
import com.example.chenguang.gankio.widget.ScaleImageView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by chenguang on 2017/10/19.
 */

public class WelfareAdapter extends RecyclerArrayAdapter<GirlItemData> {
    public WelfareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareViewHolder(parent, R.layout.item_welfare);
    }

    class WelfareViewHolder extends BaseViewHolder<GirlItemData> {

        private final ScaleImageView mImageView;

        public WelfareViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mImageView = $(R.id.girl_item_iv);
        }

        @Override
        public void setData(GirlItemData data) {
            mImageView.setInitSize(data.getWidth(), data.getHeight());
            Glide.with(getContext())
                    .load(data.getUrl())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.jiazaizhong)
                    .error(R.mipmap.error)
                    .into(mImageView);

        }
    }
}
