package com.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jzvd.JzvdStd;
import com.bumptech.glide.Glide;
import com.news.R;
import com.news.bean.Video;
import com.news.itf.OnImageViewClick;
import com.news.itf.OnTextViewClick;

import java.util.List;

public class VideoAdapterForMyFocus extends RecyclerView.Adapter {
    private List<Video> videoList;
    private Context context;
    private OnImageViewClick onImageViewClick;

    public VideoAdapterForMyFocus(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_video2, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        final Video video = videoList.get(i);

        myViewHolder.videoPlayerStandard.setUp(video.getUrl(), video.getTitle(), JzvdStd.SCREEN_WINDOW_LIST);
        myViewHolder.videoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        myViewHolder.tvName.setText(video.getName());
        Glide.with(context).load(video.getImage()).into(myViewHolder.videoPlayerStandard.thumbImageView);

        if (!"".equals(video.getIcon()))
            Glide.with(context).load(video.getIcon()).into(myViewHolder.ivVideoIcon);

        myViewHolder.ivVideoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick.onClick(v,i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private JzvdStd videoPlayerStandard;
        private TextView tvName;
        private ImageView ivVideoIcon, ivVideoDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPlayerStandard = itemView.findViewById(R.id.jzv_std);
            tvName = itemView.findViewById(R.id.tv_video_name);
            ivVideoIcon = itemView.findViewById(R.id.iv_video_icon);
            ivVideoDelete = itemView.findViewById(R.id.iv_video_delete);
        }

    }

    public void setOnImageViewClick(OnImageViewClick onImageViewClick){
        this.onImageViewClick = onImageViewClick;
    }

}
