package com.news;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JingDongActivity extends AppCompatActivity {
    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jing_dong);
        initView();
        initData();
        setOnListener();
    }

    public void initView(){
        wb = findViewById(R.id.wb_jingdong);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorMineFragmentBackground));
        }
    }

    public void initData(){
        wb.loadUrl("https://m.jd.com");
        WebSettings settings = wb.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
    }

    public void setOnListener(){
        wb.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if (wb.canGoBack()){
            wb.goBack();
            return;
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close_text,menu);
        return true;
    }
}
