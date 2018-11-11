package com.example.alan.data;

import android.content.Context;

import com.example.alan.db.gen.DaoMaster;
import com.example.alan.db.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

public class GreenDao {
    private static final GreenDao ourInstance = new GreenDao();

    public static GreenDao getInstance() {
        return ourInstance;
    }

    private GreenDao() {
    }

    private DaoSession daoSession;

    public void init(Context context){
        initGreenDao(context);
    }

    private void initGreenDao(Context context) {
        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "music-score-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void clear(){
        daoSession.clear();
    }
}
