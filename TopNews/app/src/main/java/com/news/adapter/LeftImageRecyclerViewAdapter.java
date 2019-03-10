package com.news.adapter;

import android.content.Context;
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

import java.util.List;

public class LeftImageRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<NewsBean> newsList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public LeftImageRecyclerViewAdapter(List<NewsBean> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolderLeftImage leftImage = new ViewHolderLeftImage(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_left_image, viewGroup, false));
        return leftImage;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolderLeftImage leftImage = (ViewHolderLeftImage) viewHolder;
        final NewsBean news = newsList.get(i);
        leftImage.tvLeftTitle.setText(news.getTitle());
        leftImage.tvLeftAuthor.setText(news.getAuthor());
        leftImage.tvLeftTime.setText(news.getTime());

        Glide.with(context).load(news.getImageUrl()).into(leftImage.ivLeft);

        leftImage.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,i);
            }
        });
        leftImage.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolderLeftImage extends RecyclerView.ViewHolder {
        ImageView ivLeft;
        TextView tvLeftTitle, tvLeftAuthor, tvLeftTime;

        public ViewHolderLeftImage(@NonNull View itemView) {
            super(itemView);
            ivLeft = itemView.findViewById(R.id.iv_left);
            tvLeftTitle = itemView.findViewById(R.id.tv_left_title);
            tvLeftAuthor = itemView.findViewById(R.id.tv_left_author);
            tvLeftTime = itemView.findViewById(R.id.tv_left_time);
        }

    }
}
