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

public class MusicScoreView extends View {
    private int defaultNoteWidth2;
    private int defaultNoteHeight;
    private MusicScore data;
    private Paint textPaint = new Paint();
    private Paint pointPaint = new Paint();
    private Paint changePaint = new Paint();
    private int defaultNoteWidth;
    private int defaultTextSize;
    private Paint.FontMetrics fontMetrics;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mHeaderHeight = 0;
    private int changeTextSize;

    public MusicScoreView(Context context, MusicScore musicScore) {
        super(context);
        data = musicScore;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        textPaint.setTextSize(defaultTextSize);
        defaultNoteWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        defaultNoteWidth2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        defaultNoteHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        mHeaderHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(10);
        fontMetrics = textPaint.getFontMetrics();
        changePaint.setColor(Color.BLACK);
        changePaint.setFakeBoldText(true);
        changePaint.setTextAlign(Paint.Align.CENTER);
        changeTextSize = defaultTextSize * 3 / 4;
        changePaint.setTextSize(changeTextSize);

//        setBackgroundColor(Color.YELLOW);
    }

    public MusicScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MusicScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int maxWidth = 0;
        int x = 0, y = defaultNoteHeight + mHeaderHeight;
        for (int i = 0; i < data.getNotesLen(); i++) {
            MusicNote note = data.getMusicNotes()[i];
            if (note.isNextLineMark()) {
                maxWidth = x > maxWidth ? x : maxWidth;
                x = 0;
                y += defaultNoteHeight;
            } else {
                if (note.change == MusicNote.stand) {
                    if (x + defaultNoteWidth2 > widthSize) {
                        x = 0;
                        y += defaultNoteHeight;
                    }
                    x += defaultNoteWidth2;
                } else {
                    if (x + defaultNoteWidth > widthSize) {
                        x = 0;
                        y += defaultNoteHeight;
                    }
                    x += defaultNoteWidth;
                }
            }
        }
        mWidth = maxWidth;
//        Log.i(TAG, "onMeasure 最后一条y坐标: " + y);
        mHeight = y + mHeaderHeight; // ???

        setMeasuredDimension(widthSize, mHeight);
    }

    private static final String TAG = "MusicScoreView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(data.getTitle(), getWidth() / 2, defaultNoteHeight, textPaint);
        canvas.drawText(data.getAuthor(), getWidth() * 3 / 4, defaultNoteHeight * 3 / 2, textPaint);
        canvas.drawText(data.getTrack(), 100, defaultNoteHeight * 3 / 2, textPaint);

        int x = changeTextSize / 2, y = defaultNoteHeight + mHeaderHeight;
        for (int i = 0; i < data.getNotesLen(); i++) {
            MusicNote note = data.getMusicNotes()[i];
            int width = 0;
            if (note.isNextLineMark()) {
                x = changeTextSize / 2;
                y += defaultNoteHeight;
            } else {
                if (note.change == MusicNote.stand) {
                    width = defaultNoteWidth2;
                    if (x + width > getWidth()) {
                        x = changeTextSize / 2;
                        y += defaultNoteHeight;
                    }
                    x += width;
                    Log.d(TAG, "onDraw: x =" + x + ",getwidth = " + getWidth());
                } else {
                    width = defaultNoteWidth;
                    if (x + width > getWidth()) {
                        x = changeTextSize / 2;
                        y += defaultNoteHeight;
                    }
                    x += width;
                    Log.d(TAG, "onDraw: x =" + x + ",getwidth = " + getWidth());
                }
            }
//            canvas.drawLine(x, y, getWidth(), y, textPaint);

            if (!note.isBlankMark() && !note.isNextLineMark()) {
                canvas.drawText(String.valueOf(note.number), x - width / 2, y + defaultNoteHeight / 2, textPaint);
                if (note.level == 1) {
                    canvas.drawCircle(x - width / 2, y + defaultNoteHeight / 2 + fontMetrics.top, 1, pointPaint);
                } else if (note.level == -1) {
                    canvas.drawCircle(x - width / 2, y + defaultNoteHeight / 2 + fontMetrics.bottom, 1, pointPaint);
                }
                if (note.change == MusicNote.low) {
                    canvas.drawText("b", x - width, y + defaultNoteHeight / 4, changePaint);
                } else if (note.change == MusicNote.up) {
                    canvas.drawText("#", x - width, y + defaultNoteHeight / 4, changePaint);
                }
            }
        }
    }
}
