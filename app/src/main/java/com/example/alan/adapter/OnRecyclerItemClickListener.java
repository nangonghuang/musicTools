package com.example.alan.adapter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alan on 2016/10/18.
 * 用来处理RecyclerView的点击事件，包括点击，双击，长按等事件
 * 想要处理点击事件需要自己去继承然后重写相应的方法
 */

public class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView attachedRecyclerView;

    /**
     * 用来处理RecyclerView的点击事件，包括点击，双击，长按等事件
     * 想要处理点击事件需要自己去重写相应的方法
     *
     * @param recyclerView
     */
    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        attachedRecyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View pressedChildView = attachedRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (pressedChildView != null) {
                    BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder) attachedRecyclerView.getChildViewHolder(pressedChildView);
                    if (vh.getItemChildClickViewIds() != null && vh.getItemChildClickViewIds().size() > 0) {
                        for (int id : vh.getItemChildClickViewIds()) {
                            View itemChildView = vh.getView(id);
                            if (itemChildView != null &&
                                    inRangeOfView(itemChildView, e) && itemChildView.isEnabled()) {
                                onItemChildClick(itemChildView, vh);
                                return true;
                            }
                        }
                    }
                    onItemClick(vh);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View pressedChildView = attachedRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (pressedChildView != null) {
                    BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder) attachedRecyclerView.getChildViewHolder(pressedChildView);
                    if (vh.getItemChildClickViewIds() != null && vh.getItemChildClickViewIds().size() > 0) {
                        for (int id : vh.getItemChildClickViewIds()) {
                            View itemChildView = vh.getView(id);
                            if (itemChildView != null &&
                                    inRangeOfView(itemChildView, e) && itemChildView.isEnabled()) {
                                onItemChildLongClick(itemChildView, vh);
                                return;
                            }
                        }
                    }
                    onItemLongClick(vh);
                }
            }

            @Override
            public boolean onDown(MotionEvent e) {
                View pressedChildView = attachedRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (pressedChildView != null) {
                    BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder) attachedRecyclerView.getChildViewHolder(pressedChildView);
                    onItemDown(vh);
                } else {
                    if (inRangeOfView(attachedRecyclerView, e)) {
                        onNoItemDown();
                    }
                }
                return true;
            }
        });
    }

    private boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        } else {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            return ev.getRawX() >= (float) x && ev.getRawX() <= (float) (x + view.getWidth()) && ev.getRawY() >= (float) y && ev.getRawY() <= (float) (y + view.getHeight());
        }
    }

    //点击事件交给mGestureDetector处理
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    public void onItemClick(BaseRecyclerViewHolder vh) {

    }

    public void onItemChildClick(View itemChild, BaseRecyclerViewHolder vh) {

    }

    //长按监听
    public void onItemLongClick(BaseRecyclerViewHolder vh) {

    }

    public void onItemChildLongClick(View itemChild, BaseRecyclerViewHolder vh) {

    }

    public void onItemDown(BaseRecyclerViewHolder vh) {

    }

    public void onNoItemDown() {

    }
}
