package com.news.bean;

import org.greenrobot.greendao.annotation.*;

import java.util.List;

import org.greenrobot.greendao.DaoException;
import com.news.greendao.DaoSession;
import com.news.greendao.NewsBeanDao;
import com.news.greendao.UserDao;
import com.news.greendao.VideoDao;

@Entity
public class User {

    @Id
    private String userName;

    private String password;

    private String phoneNumber;

    private String imagePath;

    @ToMany
    @JoinEntity(entity = UserJoinNewsBean.class,sourceProperty = "userName",targetProperty = "newsId")
    private List<NewsBean> newsList;

    @ToMany
    @JoinEntity(entity = UserJoinVideo.class,sourceProperty = "userName",targetProperty = "videoId")
    private List<Video> videoList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    public User(){}

    public User(String userName,String password,String imagePath){
        this.userName = userName;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Generated(hash = 1429775579)
    public User(String userName, String password, String phoneNumber,
            String imagePath) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Keep
    public List<NewsBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsBean> newsList) {
        this.newsList = newsList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1447370136)
    public synchronized void resetNewsList() {
        newsList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1038389664)
    public List<Video> getVideoList() {
        if (videoList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideoDao targetDao = daoSession.getVideoDao();
            List<Video> videoListNew = targetDao._queryUser_VideoList(userName);
            synchronized (this) {
                if (videoList == null) {
                    videoList = videoListNew;
                }
            }
        }
        return videoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1186219891)
    public synchronized void resetVideoList() {
        videoList = null;
    }

}
