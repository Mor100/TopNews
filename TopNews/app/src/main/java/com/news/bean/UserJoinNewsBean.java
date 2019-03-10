package com.news.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class UserJoinNewsBean {
    @Id(autoincrement = true)
    private Long id;

    private String userName;

    private Long newsId;

    @Generated(hash = 136907453)
    public UserJoinNewsBean() {
    }

    @Keep
    @Generated(hash = 1962306600)
    public UserJoinNewsBean(Long id, String userName, Long newsId) {
        this.id = id;
        this.userName = userName;
        this.newsId = newsId;
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

    public Long getNewsId() {
        return this.newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
