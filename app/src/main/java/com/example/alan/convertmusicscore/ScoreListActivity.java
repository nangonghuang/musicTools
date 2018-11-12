package com.example.alan.convertmusicscore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.alan.adapter.BaseRecyclerViewHolder;
import com.example.alan.adapter.OnRecyclerItemClickListener;
import com.example.alan.adapter.ScoreListAdapter;
import com.example.alan.data.GreenDao;
import com.example.alan.data.MusicScore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScoreListActivity extends AppCompatActivity {

    private static final String TAG = "ScoreListActivity";
    private ScoreListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        List<MusicScore> musicScores = GreenDao.getInstance().getDaoSession().getMusicScoreDao().loadAll();
        adapter = new ScoreListAdapter(musicScores);
        RecyclerView scorelist = findViewById(R.id.score_list);
        scorelist.setAdapter(adapter);
        scorelist.setLayoutManager(new LinearLayoutManager(this));
        scorelist.addOnItemTouchListener(new OnRecyclerItemClickListener(scorelist) {
            @Override
            public void onItemClick(BaseRecyclerViewHolder vh) {
                MusicScore score = adapter.getDataAt(vh.getAdapterPosition());
                MusicScoreActivity.startActivity(ScoreListActivity.this, score);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
