package com.example.chenguang.gankio.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenguang.gankio.R;
import com.example.chenguang.gankio.bean.VideoBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

/**
 * Created by chenguang on 2017/11/3.
 */

public class VideoAdapter extends RecyclerArrayAdapter<VideoBean> {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(parent, R.layout.item_video);
        myViewHolder.setController(new TxVideoPlayerController(parent.getContext()));
        return myViewHolder;
    }


    public class MyViewHolder extends BaseViewHolder<VideoBean> {
        public void setController(TxVideoPlayerController controller) {
            mController = controller;
            nv_player.setController(mController);
        }

        public TxVideoPlayerController mController;
        public TextView tv_time;
        public TextView tv_author;
        public TextView tv_comment;
        public NiceVideoPlayer nv_player;

        public MyViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            nv_player = $(R.id.nv_player);
            tv_time = $(R.id.tv_time);
            tv_author = $(R.id.tv_author);
            tv_comment = $(R.id.tv_comment);
            //16:9
            ViewGroup.LayoutParams params = nv_player.getLayoutParams();
            params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
            params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
            nv_player.setLayoutParams(params);
        }

        @Override
        public void setData(VideoBean data) {
            super.setData(data);
            /*VideoBean data = new VideoBean("11", "http://tanzi27niu.cdsb" +
                    ".mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-20-26.mp4",
                    "http://tanzi27niu.cdsb" +
                            ".mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-09-58.jpg",
                    "2017", "11", "2");*/
            mController.setTitle(data.title);
//            mController.setLenght(98000);
            Glide.with(getContext())
                    .load(data.imageUrl)
                    .centerCrop()
                    .into(mController.imageView());
            nv_player.setUp(data.url, null);


            tv_time.setText(data.time);
            tv_author.setText(data.author);
            tv_comment.setText(data.commentCount + "");
        }
    }
}
