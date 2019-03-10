package com.news.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapterForVideo extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles = new String[]{"动画","剧情","广告","运动","音乐","记录","萌宠","搞笑","游戏","生活","综艺"};

    public ViewPagerAdapterForVideo(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
