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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.news.R;

public class LoginDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private View view;
    private ImageView ivDialogClose;

    public LoginDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public LoginDialog(@NonNull Context context, int themeResId, View view) {
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
        ivDialogClose = view.findViewById(R.id.iv_dialog_close);
        ivDialogClose.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        animatorShow();
    }

    public void animatorDismiss() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "translationY", 40, getWindowHeight()).setDuration(500));
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LoginDialog.this.dismiss();
                ViewGroup parent = (ViewGroup) LoginDialog.this.view.getParent();
                parent.removeAllViews();
            }
        });
    }

    private void animatorShow() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "translationY", getWindowHeight(), 40).setDuration(500));
        set.start();
    }

    private int getWindowHeight() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        Log.i("屏幕高度", String.valueOf(metrics.heightPixels));
        return metrics.heightPixels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tv_dialog_register:
//                new RegisterDialog(context,R.style.LoginDialog, LayoutInflater.from(context).inflate(
//                        R.layout.dialog_register,null
//                )).show();
//                animatorDismiss();
//                break;
            case R.id.iv_dialog_close:
                animatorDismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        LoginDialog.this.dismiss();
//        ViewGroup parent = (ViewGroup) LoginDialog.this.view.getParent();
//        parent.removeAllViews();

        animatorDismiss();
    }
}
