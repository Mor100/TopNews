package com.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {
    private TextView tvStart;
    private int second = 5;
    private SharedPreferences preferences;
    private String loginName;

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        shortcut(this);

        tvStart = findViewById(R.id.tv_start);

        getUserNameFromSharedPreferences();

        final Timer timer = new Timer();


        onPageJump(timer);

        onTextViewClick(tvStart, timer);

        setStateBarColor();

    }

    void onPageJump(final Timer timer) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        second--;
                        tvStart.setText("跳过 " + second);
                        if (second == 1) {
                            startActivity(new Intent(StartActivity.this, MainActivity.class)
                                    .putExtra("loginName",loginName));
                            timer.cancel();
                            finish();
                        }
                    }
                });

            }
        }, 0, 1000);
    }

    void onTextViewClick(TextView tvStart, final Timer timer) {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                startActivity(new Intent(StartActivity.this, MainActivity.class)
                        .putExtra("loginName",loginName));
                finish();
            }
        });
    }

    void setStateBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorTransparent));
        }
    }

    void getUserNameFromSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = preferences.getString("userName", "");
        if (userName != null && !"".equals(userName)) {
            Log.e("userName",userName);
            loginName = userName;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    void shortcut(Context context) {
        getUserNameFromSharedPreferences();
        ShortcutInfo infoConnectToUs = new ShortcutInfo.Builder(this, "SHORTCUT_CONNECT_TO_US")
                .setShortLabel("联系我们")
                .setIcon(Icon.createWithResource(context, R.drawable.telefono_telephone))
                .setIntent(new Intent(context, ConnectionUsActivity.class).setAction(Intent.ACTION_VIEW))
                .build();

        ShortcutInfo infoShare = new ShortcutInfo.Builder(this, "SHORTCUT_SHARE")
                .setShortLabel("分享头条")
                .setIcon(Icon.createWithResource(context, R.drawable.share))
                .setIntent(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse("https://github.com/Mor100/TopNews")))
                .build();

        ShortcutInfo infoFavorite = new ShortcutInfo.Builder(this, "SHORTCUT_FAVORITE")
                .setShortLabel("我的关注")
                .setIcon(Icon.createWithResource(context, R.drawable.share))
                .setIntent(new Intent(context,MyFocusActivity.class).setAction(Intent.ACTION_VIEW))
                .build();

        List<ShortcutInfo> infoList = new ArrayList<>();
        infoList.add(infoConnectToUs);
        infoList.add(infoShare);

        ShortcutManager manager = getSystemService(ShortcutManager.class);
        manager.setDynamicShortcuts(infoList);
    }

}
