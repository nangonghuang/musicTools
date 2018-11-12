package com.example.alan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alan.convertmusicscore.R;
import com.example.alan.data.MusicScore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoreListAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


    private final List<MusicScore> scores;

    public ScoreListAdapter(List<MusicScore> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_list, parent, false);
        return new BaseRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.score_list_title);
        title.setText(scores.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public MusicScore getDataAt(int position){
        return scores.get(position);
    }
}
