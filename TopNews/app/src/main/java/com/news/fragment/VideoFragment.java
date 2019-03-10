package com.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidkun.xtablayout.XTabLayout;
import com.news.R;
import com.news.adapter.ViewPagerAdapterForVideo;
import com.news.fragment.video.*;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private ViewPager vpNavigationList;
    private XTabLayout tlNavigationBar;
    private View view;
    private ViewPagerAdapterForVideo vpAdapter;
    private List<Fragment> fragmentList;
    private Fragment[] fragments = new Fragment[]{new AnimationFragment(), new JuqingFragment(), new ADFragment(), new SportsFragment(),
            new MusicFragment(), new RecordFragment(), new PetsFragment(), new FunFragment(), new GamesFragment(), new LifeFragment(),
            new ZongyiFragment()};
    private String loginName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_video, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        vpNavigationList = view.findViewById(R.id.vp_navigation_list);
        tlNavigationBar = view.findViewById(R.id.tl_navigation_bar);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < fragments.length; i++) {
            fragmentList.add(fragments[i]);
        }
        vpAdapter = new ViewPagerAdapterForVideo(getChildFragmentManager(), fragmentList);
        vpNavigationList.setAdapter(vpAdapter);
        tlNavigationBar.setupWithViewPager(vpNavigationList);

        Bundle bundle = getArguments();
        if (bundle != null){
            loginName = bundle.getString("loginName");
            sendData(loginName);
        }
    }

    private void sendData(String userName){
        Bundle bundle = new Bundle();
        bundle.putString("loginName",userName);
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentList.get(i).setArguments(bundle);
        }
    }

}
