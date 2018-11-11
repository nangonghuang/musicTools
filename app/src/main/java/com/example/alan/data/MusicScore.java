package com.example.alan.data;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Generated;

//乐曲
@Entity
public class MusicScore {
    static final int half = 100;
    static final int full = 200;
    static final int[] interval = {full, full, half, full, full, full, half};

    @Id(autoincrement = true)
    private Long id;
    /**
     * 主调
     */
    private String homophony;
//    static final int[] number = {1, 2, 3, 4, 5, 6, 7};

    String title ;
    String author = "unknown";
    String desc;

    @Transient
    int[] musicNotes;

    String content;

    int notesLen;

    private MusicScore(String title, String homophony, int[] temp, int len) {
        this.title = title == null ? "" : title;
        this.homophony = homophony == null ? "" : homophony;
        musicNotes = temp;
        notesLen = len;
        content = writeMusicNotesString();
    }

    @Generated(hash = 192322349)
    public MusicScore(Long id, String homophony, String title, String author, String desc, String content, int notesLen) {
        this.id = id;
        this.homophony = homophony;
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.content = content;
        this.notesLen = notesLen;
    }

    @Generated(hash = 733556080)
    public MusicScore() {
    }

    public String writeMusicNotesString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < notesLen; i++) {
            builder.append(musicNotes[i]);
            builder.append(",");
        }
        builder = builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public void readMusicNotesString() {
        String[] split = content.split(",");
        Log.i(TAG, "readMusicNotesString: " + content);
        musicNotes = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            musicNotes[i] = Integer.valueOf(split[i]);
        }
    }

    public String getHomophony() {
        return homophony;
    }

    @Nullable
    public static MusicScore convertToStandard(String name, String homophony, String str) {
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
                    value = MusicNote.setLevel(value, level);
                    i++;
                    if (chars[i] == '#') {
                        i++;
                        value = MusicNote.setChange(value, MusicNote.UP);
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] == 'b') {
                        i++;
                        value = MusicNote.setChange(value, MusicNote.DOWN);
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    i++; //跳过']',')'
                    temp[len] = value;
                } else if (chars[i] == '#' || chars[i] == 'b') {
                    int value = 0;
                    if (chars[i] == '#') {
                        i++;
                        value = MusicNote.setChange(value, MusicNote.UP);
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] == 'b') {
                        i++;
                        value = MusicNote.setChange(value, MusicNote.DOWN);
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else if (chars[i] > '0' && chars[i] < '8') {
                        value = MusicNote.setNumber(value, Character.digit(chars[i], 10));
                    } else {
                        throw new IllegalArgumentException("error input in line " + line);
                    }
                    temp[len] = value;
                } else if (chars[i] > '0' && chars[i] < '8') {
                    int value = MusicNote.setNumber(0, Character.digit(chars[i], 10));
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
            return new MusicScore(name, homophony, temp, len);
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
        builder.append("title:" + this.title + "\n");
        builder.append("author:" + this.author + "\n");
        builder.append("desc:" + this.desc + "\n");
        builder.append("homophony:" + this.homophony + "\n");
        builder.append("content:" + this.content + "\n");
        return builder.toString();
    }

    public int[] getMusicNotes() {
        if(musicNotes == null){
            readMusicNotesString();
        }
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHomophony(String homophony) {
        this.homophony = homophony;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNotesLen(int notesLen) {
        this.notesLen = notesLen;
    }
}
