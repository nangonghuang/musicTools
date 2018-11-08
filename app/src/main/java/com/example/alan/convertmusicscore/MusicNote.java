package com.example.alan.convertmusicscore;

import androidx.annotation.NonNull;

//音符
class MusicNote {
    public static final int up = 1;
    public static final int stand = 0;
    public static final int low = 2;

    int level;  //音高
    int number;  // 音符数字
    char name;    // 音符名称
    int change = stand;   //变化音级

    public MusicNote() {
    }

    public MusicNote(int number) {
        this.number = number;
    }

    public boolean isNextLineMark() {
        return number == -2;
    }

    public boolean isBlankMark() {
        return number == -1;
    }

    @NonNull
    @Override
    public String toString() {
        String prefix = "";
        if (level > 1) {
            prefix = (level - 1) + "阶高音";
        } else if (level == 1) {
            prefix = "高音";
        } else if (level == -1) {
            prefix = "低音";
        } else if (level < -1) {
            prefix = (-level - 1) + "阶低音";
        }
        if (change == up) {
            prefix += "升";
        } else if (change == low) {
            prefix += "降";
        }

        String str = "";
        if (number == -1) {
            str = " ";
        } else if (number == -2) {
            str = "\n";
        }
        return number > 0 ? prefix + number : str;
    }
}
