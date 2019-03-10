package com.news.bean;

import com.news.greendao.DaoSession;
import com.news.greendao.NewsBeanDao;
import com.news.greendao.UserDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class NewsBean implements Serializable{

    @Id(autoincrement = true)
    private Long newsId;

    private String title;

    private String contentUrl;

    private String author;

    private String time;

    private String imageUrl;

    private String imageUrl2;

    private String imageUrl3;

    private String category;

    private int type;

    @ToMany
    @JoinEntity(entity = UserJoinNewsBean.class,sourceProperty = "newsId",targetProperty = "userName")
    private List<User> userList;

    private static final long serialVersionUID=536871008;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1978566988)
    private transient NewsBeanDao myDao;

    public NewsBean(String title, String contentUrl, String author, String time, String imageUrl, String imageUrl2,
                    String imageUrl3,String category,int type) {
        this.title = title;
        this.contentUrl = contentUrl;
        this.author = author;
        this.time = time;
        this.imageUrl = imageUrl;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.category = category;
        this.type = type;
    }

    public NewsBean(String title, String contentUrl, String author, String time, String imageUrl,String category,int type) {
        this.title = title;
        this.contentUrl = contentUrl;
        this.author = author;
        this.time = time;
        this.imageUrl = imageUrl;
        this.category = category;
        this.type = type;
    }

    public NewsBean(String title, String contentUrl, String author, String time, String imageUrl, String imageUrl2,
                    String category,int type) {
        this.title = title;
        this.contentUrl = contentUrl;
        this.author = author;
        this.time = time;
        this.imageUrl = imageUrl;
        this.imageUrl2 = imageUrl2;
        this.category = category;
        this.type = type;
    }

    public NewsBean() {
    }

    @Keep
    @Generated(hash = 180093594)
    public NewsBean(int type, String title, String contentUrl, String author, String time, String imageUrl, String imageUrl2, String imageUrl3) {
        this.type = type;
        this.title = title;
        this.contentUrl = contentUrl;
        this.author = author;
        this.time = time;
        this.imageUrl = imageUrl;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
    }

    @Keep
    @Generated(hash = 1032133656)
    public NewsBean(Long newsId, String title, String contentUrl, String author, String time, String imageUrl, String imageUrl2,
            String imageUrl3, String category, int type) {
        this.newsId = newsId;
        this.title = title;
        this.contentUrl = contentUrl;
        this.author = author;
        this.time = time;
        this.imageUrl = imageUrl;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.category = category;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1517531020)
    public synchronized void resetUserList() {
        userList = null;
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
    @Generated(hash = 12358469)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNewsBeanDao() : null;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getNewsId() {
        return this.newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    @Generated(hash = 1124337021)
    public List<User> getUserList() {
        if (userList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> userListNew = targetDao._queryNewsBean_UserList(newsId);
            synchronized (this) {
                if (userList == null) {
                    userList = userListNew;
                }
            }
        }
        return userList;
    }
    
}
