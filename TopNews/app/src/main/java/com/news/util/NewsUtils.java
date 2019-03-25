package com.news.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;
import com.news.R;
import com.news.bean.NewsBean;
import com.news.itf.URLCallBack;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/26.
 */

public class NewsUtils {
    public static void readUrl(String url, final URLCallBack callBack) {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                StringBuilder result = new StringBuilder();
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                    connection.connect();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("json格式数据", result.toString());
                return result.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                if (callBack != null) {
                    callBack.getData(s);
                }
            }
        }.execute(url);

    }

    public static void readJSON(String result, List<NewsBean> newsList) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject object1 = object.getJSONObject("result");
            JSONArray array = object1.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object2 = array.getJSONObject(i);
                String title = object2.getString("title");
                String time = object2.getString("date");
                String author = object2.getString("author_name");
                String contentUrl = object2.getString("url");
                String image = object2.getString("thumbnail_pic_s");
                String category = object2.getString("category");
                //如果没有第二张图片
                if (!object2.has("thumbnail_pic_s02")) {
                    NewsBean news = new NewsBean(title, contentUrl, author, time, image, "null", category, 1);
                    newsList.add(news);
                    SQLUtils.addNews(news);
                } else {
                    String image2 = object2.getString("thumbnail_pic_s02");
                    //如果没有第三张图片
                    if (!object2.has("thumbnail_pic_s03")) {
                        NewsBean news = new NewsBean(title, contentUrl, author, time, image, image2, category, 2);
                        newsList.add(news);
                        SQLUtils.addNews(news);
                    } else {
                        String image3 = object2.getString("thumbnail_pic_s03");
                        NewsBean news = new NewsBean(title, contentUrl, author, time, image, image2, image3, category, 3);
                        newsList.add(news);
                        SQLUtils.addNews(news);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void initSettings(Context context, RecyclerView.Adapter recyclerViewAdapter, PullLoadMoreRecyclerView rv) {

        rv.setLinearLayout();
        rv.setAdapter(recyclerViewAdapter);
        rv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rv.setFooterViewBackgroundColor(R.color.colorPrimary);
        rv.setFooterViewTextColor(R.color.colorWhite);
    }

    public static void initData(final List<NewsBean> temp, final List<NewsBean> newsList, final RecyclerView.Adapter recyclerViewAdapter,
                                String url) {

        if (temp.size() > 0) {
            int round = 0;
            while (round < 10) {
                newsList.add(temp.get(round));
                round++;
            }
            recyclerViewAdapter.notifyDataSetChanged();
        } else {
            NewsUtils.readUrl(url, new URLCallBack() {
                @Override
                public void getData(String result) {
                    readJSON(result, temp);
                    int round = 0;
                    while (round < 10) {
                        newsList.add(temp.get(round));
                        round++;
                    }
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    public static Uri getResourcesUri(@DrawableRes int id,Context context) {
        Resources resources = context.getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return Uri.parse(uriPath);
    }
}
