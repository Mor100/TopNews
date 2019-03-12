package com.news;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.news.bean.Event;
import com.news.fragment.MainFragment;
import com.news.fragment.MyFragment;
import com.news.fragment.VideoFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvIndex,tvVideo,tvMine;
    private ImageView ivIndex,ivVideo,ivMine;
    private LinearLayout llIndex,llVideo,llMine;
    private MainFragment mainFragment;
    private VideoFragment videoFragment;
    private MyFragment myFragment;
    private String loginName;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    private void initData(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = preferences.getString("userName","");
        if (userName!=null&&!"".equals(userName)){
            loginName = userName;
        }
    }

    private void initView(){
        llIndex = findViewById(R.id.ll_index_main);
        llVideo = findViewById(R.id.ll_video_main);
        llMine = findViewById(R.id.ll_mine_main);
        ivIndex = findViewById(R.id.iv_index_main);
        ivVideo = findViewById(R.id.iv_video_main);
        ivMine = findViewById(R.id.iv_mine_main);
        tvIndex = findViewById(R.id.tv_index_main);
        tvVideo = findViewById(R.id.tv_video_main);
        tvMine = findViewById(R.id.tv_mine_main);

        mainFragment = new MainFragment();
        videoFragment = new VideoFragment();
        myFragment = new MyFragment();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.ll_container,mainFragment).commit();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorToolbarBackground));
        }

    }

    private void setListener(){
        llIndex.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llMine.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_index_main:
                tvIndex.setTextColor(getResources().getColor(R.color.colorAccent));
                tvVideo.setTextColor(getResources().getColor(R.color.colorNormal));
                tvMine.setTextColor(getResources().getColor(R.color.colorNormal));
                ivIndex.setImageResource(R.mipmap.tab_home_selected);
                ivVideo.setImageResource(R.mipmap.tab_video_normal);
                ivMine.setImageResource(R.mipmap.tab_me_normal);

                Bundle mainBundle = new Bundle();
                mainBundle.putString("loginName",loginName);
                mainFragment.setArguments(mainBundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.ll_container,mainFragment).commit();
                break;
            case R.id.ll_video_main:
                tvIndex.setTextColor(getResources().getColor(R.color.colorNormal));
                tvVideo.setTextColor(getResources().getColor(R.color.colorAccent));
                tvMine.setTextColor(getResources().getColor(R.color.colorNormal));
                ivIndex.setImageResource(R.mipmap.tab_home_normal);
                ivVideo.setImageResource(R.mipmap.tab_video_selected);
                ivMine.setImageResource(R.mipmap.tab_me_normal);

                Bundle videoBundle = new Bundle();
                videoBundle.putString("loginName",loginName);
                videoFragment.setArguments(videoBundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.ll_container,videoFragment).commit();
                break;
            case R.id.ll_mine_main:
                tvIndex.setTextColor(getResources().getColor(R.color.colorNormal));
                tvVideo.setTextColor(getResources().getColor(R.color.colorNormal));
                tvMine.setTextColor(getResources().getColor(R.color.colorAccent));
                ivIndex.setImageResource(R.mipmap.tab_home_normal);
                ivVideo.setImageResource(R.mipmap.tab_video_normal);
                ivMine.setImageResource(R.mipmap.tab_me_selected);

                Bundle myBundle = new Bundle();
                myBundle.putString("loginName",loginName);
                myFragment.setArguments(myBundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.ll_container,myFragment).commit();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        loginName = event.loginName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
