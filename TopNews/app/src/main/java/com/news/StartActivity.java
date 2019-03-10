package com.news;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {
    private TextView tvStart;
    private int second = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvStart = findViewById(R.id.tv_start);
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                second--;
                if (second == 0) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    timer.cancel();
                    finish();
                }
            }
        }, 0, 1000);

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorTransparent));
        }
    }
}
