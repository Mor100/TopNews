package com.news.util;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.news.adapter.VideoAdapter;
import com.news.bean.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.news.itf.URLCallBack;
import com.news.url.UrlConnection;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoUtils {

    public static void readUrl(String url, final URLCallBack callBack){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(strings[0]).build();
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.e("result",result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (callBack != null){
                    callBack.getData(s);
                }
            }
        }.execute(url);
    }

    public static List<Video> readJSON(String json) {
        List<Video> videoList = new ArrayList<>();
        JSONObject object = JSON.parseObject(json);
        JSONArray result = object.getJSONArray("result");
        for (Object obj : result) {
            JSONObject temp = (JSONObject) obj;
            JSONObject data = temp.getJSONObject("data");

            JSONObject header = data.getJSONObject("header");
            String desc = header.getString("description");
            String description = desc.substring(desc.length()-2);

            JSONObject content = data.getJSONObject("content");
            JSONObject data2 = content.getJSONObject("data");
            String title = data2.getString("title");
            String playUrl = data2.getString("playUrl");
            long id = data2.getLongValue("id");
            JSONObject cover = data2.getJSONObject("cover");
            String feed = cover.getString("feed");
            JSONObject provider = data2.getJSONObject("provider");
            String name = provider.getString("name");
            String icon = provider.getString("icon");

            Log.e("id", String.valueOf(id));
            Log.e("description",description);
            Log.e("playUrl",playUrl);
            Log.e("title",title);
            Log.e("feed",feed);
            Log.e("name",name);
            Log.e("icon",icon);
            Video video = new Video(playUrl, title, feed, name, icon,description,id);
            videoList.add(video);
            SQLUtils.addVideo(video);
        }
        return videoList;
    }

    public static void initSettings(Context context, VideoAdapter adapter, RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
    }

    public static void initData(final List<Video> videoList,final VideoAdapter videoAdapter,String category,String url){
        List<Video> temp = SQLUtils.queryVideosByCategory(category);
        if (temp.size() > 0) {
            videoList.addAll(temp);
            videoAdapter.notifyDataSetChanged();
        } else {
            VideoUtils.readUrl(url, new URLCallBack() {
                @Override
                public void getData(String result) {
                    videoList.addAll(VideoUtils.readJSON(result));
                    videoAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
