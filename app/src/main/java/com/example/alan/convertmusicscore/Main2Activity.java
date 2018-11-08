package com.example.alan.convertmusicscore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String test = getIntent().getStringExtra("music");
        ScrollView scrollView = new ScrollView(this);
        MusicScore2 musicScore2 = MusicScore2.convertToStandard("Only my Railgun", "C", test);
        MusicScoreView2 musicScoreView = new MusicScoreView2(this, musicScore2);
//        MusicScore musicScore = MusicScore.convertToStandard("Only my Railgun", "C", test);
//        MusicScoreView musicScoreView = new MusicScoreView(this, musicScore);
        scrollView.addView(musicScoreView);
        setContentView(scrollView);
    }
}
