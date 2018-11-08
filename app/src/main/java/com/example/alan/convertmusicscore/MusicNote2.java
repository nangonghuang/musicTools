package com.example.alan.convertmusicscore;

public class MusicNote2 {

    public static final int NUMBER_BITS = ~7; // 表示音符值的3个bit位, 0x 111...11000
    public static final int LEVEL_BITS = (NUMBER_BITS << 3) | 7; //  表示音级的3个bit位，0x 111...11000111
    public static final int CHANGE_BITS = (NUMBER_BITS << 5) | 0x3f; // 表示 变化音级的2个bit位0x 111...100111111

    public static final int NEXT_LINE = -2;
    public static final int BLANK = -1;

    /**
     * @param origin 音符的复合值
     * @param num    要设置的音符的值.i.e (1234567)
     * @return
     */
    public static int setNumber(int origin, int num) {
        if (num == -1 || num == -2) {
            return num;
        } else if (num > 0 && num < 8) {
            return origin & NUMBER_BITS | num;
        } else {
            return 0;
        }

    }

    public static boolean isNextLineMark(int origin) {
        return origin == -2;
    }

    public static boolean isBlankMark(int origin) {
        return origin == -1;
    }

    /**
     * @param origin 音符的复合值
     * @param level  要设置的音高值  +- 2范围,3位的最高位表示正负
     * @return
     */
    public static int setLevel(int origin, int level) {
        if (level > 2 || level < -2) {
            return 0;
        }
        int temp = level;
        if (level < 0) {
            temp = (-level) | 4;
        }
        return origin & LEVEL_BITS | (temp << 3);
    }

    /**
     * @param origin 音符的复合值
     * @param change 要设置的变化音级
     * @return
     */
    public static int setChange(int origin, int change) {
        return origin & CHANGE_BITS | (change << 6);
    }

    public static int getNumber(int origin) {
        return origin & (~NUMBER_BITS);
    }

    public static int getLevel(int origin) {
        int temp = (origin & (~LEVEL_BITS)) >>> 3;
        int high = (temp >>> 2) == 0 ? 1 : -1;
        int low = temp & 3;
        return high * low;
    }

    public static final char[] names = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};
    public static final int STANDARD = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;

    public static int getChange(int origin) {
        return (origin & (~CHANGE_BITS)) >>> 6;
    }

    public static String toString(int origin) {
        String prefix = "";
        int level = getLevel(origin);
        if (level > 1) {
            prefix = (level - 1) + "阶高音";
        } else if (level == 1) {
            prefix = "高音";
        } else if (level == -1) {
            prefix = "低音";
        } else if (level < -1) {
            prefix = (-level - 1) + "阶低音";
        }
        int change = getChange(origin);
        if (change == UP) {
            prefix += "升";
        } else if (change == DOWN) {
            prefix += "降";
        }

        String str = "";
        if (isBlankMark(origin)) {
            str = " ";
        } else if (isNextLineMark(origin)) {
            str = "\n";
        }
        return origin > 0 ? prefix + getNumber(origin) : str;
    }
}
