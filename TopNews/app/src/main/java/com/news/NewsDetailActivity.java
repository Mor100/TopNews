package com.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.webkit.WebView;
import android.widget.Toast;
import com.mylhyl.superdialog.SuperDialog;
import com.news.bean.NewsBean;
import com.news.bean.UserJoinNewsBean;
import com.news.util.DialogUtils;
import com.news.util.SQLUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private String loginName;
    private NewsBean newsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
        initData();
        setOnListener();
    }

    public void initView() {
        webView = findViewById(R.id.wb_news_detail);
        toolbar = findViewById(R.id.tb_news_detail);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorMineFragmentBackground));
        }
    }

    public void initData() {
        newsBean = (NewsBean) getIntent().getSerializableExtra("news");
        webView.loadUrl(newsBean.getContentUrl());
        toolbar.setTitle(newsBean.getTitle());

        loginName = getIntent().getStringExtra("loginName");

    }

    public void setOnListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.share:
                        if (!"".equals(loginName)) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/*");
                            intent.putExtra(Intent.EXTRA_TEXT, newsBean.getContentUrl());
                            startActivity(Intent.createChooser(intent, "选择分享的应用"));
                        } else {
                            showDialog();
                        }
                        break;
                    case R.id.favorite:
                        if (!"".equals(loginName) && loginName != null) {
                            UserJoinNewsBean userJoinNewsBean = SQLUtils.queryNewsByNewsIdAndUserName(newsBean.getNewsId(),
                                    loginName);
                            if (userJoinNewsBean != null) {
                                if (userJoinNewsBean.getNewsId().longValue() != newsBean.getNewsId().longValue()) {
                                    SQLUtils.addNewsToUser(newsBean, loginName);
                                    Toast.makeText(NewsDetailActivity.this, "您成功关注了该新闻！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NewsDetailActivity.this, "您已经关注了该新闻，无法再次关注！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(NewsDetailActivity.this, "您成功关注了该新闻！", Toast.LENGTH_SHORT).show();
                                SQLUtils.addNewsToUser(newsBean, loginName);
                            }
                        } else {
                            showDialog();
                        }
                        break;
                }
                return true;
            }
        });
    }

    void showDialog() {
        new AlertDialog.Builder(NewsDetailActivity.this)
                .setTitle("提示")
                .setMessage("你还未登录!")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
