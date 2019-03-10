package com.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.mylhyl.superdialog.SuperDialog;
import com.news.requestcode.RequestCode;

public class ConnectionUsActivity extends AppCompatActivity {
    private Toolbar tb;
    private ImageView ivPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_us);

        initView();
        setListener();
    }

    private void initView() {
        tb = findViewById(R.id.tb_connect_us);
        ivPhone = findViewById(R.id.iv_connect_us);
        setSupportActionBar(tb);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorToolbarBackground));
        }
    }

    private void setListener() {
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    RequestCode.REQUEST_CODE_CALL_PHONE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCode.REQUEST_CODE_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog();
                } else {
                    Toast.makeText(this, "请允许授权拨打电话权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void onCall() {
        if (checkPermission()) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15988337947"));
            startActivity(intent);
        }
    }

    private void showDialog() {
        new SuperDialog.Builder(this)
                .setTitle("提示")
                .setGravity(Gravity.CENTER)
                .setMessage("即将拨打电话，请确认")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new SuperDialog.OnClickPositiveListener() {
                    @Override
                    public void onClick(View v) {
                        onCall();
                    }
                }).build();
    }
}
