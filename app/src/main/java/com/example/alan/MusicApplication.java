package com.example.alan;

import android.app.Application;

import com.example.alan.data.GreenDao;

public class MusicApplication extends Application {

    private static final String TAG = "MusicApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        GreenDao.getInstance().init(this);
    }
}
