package com.example.alan.convertmusicscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
    private AsyncTask<String, Void, MusicScore> asyncTask;
    private ViewGroup scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicscore);
        String content = getIntent().getStringExtra("content");
        String title = getIntent().getStringExtra("title");
        String key = getIntent().getStringExtra("key");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        scrollView = findViewById(R.id.music_score_container);
        asyncTask = new MusicScoreAsyncTask();
        asyncTask.execute(title, key, content);

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
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
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
            musicScoreDao.save(data);
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

    private class MusicScoreAsyncTask extends AsyncTask<String, Void, MusicScore> {

        @Override
        protected MusicScore doInBackground(String... strings) {
            MusicScore musicScore = MusicScore.convertToStandard(strings[0], strings[1], strings[2]);
            return musicScore;
        }

        @Override
        protected void onPostExecute(MusicScore musicScore) {
            musicScoreView = new MusicScoreView(MusicScoreActivity.this, musicScore);
            scrollView.addView(musicScoreView);
        }
    }
}
