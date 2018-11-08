package com.example.alan.convertmusicscore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class MusicNoteView extends View {
    private MusicNote note;
    private Paint textPaint = new Paint();
    private Paint pointPaint = new Paint();
    private int defaultSize;
    private int defaultTextSize = 14;
    private Paint.FontMetrics fontMetrics;
    private boolean drawHighP = false;
    private boolean drawLowP = false;

    public MusicNoteView(Context context, MusicNote note) {
        super(context);
        this.note = note;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, defaultTextSize, getResources().getDisplayMetrics()));
        defaultSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        setBackgroundColor(Color.YELLOW);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(10);
        fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        Log.d(TAG, "MusicNoteView: top" + top);
        Log.d(TAG, "MusicNoteView: bottom" + bottom);
    }

    public MusicNoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MusicNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize, defaultSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultSize);
        }
    }

    private static final String TAG = "MusicNoteView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0, 10);
        canvas.drawText(String.valueOf(note.number), defaultSize / 2, defaultSize / 2, textPaint);
        if (drawLowP) {
            canvas.drawCircle(defaultSize / 2, defaultSize / 2 + fontMetrics.bottom, 1, pointPaint);
        }
        if (drawHighP) {
            canvas.drawCircle(defaultSize / 2, defaultSize / 2 + fontMetrics.top, 1, pointPaint);
        }
        canvas.restore();
    }

    public void drawHighPoint() {
        this.drawHighP = true;
        invalidate();
    }

    public void drawLowPoint() {
        this.drawLowP = true;
        invalidate();
    }
}
