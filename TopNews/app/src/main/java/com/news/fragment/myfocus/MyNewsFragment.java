package com.news.fragment.myfocus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.news.MyNewsActivity;
import com.news.R;
import com.news.adapter.RightImageRecyclerViewAdapter;
import com.news.bean.NewsBean;
import com.news.itf.OnItemClickListener;
import com.news.util.SQLUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyNewsFragment extends Fragment {
    private View view;
    private List<NewsBean> newsList;
    private PullLoadMoreRecyclerView recyclerView;
    private RightImageRecyclerViewAdapter recyclerViewAdapter;
    private String loginName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.rv);
    }

    private void initData() {
        newsList = new ArrayList<>();

        final Bundle bundle = getArguments();
        if (bundle != null) {
            loginName = bundle.getString("loginName");
            newsList = SQLUtils.queryNewsByUserName(loginName);
        }

        if (newsList.size() > 0) {
            recyclerViewAdapter = new RightImageRecyclerViewAdapter(newsList, getActivity());
            recyclerView.setLinearLayout();
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

            recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), MyNewsActivity.class);
                    intent.putExtra("url", newsList.get(position).getContentUrl());
                    intent.putExtra("title", newsList.get(position).getTitle());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(final int position) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("删除")
                            .setMessage("您想要删除此新闻吗")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NewsBean newsBean = newsList.get(position);
                                    SQLUtils.removeNewsByNewsId(newsBean.getNewsId());
                                    newsList.remove(position);
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }
                            }).show();
                }
            });


        }

        recyclerView.setPullRefreshEnable(false);
        recyclerView.setPushRefreshEnable(false);
    }
}
