package com.news.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.news.R;

public class RegisterDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private View view;
    private ImageView ivDialogClose;

    public RegisterDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public RegisterDialog(@NonNull Context context, int themeResId, View view) {
        super(context, themeResId);
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        initView();
    }

    private void initView() {
        ivDialogClose = view.findViewById(R.id.iv_dialog_close_register);
        ivDialogClose.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        animatorShow();
    }

    public void animatorDismiss() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", 0, getWindowWidth()).setDuration(800));
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                RegisterDialog.this.dismiss();
                ViewGroup parent = (ViewGroup) RegisterDialog.this.view.getParent();
                parent.removeAllViews();
            }
        });
    }

    private void animatorShow() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view,"translationY",0,40).setDuration(100));
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", getWindowWidth(), 0).setDuration(800));
        set.start();
    }

    private int getWindowHeight() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    private int getWindowWidth() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_dialog_close_register:
                this.animatorDismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        animatorDismiss();
    }
}
