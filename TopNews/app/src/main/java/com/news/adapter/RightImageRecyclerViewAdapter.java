package com.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.news.R;
import com.news.bean.NewsBean;
import com.news.itf.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RightImageRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<NewsBean> newsList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public RightImageRecyclerViewAdapter(List<NewsBean> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolderRightImage rightImage = new ViewHolderRightImage(LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_list_right_image,viewGroup,false));

        return rightImage;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolderRightImage rightImage = (ViewHolderRightImage) viewHolder;
        rightImage.tvRightTitle.setText(newsList.get(i).getTitle());
        rightImage.tvRightAuthor.setText(newsList.get(i).getAuthor());
        rightImage.tvRightTime.setText(newsList.get(i).getTime());
        final Map<String,Bitmap> bitmapMap=new HashMap<>();

        Glide.with(context).load(newsList.get(i).getImageUrl()).into(rightImage.ivRight);

        rightImage.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,i);
            }
        });
        rightImage.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongClick(i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolderRightImage extends RecyclerView.ViewHolder {
        ImageView ivRight;
        TextView tvRightTitle,tvRightAuthor,tvRightTime;

        public ViewHolderRightImage(@NonNull View itemView) {
            super(itemView);
            ivRight = itemView.findViewById(R.id.iv_right);
            tvRightTitle = itemView.findViewById(R.id.tv_right_title);
            tvRightAuthor = itemView.findViewById(R.id.tv_right_author);
            tvRightTime = itemView.findViewById(R.id.tv_right_time);
        }
    }
}
