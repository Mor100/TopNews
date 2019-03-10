package com.news.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserJoinVideo {
    @Id
    private Long id;

    private String userName;

    private Long videoId;

    @Generated(hash = 1850062282)
    public UserJoinVideo(Long id, String userName, Long videoId) {
        this.id = id;
        this.userName = userName;
        this.videoId = videoId;
    }

    @Generated(hash = 1977321492)
    public UserJoinVideo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
