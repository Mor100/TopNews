package com.news.util;

import android.support.v4.app.FragmentActivity;
import com.mylhyl.superdialog.SuperDialog;

public class DialogUtils{

    public static void showDialog(FragmentActivity activity, String title, String content, SuperDialog.OnClickPositiveListener listener,
                                   SuperDialog.OnClickNegativeListener listener2) {
        SuperDialog.Builder builder = new SuperDialog.Builder(activity)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", listener).setNegativeButton("取消", listener2);
        builder.build();
    }
}
