package com.news.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.news.NewsDetailActivity;
import com.news.R;
import com.news.adapter.MiddleImageRecyclerViewAdapter;
import com.news.bean.NewsBean;
import com.news.itf.OnItemClickListener;
import com.news.itf.URLCallBack;
import com.news.url.UrlConnection;
import com.news.util.NewsUtils;
import com.news.util.SQLUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GuoneiFragment extends Fragment {
    private PullLoadMoreRecyclerView rv;
    private View view;
    private MiddleImageRecyclerViewAdapter recyclerViewAdapter;
    private List<NewsBean> newsList;
    private List<NewsBean> temp;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        rv = view.findViewById(R.id.rv);

        newsList = new ArrayList<>();
        temp = SQLUtils.queryNewsByCategory("国内");
        recyclerViewAdapter = new MiddleImageRecyclerViewAdapter(newsList, getActivity());

        NewsUtils.initSettings(getActivity(), recyclerViewAdapter, rv);
        NewsUtils.initData(temp, newsList, recyclerViewAdapter, UrlConnection.GUONEI_URL);

        recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news", newsList.get(position));

                Bundle bundle = getArguments();
                if (bundle != null) {
                    intent.putExtra("loginName", bundle.getString("loginName"));
                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

        rv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        temp.clear();
                        NewsUtils.readUrl(UrlConnection.GUONEI_URL, new URLCallBack() {
                            @Override
                            public void getData(String result) {
                                NewsUtils.readJSON(result,temp);
                                if (temp.get(0).getTitle().equals(newsList.get(0).getTitle())){
                                    Toast.makeText(getActivity(),"暂无更多数据",Toast.LENGTH_SHORT).show();
                                }else {
                                    newsList.clear();
                                    page = 1;
                                    NewsUtils.initData(temp,newsList,recyclerViewAdapter,UrlConnection.GUONEI_URL);
                                }

                                rv.setPullLoadMoreCompleted();
                            }
                        });
                    }
                }, 800);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int round = 0;
                        if (page <= 2) {
                            while (round < 10) {
                                Log.i("temp.Size()", String.valueOf(temp.size()));
                                newsList.add(temp.get(round + 10 * page));
                                round++;
                                recyclerViewAdapter.notifyItemInserted(newsList.size() - 1);
                            }
                        } else {
                            Toast.makeText(getActivity(), "已加载所有数据", Toast.LENGTH_SHORT).show();
                        }
                        page++;
                        rv.setPullLoadMoreCompleted();
                    }
                }, 800);
            }
        });

        return view;
    }

}
