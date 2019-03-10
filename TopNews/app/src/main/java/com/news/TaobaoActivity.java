package com.news;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

public class TaobaoActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobao);
        initView();
        initData();
        setOnListener();
    }

    public void initView() {
        webView = findViewById(R.id.wb_taobao);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorMineFragmentBackground));
        }
    }

    public void initData() {
        PackageManager manager = getPackageManager();
        List<PackageInfo> infos = manager.getInstalledPackages(0);
        try {
            PackageInfo info = manager.getPackageInfo("com.taobao",0);
            Log.i("packageName",info.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).packageName.equalsIgnoreCase("com.taobao.taobao")) {
                Intent intent = new Intent();
                intent.setClassName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
                startActivity(intent);
            } else {
                webView.loadUrl("https://h5.m.taobao.com");
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setDomStorageEnabled(true);
            }
        }
    }

    public void setOnListener() {
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close_text, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        webView.destroy();
        finish();
    }

}
