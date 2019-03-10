package com.news.fragment.myfocus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.jzvd.Jzvd;
import com.news.R;
import com.news.adapter.VideoAdapterForMyFocus;
import com.news.bean.Video;
import com.news.itf.OnImageViewClick;
import com.news.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

public class MyVideosFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private VideoAdapterForMyFocus adapter;
    private String loginName;
    private List<Video> videoList;
    private SensorManager manager;
    private Jzvd.JZAutoFullscreenListener fullscreenListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        init();
        manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        fullscreenListener = new Jzvd.JZAutoFullscreenListener();
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.rv_video);

        videoList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginName = bundle.getString("loginName");
            videoList = SQLUtils.queryVideosByUserName(loginName);
        }

        if (videoList.size() > 0) {

            adapter = new VideoAdapterForMyFocus(videoList, getActivity());

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            adapter.setOnImageViewClick(new OnImageViewClick() {
                @Override
                public void onClick(View view, int position) {
                    Video video = videoList.get(position);
                    SQLUtils.removeVideoByVideoId(video.getVideoId());
                    video.setFavorite("null");
                    SQLUtils.updateVideoByVideoId(video);

                    videoList.remove(position);
                    adapter.notifyDataSetChanged();

                }
            });
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(fullscreenListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
        manager.unregisterListener(fullscreenListener);
    }
}
