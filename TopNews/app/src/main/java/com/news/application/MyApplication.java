package com.news.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.news.greendao.DaoMaster;
import com.news.greendao.DaoSession;
import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {
    private DaoMaster master;
    private DaoMaster.DevOpenHelper helper;
    private DaoSession session;
    private SQLiteDatabase database;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        helper = new DaoMaster.DevOpenHelper(this,"my_db.db");
        database = helper.getWritableDatabase();
        master = new DaoMaster(database);
        session = master.newSession();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        ActiveAndroid.dispose();
    }

    public DaoSession getSession(){
        return session;
    }

    public static MyApplication getInstance(){
        return application;
    }
}
