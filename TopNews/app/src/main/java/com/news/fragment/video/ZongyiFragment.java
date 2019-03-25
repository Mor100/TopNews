package com.news.fragment.video;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jzvd.Jzvd;
import com.news.R;
import com.news.adapter.VideoAdapter;
import com.news.bean.Video;
import com.news.itf.OnImageViewClick;
import com.news.itf.OnTextViewClick;
import com.news.itf.URLCallBack;
import com.news.url.UrlConnection;
import com.news.util.DialogUtils;
import com.news.util.SQLUtils;
import com.news.util.VideoUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ZongyiFragment extends Fragment {
    private RecyclerView rvVideo;
    private List<Video> videoList;
    private VideoAdapter videoAdapter;
    private View view;
    private SensorManager manager;
    private Jzvd.JZAutoFullscreenListener fullscreenListener;
    private String loginName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        rvVideo = view.findViewById(R.id.rv_video);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginName = bundle.getString("loginName");

        }

        videoList = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoList,getActivity(),loginName);

        VideoUtils.initSettings(getActivity(),videoAdapter,rvVideo);
        VideoUtils.initData(videoList,videoAdapter,"综艺",UrlConnection.ZONGYI_URL);

        videoAdapter.setOnImageViewClick(new OnImageViewClick() {
            @Override
            public void onClick(View view, int position) {
                Video video = videoList.get(position);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                Uri uri = Uri.parse(video.getImage());
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.putExtra(Intent.EXTRA_TEXT,video.getTitle() + "\n" + video.getUrl());
                startActivity(Intent.createChooser(intent, "请选择分享的平台"));
            }
        });

        videoAdapter.setOnTextViewClick(new OnTextViewClick() {
            @Override
            public void onClick(View view, final int position) {
                final TextView tvFocus = view.findViewById(R.id.tv_video_focus);
                final ImageView ivFocus = view.findViewById(R.id.iv_video_focus);

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotation);
                if (loginName != null && !"".equals(loginName)) {
                    if ("关注".equals(tvFocus.getText()))
                        ivFocus.startAnimation(animation);
                } else
                    DialogUtils.showDialog(getActivity(),"提示","您还未登录！",null,null);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        ivFocus.setVisibility(View.VISIBLE);
                        tvFocus.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if ("关注".equals(tvFocus.getText())) {

                            tvFocus.setText("已关注");
                            tvFocus.setVisibility(View.VISIBLE);
                            tvFocus.setTextColor(getResources().getColor(R.color.colorMineFragmentDivider));
                            ivFocus.setVisibility(View.INVISIBLE);
                            SQLUtils.addVideoToUser(videoList.get(position), loginName);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        fullscreenListener = new Jzvd.JZAutoFullscreenListener();

        return view;
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
