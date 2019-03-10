package com.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jzvd.JzvdStd;
import com.bumptech.glide.Glide;
import com.news.R;
import com.news.bean.UserJoinVideo;
import com.news.bean.Video;
import com.news.itf.OnImageViewClick;
import com.news.itf.OnTextChange;
import com.news.itf.OnTextViewClick;
import com.news.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter {
    private List<Video> videoList;
    private Context context;
    private OnImageViewClick onImageViewClick;
    private OnTextViewClick onTextViewClick;
    private String loginName;

    public VideoAdapter(List<Video> videoList, Context context,String loginName) {
        this.videoList = videoList;
        this.context = context;
        this.loginName = loginName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_video, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        final Video video = videoList.get(i);

        myViewHolder.videoPlayerStandard.setUp(video.getUrl(), video.getTitle(), JzvdStd.SCREEN_WINDOW_LIST);
        myViewHolder.videoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        myViewHolder.tvName.setText(video.getName());

        if (loginName != null && !"".equals(loginName)){
             UserJoinVideo userJoinVideo = SQLUtils.queryVideoByUserNameAndVideoId(loginName,video.getVideoId());
             if (userJoinVideo!=null){
                 myViewHolder.tvVideoFocus.setText("已关注");
                 myViewHolder.tvVideoFocus.setTextColor(myViewHolder.itemView.getResources().getColor(R.color.colorMineFragmentDivider));
             }else {
                 myViewHolder.tvVideoFocus.setText("关注");
                 myViewHolder.tvVideoFocus.setTextColor(myViewHolder.itemView.getResources().getColor(R.color.colorAccent));
             }
        }else {
            myViewHolder.tvVideoFocus.setText("关注");
            myViewHolder.tvVideoFocus.setTextColor(myViewHolder.itemView.getResources().getColor(R.color.colorAccent));
        }


        Glide.with(context).load(video.getImage()).into(myViewHolder.videoPlayerStandard.thumbImageView);

        if (!"".equals(video.getIcon()))
            Glide.with(context).load(video.getIcon()).into(myViewHolder.ivVideoIcon);

        myViewHolder.ivVideoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick.onClick(v, i);
            }
        });

        myViewHolder.tvVideoFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextViewClick.onClick(myViewHolder.itemView, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private JzvdStd videoPlayerStandard;
        private TextView tvName, tvVideoFocus;
        private ImageView ivVideoIcon, ivVideoShare, ivVideoFocus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPlayerStandard = itemView.findViewById(R.id.jzv_std);
            tvName = itemView.findViewById(R.id.tv_video_name);
            ivVideoIcon = itemView.findViewById(R.id.iv_video_icon);
            ivVideoShare = itemView.findViewById(R.id.iv_video_share);
            tvVideoFocus = itemView.findViewById(R.id.tv_video_focus);
            ivVideoFocus = itemView.findViewById(R.id.iv_video_focus);
        }

    }

    public void setOnImageViewClick(OnImageViewClick onImageViewClick) {
        this.onImageViewClick = onImageViewClick;
    }

    public void setOnTextViewClick(OnTextViewClick onTextViewClick) {
        this.onTextViewClick = onTextViewClick;
    }

}
