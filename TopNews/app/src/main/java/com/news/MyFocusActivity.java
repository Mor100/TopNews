package com.news;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.news.adapter.ViewPagerAdapterForMyFocus;
import com.news.fragment.myfocus.MyNewsFragment;
import com.news.fragment.myfocus.MyVideosFragment;

public class MyFocusActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String loginName;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private Fragment[] fragments;
    private ViewPagerAdapterForMyFocus adapter;
    private MyNewsFragment newsFragment;
    private MyVideosFragment videosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);

        init();
    }

    public void init() {

        toolbar = findViewById(R.id.tb_my_focus);
        tableLayout = findViewById(R.id.tl_my_focus);
        viewPager = findViewById(R.id.vp_my_focus);

        setSupportActionBar(toolbar);

        newsFragment = new MyNewsFragment();
        videosFragment = new MyVideosFragment();
        fragments = new Fragment[]{newsFragment, videosFragment};

        adapter = new ViewPagerAdapterForMyFocus(getSupportFragmentManager(),fragments);

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        loginName = getIntent().getStringExtra("loginName");

        Bundle bundle = new Bundle();
        bundle.putString("loginName",loginName);
        fragments[0].setArguments(bundle);
        fragments[1].setArguments(bundle);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.colorMineFragmentBackground));
        }
    }

}
