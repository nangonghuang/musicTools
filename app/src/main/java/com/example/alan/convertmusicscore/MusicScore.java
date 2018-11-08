package com.example.alan.convertmusicscore;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//乐曲
public class MusicScore {
    static final int half = 100;
    static final int full = 200;
    private String track = "C";
    static final int[] interval = {full, full, half, full, full, full, half};
    static final char[] names = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};
    static final int[] number = {1, 2, 3, 4, 5, 6, 7};

    String title;
    String author = "unknown";
    String desc;
    MusicNote[] musicNotes;
    int notesLen;

    private MusicScore() {

    }

    private MusicScore(String title, String track, MusicNote[] temp, int len) {
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
    public static MusicScore convertToStandard(String name, String track, String str) {
        try {
            char[] chars = str.toCharArray();
            MusicNote[] temp = new MusicNote[str.length()];
            int line = 1;
            int len = 0;
            for (int i = 0; i < chars.length; i++, len++) {
                if (chars[i] == '[' || chars[i] == '(') {
                    MusicNote note = new MusicNote();
                    if (chars[i] == '[') {
                        note.level += 1;
                    } else {
                        note.level -= 1;
                    }
                    i++;
                    if (chars[i] == '#') {
                        i++;
                        note.change = MusicNote.up;
                        note.number = Character.digit(chars[i], 10);
                    } else if (chars[i] == 'b') {
                        i++;
                        note.change = MusicNote.low;
                        note.number = Character.digit(chars[i], 10);
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        note.number = Character.digit(chars[i], 10);
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    i++; //跳过']',')'
                    temp[len] = note;
                } else if (chars[i] == '#' || chars[i] == 'b') {
                    MusicNote note = new MusicNote();
                    if (chars[i] == '#') {
                        i++;
                        note.change = MusicNote.up;
                        note.number = Character.digit(chars[i], 10);
                    } else if (chars[i] == 'b') {
                        i++;
                        note.change = MusicNote.low;
                        note.number = Character.digit(chars[i], 10);
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        note.number = Character.digit(chars[i], 10);
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    temp[len] = note;
                } else if (chars[i] > '0' && chars[i] < '8') {
                    MusicNote note = new MusicNote();
                    note.number = Character.digit(chars[i], 10);
                    temp[len] = note;
                } else if (chars[i] == ' ') {
                    MusicNote note = new MusicNote();
                    note.number = -1;
                    temp[len] = note;
                } else if (chars[i] == '\n') {
                    MusicNote note = new MusicNote();
                    note.number = -2;
                    temp[len] = note;
                    line++;
                } else {
                    throw new IllegalArgumentException("error input in line " + line + ",value = " + String.valueOf(chars[i]));
                }
            }
            return new MusicScore(name, track, temp, len);
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
            builder.append(musicNotes[i].toString());
        }
        return builder.toString();
    }

    public MusicNote[] getMusicNotes() {
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

    public static MusicScore readFromFile(File file) {
        return null;
    }
}
