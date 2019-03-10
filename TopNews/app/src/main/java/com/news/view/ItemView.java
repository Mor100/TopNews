package com.news.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.R;

public class ItemView extends LinearLayout {
    private TextView tvTitle;
    private ImageView ivIcon;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.item_view,this);
        tvTitle = findViewById(R.id.tv_title);
        ivIcon = findViewById(R.id.iv_icon);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemView);
        tvTitle.setText(array.getString(R.styleable.ItemView_item_text));
        ivIcon.setImageDrawable(array.getDrawable(R.styleable.ItemView_item_icon));
        array.recycle();
    }

    private void setImgRecourse(int resId){
        ivIcon.setImageResource(resId);
    }
}
