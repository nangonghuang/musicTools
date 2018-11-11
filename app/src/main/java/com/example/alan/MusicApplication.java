package com.example.alan;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.alan.data.GreenDao;
import com.example.alan.data.MusicScore;

import java.util.List;

public class MusicApplication extends Application {

    private static final String TAG = "MusicApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        GreenDao.getInstance().init(this);
        GreenDao.getInstance().clear();
    }
}
