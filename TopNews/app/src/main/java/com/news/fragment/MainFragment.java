package com.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidkun.xtablayout.XTabLayout;
import com.news.R;
import com.news.adapter.ViewPagerAdapterForNews;
import com.news.bean.Event;
import com.news.fragment.news.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainFragment extends Fragment {
    private ViewPager vpNavigationList;
    private XTabLayout tlNavigationBar;
    private View view;

    private ViewPagerAdapterForNews vpAdapter;
    private ToutiaoFragment toutiaoFragment;
    private ShehuiFragment shehuiFragment;
    private GuoneiFragment guoneiFragment;
    private GuojiFragment guojiFragment;
    private YuleFragment yuleFragment;
    private TiyuFragment tiyuFragment;
    private JunshiFragment junshiFragment;
    private KejiFragment kejiFragment;
    private CaijingFragment caijingFragment;
    private ShishangFragment shishangFragment;
    private Fragment[] fragments = new Fragment[10];
    private String loginName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_news, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        vpNavigationList = view.findViewById(R.id.vp_navigation_list);
        tlNavigationBar = view.findViewById(R.id.tl_navigation_bar);
    }

    private void initData() {
        toutiaoFragment = new ToutiaoFragment();
        shehuiFragment = new ShehuiFragment();
        guoneiFragment = new GuoneiFragment();
        guojiFragment = new GuojiFragment();
        yuleFragment = new YuleFragment();
        tiyuFragment = new TiyuFragment();
        junshiFragment = new JunshiFragment();
        kejiFragment = new KejiFragment();
        caijingFragment = new CaijingFragment();
        shishangFragment = new ShishangFragment();

        fragments[0] = toutiaoFragment;
        fragments[1] = shehuiFragment;
        fragments[2] = guoneiFragment;
        fragments[3] = guojiFragment;
        fragments[4] = yuleFragment;
        fragments[5] = tiyuFragment;
        fragments[6] = junshiFragment;
        fragments[7] = kejiFragment;
        fragments[8] = caijingFragment;
        fragments[9] = shishangFragment;

        vpAdapter = new ViewPagerAdapterForNews(getChildFragmentManager(), fragments);
        vpNavigationList.setAdapter(vpAdapter);
        tlNavigationBar.setupWithViewPager(vpNavigationList);
        Bundle bundle = getArguments();
        if (bundle != null) {
            loginName = bundle.getString("loginName");
            sendData(loginName);
        }
    }

    public void sendData(String userName) {
        Bundle bundle = new Bundle();
        bundle.putString("loginName", userName);
        toutiaoFragment.setArguments(bundle);
        shehuiFragment.setArguments(bundle);
        guojiFragment.setArguments(bundle);
        guoneiFragment.setArguments(bundle);
        yuleFragment.setArguments(bundle);
        tiyuFragment.setArguments(bundle);
        junshiFragment.setArguments(bundle);
        kejiFragment.setArguments(bundle);
        caijingFragment.setArguments(bundle);
        shishangFragment.setArguments(bundle);
    }


}
