package com.news.util;

import android.icu.lang.UScript;
import android.util.Log;
import com.news.application.MyApplication;
import com.news.bean.*;
import com.news.greendao.*;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {
    private static DaoSession session = MyApplication.getInstance().getSession();


    public static void addUser(User user) {
        session.getUserDao().insert(user);
    }

    public static void addNews(NewsBean newsBean) {
        session.getNewsBeanDao().insert(newsBean);
    }


    public static List<NewsBean> queryNewsByCategory(String category) {
        List<NewsBean> beanList = session.queryBuilder(NewsBean.class).where(NewsBeanDao.Properties.Category.eq(category))
                .list();
        return beanList.size() <= 0 ? new ArrayList<NewsBean>() : beanList;
    }


    public static User queryUserByUserName(final String userName) {
        List<User> userList = session.queryBuilder(User.class).where(UserDao.Properties.UserName.eq(userName)).list();
        return userList.size() <= 0 ? new User() : userList.get(0);
    }


    public static void addNewsToUser(final NewsBean news, String userName) {
        UserJoinNewsBean userJoinNewsBean = new UserJoinNewsBean();
        userJoinNewsBean.setNewsId(news.getNewsId());
        userJoinNewsBean.setUserName(userName);
        session.getUserJoinNewsBeanDao().insert(userJoinNewsBean);
    }


    public static UserJoinNewsBean queryNewsByNewsIdAndUserName(Long newId, String userName) {
        Log.i("newsId", newId.toString());
        List<UserJoinNewsBean> beanList;
        beanList = session.queryBuilder(UserJoinNewsBean.class).where(UserJoinNewsBeanDao.Properties.NewsId.eq(newId),
                UserJoinNewsBeanDao.Properties.UserName.eq(userName)).list();
        return beanList.size() <= 0 ? null : beanList.get(0);
    }


    public static List<NewsBean> queryNewsByUserName(String userName) {
        List<NewsBean> beanList = new ArrayList<>();

        List<UserJoinNewsBean> joinNewsBeanList = session.queryBuilder(UserJoinNewsBean.class).where(UserJoinNewsBeanDao
                .Properties.UserName.eq(userName)).list();
        Log.i("用户关注新闻数量", String.valueOf(joinNewsBeanList.size()));

        for (UserJoinNewsBean bean :
                joinNewsBeanList) {
            Long newsId = bean.getNewsId();
            NewsBean newsBean = session.queryBuilder(NewsBean.class).where(NewsBeanDao.Properties.NewsId.eq(newsId.longValue()))
                    .list().get(0);
            beanList.add(newsBean);
        }
        return beanList;
    }

    public static UserJoinVideo queryVideoByUserNameAndVideoId(String userName,Long videoId){
        List<UserJoinVideo> videoList = session.queryBuilder(UserJoinVideo.class).where(UserJoinVideoDao.Properties
                .UserName.eq(userName),UserJoinVideoDao.Properties.VideoId.eq(videoId)).list();
        return videoList.size()<=0?null:videoList.get(0);
    }


    public static void removeAllNewsByUserName(String userName) {
        List<UserJoinNewsBean> joinNewsBeanList = session.getUserJoinNewsBeanDao().queryBuilder().where(
                UserJoinNewsBeanDao.Properties.UserName.eq(userName)).list();

        for (UserJoinNewsBean b : joinNewsBeanList) {
            session.getUserJoinNewsBeanDao().delete(b);
        }
    }


    public static void addVideo(Video video) {
        session.getVideoDao().insert(video);
    }


    public static List<Video> queryVideosByCategory(String category) {
        List<Video> videoList = session.queryBuilder(Video.class).where(VideoDao.Properties.Category.eq(category)).list();
        return videoList.size() <= 0 ? new ArrayList<Video>() : videoList;
    }


    public static void addVideoToUser(Video video, String userName) {
        UserJoinVideo joinVideo = new UserJoinVideo();
        joinVideo.setUserName(userName);
        joinVideo.setVideoId(video.getVideoId().longValue());
        session.getUserJoinVideoDao().insert(joinVideo);
    }

    public static List<Video> queryVideosByUserName(String userName) {
        List<UserJoinVideo> videoListId = session.queryBuilder(UserJoinVideo.class).where(UserJoinVideoDao.Properties.UserName
                .eq(userName)).list();
        List<Video> videoList = new ArrayList<>();
        for (UserJoinVideo v :
                videoListId) {
            Long videoId = v.getVideoId();
            Video video = session.queryBuilder(Video.class).where(VideoDao.Properties.VideoId.eq(videoId.longValue())).list().get(0);
            videoList.add(video);
        }
        return videoList;
    }

    public static void removeAllVideoByUserName(String userName) {
        List<UserJoinVideo> joinVideoList = session.queryBuilder(UserJoinVideo.class).where(UserJoinVideoDao.Properties
                .UserName.eq(userName)).list();

        for (UserJoinVideo v :
                joinVideoList) {
            session.getUserJoinVideoDao().delete(v);
        }
    }

    public static void updateVideo(Video video){
        session.getVideoDao().update(video);
    }

    public static void removeNewsByNewsId(Long id){
        List<UserJoinNewsBean> joinNewsBeanList = session.queryBuilder(UserJoinNewsBean.class).where(
                UserJoinNewsBeanDao.Properties.NewsId.eq(id.longValue())).list();

        for (UserJoinNewsBean bean: joinNewsBeanList){
            session.getUserJoinNewsBeanDao().delete(bean);
        }
    }

    public static void removeVideoByVideoId(Long id){
        List<UserJoinVideo> joinVideoList = session.queryBuilder(UserJoinVideo.class).where(UserJoinVideoDao.Properties
        .VideoId.eq(id.longValue())).list();

        for(UserJoinVideo video:joinVideoList){
            session.getUserJoinVideoDao().delete(video);
        }
    }

    public static void updateVideoByVideoId(Video video){
        session.getVideoDao().update(video);
    }

    public static void addImageToUser(User user,String path){
        Log.i("图片路径",path);
        user.setImagePath(path);
        session.getUserDao().update(user);
    }
    
    public static void removeNewsByCategory(String category){
        List<NewsBean> beanList = queryNewsByCategory(category);
        for (NewsBean news :
                beanList) {
            session.getNewsBeanDao().delete(news);
        }
    }


}
