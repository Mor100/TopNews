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

public class MiddleImageRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<NewsBean> newsList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public MiddleImageRecyclerViewAdapter(List<NewsBean> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final ViewHolderMiddleImage middleImage = new ViewHolderMiddleImage(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_list_middel_image, viewGroup, false));
        return middleImage;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolderMiddleImage middleImage = (ViewHolderMiddleImage) viewHolder;
        final NewsBean news = newsList.get(i);
        //如果没有第二张图片(只有一张图片)
        if (news.getImageUrl2() == null || news.getImageUrl2().equals("null")) {
            middleImage.tvMiddleTitle.setText(news.getTitle());
            middleImage.tvMiddleAuthor.setText(news.getAuthor());
            middleImage.tvMiddleTime.setText(news.getTime());

            Glide.with(context).load(news.getImageUrl()).into(middleImage.ivMiddle);
            Glide.with(context).load(R.drawable.placeholder).into(middleImage.ivMiddle2);
            Glide.with(context).load(R.drawable.placeholder).into(middleImage.ivMiddle3);
        } else {
            //如果没有第三张图片（有两张图片）
            if (news.getImageUrl3() == null || news.getImageUrl3().equals("null")) {
                middleImage.tvMiddleTitle.setText(news.getTitle());
                middleImage.tvMiddleAuthor.setText(news.getAuthor());
                middleImage.tvMiddleTime.setText(news.getTime());

                Glide.with(context).load(news.getImageUrl()).into(middleImage.ivMiddle);
                Glide.with(context).load(news.getImageUrl2()).into(middleImage.ivMiddle2);
                Glide.with(context).load(R.drawable.placeholder).into(middleImage.ivMiddle3);
            } else {
                //（有三张图片）
                middleImage.tvMiddleTitle.setText(news.getTitle());
                middleImage.tvMiddleAuthor.setText(news.getAuthor());
                middleImage.tvMiddleTime.setText(news.getTime());

                Glide.with(context).load(news.getImageUrl()).into(middleImage.ivMiddle);
                Glide.with(context).load(news.getImageUrl2()).into(middleImage.ivMiddle2);
                Glide.with(context).load(news.getImageUrl3()).into(middleImage.ivMiddle3);
            }
        }
        middleImage.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,i);
            }
        });
        middleImage.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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

    class ViewHolderMiddleImage extends RecyclerView.ViewHolder {
        ImageView ivMiddle, ivMiddle2, ivMiddle3;
        TextView tvMiddleTitle, tvMiddleAuthor, tvMiddleTime;

        public ViewHolderMiddleImage(@NonNull View itemView) {
            super(itemView);
            ivMiddle = itemView.findViewById(R.id.iv_middle1);
            ivMiddle2 = itemView.findViewById(R.id.iv_middle2);
            ivMiddle3 = itemView.findViewById(R.id.iv_middle3);
            tvMiddleTitle = itemView.findViewById(R.id.tv_middle_title);
            tvMiddleAuthor = itemView.findViewById(R.id.tv_middle_author);
            tvMiddleTime = itemView.findViewById(R.id.tv_middle_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
