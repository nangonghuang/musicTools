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

public class MusicScoreView2 extends View {
    /**
     * 不带变化音级标记的字符宽度
     */
    private int defaultNoteWidth2;
    /**
     * 带变化音级标记的字符宽度
     */
    private int defaultNoteWidth;
    /**
     * 音符的长度(包括 高音 低音标记)
     */
    private int defaultNoteHeight;
    private MusicScore2 data;
    /**
     * 音符的画笔
     */
    private Paint textPaint = new Paint();
    /**
     * 高低音标记的画笔
     */
    private Paint pointPaint = new Paint();
    /**
     * 变化音级的画笔
     */
    private Paint changePaint = new Paint();
    /**
     * 音符的文字大小
     */
    private int defaultTextSize;
    private Paint.FontMetrics fontMetrics;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mHeaderHeight = 0;
    /**
     * 变化音级的大小
     */
    private int changeTextSize;

    public MusicScoreView2(Context context, MusicScore2 musicScore) {
        super(context);
        init(musicScore);
    }

    private void init(MusicScore2 musicScore) {
        data = musicScore;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        this.defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        textPaint.setTextSize(defaultTextSize);
        fontMetrics = textPaint.getFontMetrics();
        defaultNoteWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        defaultNoteWidth2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        defaultNoteHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        mHeaderHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(10);

        changePaint.setColor(Color.BLACK);
        changePaint.setFakeBoldText(true);
        changePaint.setTextAlign(Paint.Align.CENTER);
        changeTextSize = defaultTextSize * 3 / 4;
        changePaint.setTextSize(changeTextSize);
    }

    public MusicScoreView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public MusicScoreView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null);
    }

    public MusicScoreView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(null);
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
            int note = data.getMusicNotes()[i];
            if (MusicNote2.isNextLineMark(note)) {
                maxWidth = x > maxWidth ? x : maxWidth;
                x = 0;
                y += defaultNoteHeight;
            } else {
                if (MusicNote2.getChange(note) == MusicNote2.STANDARD) {
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
            int note = data.getMusicNotes()[i];
            int width = 0;
            if (MusicNote2.isNextLineMark(note)) {
                x = changeTextSize / 2;
                y += defaultNoteHeight;
            } else {
                if (MusicNote2.getChange(note) == MusicNote2.STANDARD) {
                    width = defaultNoteWidth2;
                    if (x + width > getWidth()) {
                        x = changeTextSize / 2;
                        y += defaultNoteHeight;
                    }
                    x += width;
                } else {
                    width = defaultNoteWidth;
                    if (x + width > getWidth()) {
                        x = changeTextSize / 2;
                        y += defaultNoteHeight;
                    }
                    x += width;
                }
            }

            if (!MusicNote2.isNextLineMark(note) && !MusicNote2.isBlankMark(note)) {
                canvas.drawText(String.valueOf(MusicNote2.getNumber(note)), x - width / 2, y + defaultNoteHeight / 2, textPaint);
                if (MusicNote2.getLevel(note) == 1) {
                    canvas.drawCircle(x - width / 2, y + defaultNoteHeight / 2 + fontMetrics.top, 1, pointPaint);
                } else if (MusicNote2.getLevel(note) == -1) {
                    canvas.drawCircle(x - width / 2, y + defaultNoteHeight / 2 + fontMetrics.bottom, 1, pointPaint);
                }
                if (MusicNote2.getChange(note) == MusicNote2.DOWN) {
                    canvas.drawText("b", x - width, y + defaultNoteHeight / 4, changePaint);
                } else if (MusicNote2.getChange(note) == MusicNote2.UP) {
                    canvas.drawText("#", x - width, y + defaultNoteHeight / 4, changePaint);
                }
            }
        }
    }
}
