package com.example.alan.adapter;

import android.util.SparseArray;
import android.view.View;

import java.util.HashSet;
import java.util.LinkedHashSet;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alan on 2016/10/17.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;

    private final LinkedHashSet<Integer> itemChildClickViewIds;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
        this.itemChildClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 添加点击事件，recyclerview的addOnItemTouchListener会去检查这个map，响应点击
     * @param viewId
     * @return
     */
    public BaseRecyclerViewHolder addItemChildClickListener(int viewId) {
        this.itemChildClickViewIds.add(viewId);
        return this;
    }

    public BaseRecyclerViewHolder addItemChildLongClickListener(int viewId) {
        this.itemChildLongClickViewIds.add(viewId);
        return this;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public HashSet<Integer> getItemChildClickViewIds() {
        return this.itemChildClickViewIds;
    }
}
