package com.example.alan.convertmusicscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alan.data.GreenDao;
import com.example.alan.data.MusicScore;
import com.example.alan.db.gen.MusicScoreDao;
import com.example.alan.myview.MusicScoreView;
import com.example.alan.utils.BitmapUtils;

import java.io.IOException;
import java.util.List;

public class MusicScoreActivity extends AppCompatActivity {
    private static final String TAG = "MusicScoreActivity";
    private static final int REQUEST_WRITE = 1;
    private MusicScoreView musicScoreView;
    private MusicScore musicScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicscore);
        musicScore = (MusicScore) getIntent().getSerializableExtra("musicScore");
        Log.i(TAG, "onCreate: " + musicScore.toString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(musicScore.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ViewGroup scrollView = findViewById(R.id.music_score_container);
        musicScoreView = new MusicScoreView(this, musicScore);
        scrollView.addView(musicScoreView);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
            } else {
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "请求读写sd卡失败，将无法保存成图片到sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_1:
                saveAsPicture();
                break;
            case R.id.menu_2:
                saveToDB();
                break;
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveToDB() {
        MusicScoreDao musicScoreDao = GreenDao.getInstance().getDaoSession().getMusicScoreDao();
        if (musicScoreView != null) {
            MusicScore data = musicScoreView.getData();
            List<MusicScore> list = musicScoreDao.queryBuilder().where(MusicScoreDao.Properties.Title.eq(data.getTitle()),
                    MusicScoreDao.Properties.Author.eq(data.getAuthor()))
                    .list();
            if (list.size() == 0) {
                musicScoreDao.insert(data);
            } else {
                MusicScore score = list.get(0);
                musicScoreDao.save(score.copyFrom(data));
            }
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAsPicture() {
        Bitmap bitmap = BitmapUtils.loadBitmapFromView(musicScoreView);
        try {
            BitmapUtils.saveBitmapToSDcard(bitmap, musicScoreView.getData().getTitle());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startActivity(Context context, MusicScore score) {
        Intent intent = new Intent(context, MusicScoreActivity.class);
        intent.putExtra("musicScore", score);
        context.startActivity(intent);
    }
}
