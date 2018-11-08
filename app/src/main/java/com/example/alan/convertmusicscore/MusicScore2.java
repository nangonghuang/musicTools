package com.example.alan.convertmusicscore;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//乐曲
public class MusicScore2 {
    static final int half = 100;
    static final int full = 200;
    private String track = "C";
    static final int[] interval = {full, full, half, full, full, full, half};
//    static final int[] number = {1, 2, 3, 4, 5, 6, 7};

    String title;
    String author = "unknown";
    String desc;
    int[] musicNotes;
    int notesLen;

    private MusicScore2() {

    }

    private MusicScore2(String title, String track, int[] temp, int len) {
        this.title = title;
        this.track = track;
        musicNotes = temp;
        notesLen = len;
    }

    public String getTrack() {
        return track;
    }

    //todo  改成位操作，对于一首歌，这里生成了太多的对象了，比如example,生成了983个对象，差不多有2M大小
    @Nullable
    public static MusicScore2 convertToStandard(String name, String track, String str) {
        try {
            char[] chars = str.toCharArray();
            int[] temp = new int[str.length()];
            int line = 1;
            int len = 0;
            for (int i = 0; i < chars.length; i++, len++) {
                if (chars[i] == '[' || chars[i] == '(') {
                    int value = 0;
                    int level = 0;
                    if (chars[i] == '[') {
                        level += 1;
                    } else {
                        level -= 1;
                    }
                    value = MusicNote2.setLevel(value, level);
                    i++;
                    if (chars[i] == '#') {
                        i++;
                        value = MusicNote2.setChange(value, MusicNote2.UP);
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] == 'b') {
                        i++;
                        value = MusicNote2.setChange(value, MusicNote2.DOWN);
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    i++; //跳过']',')'
                    temp[len] = value;
                } else if (chars[i] == '#' || chars[i] == 'b') {
                    int value = 0;
                    if (chars[i] == '#') {
                        i++;
                        value = MusicNote2.setChange(value, MusicNote2.UP);
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] == 'b') {
                        i++;
                        value = MusicNote2.setChange(value, MusicNote2.DOWN);
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        value = MusicNote2.setNumber(value, Character.digit(chars[i], 10));
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    temp[len] = value;
                } else if (chars[i] > '0' && chars[i] < '8') {
                    int value = MusicNote2.setNumber(0, Character.digit(chars[i], 10));
                    temp[len] = value;
                } else if (chars[i] == ' ') {
                    temp[len] = -1;
                } else if (chars[i] == '\n') {
                    temp[len] = -2;
                    line++;
                } else {
                    throw new IllegalArgumentException("error input in line " + line + ",value = " + String.valueOf(chars[i]));
                }
            }
            return new MusicScore2(name, track, temp, len);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String TAG = "MusicScore";

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < notesLen; i++) {
            builder.append(MusicNote2.toString(musicNotes[i]));
        }
        return builder.toString();
    }

    public int[] getMusicNotes() {
        return musicNotes;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNotesLen() {
        return notesLen;

    }

    public void writeFileToInternalDirs() {
    }

    public void writeFileToSDCards() {
    }

    public static MusicScore2 readFromFile(File file) {
        return null;
    }
}
