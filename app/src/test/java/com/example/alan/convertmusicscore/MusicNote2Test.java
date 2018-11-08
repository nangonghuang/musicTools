package com.example.alan.convertmusicscore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MusicNote2Test {
    @Test
    public void testConst() {
        assertEquals(0xfffffff8,MusicNote2.NUMBER_BITS);
        assertEquals(0xffffffc7,MusicNote2.LEVEL_BITS);
        assertEquals(0xffffff3f,MusicNote2.CHANGE_BITS);
    }

    @Test
    public void setNumber() {
        int value = 0 ;
        value = MusicNote2.setNumber(value,3);
        assertNotEquals(0,value);
        assertEquals(3,MusicNote2.getNumber(value));

        value = MusicNote2.setNumber(value,1);
        assertNotEquals(0,value);
        assertEquals(1,MusicNote2.getNumber(value));

        value = MusicNote2.setNumber(value,5);
        assertNotEquals(0,value);
        assertEquals(5,MusicNote2.getNumber(value));

        value = MusicNote2.setNumber(value,-1);
        assertEquals(-1,value);

        value = MusicNote2.setNumber(value,8);
        assertEquals(0,value);
    }

    @Test
    public void setLevel() {
        int value = 1 ;
        assertEquals(0,MusicNote2.getLevel(value));
        value = MusicNote2.setLevel(value,1);
        assertNotEquals(0,value);
        assertEquals(1,MusicNote2.getLevel(value));
        value = MusicNote2.setLevel(value,2);
        assertNotEquals(0,value);
        assertEquals(2,MusicNote2.getLevel(value));
        value = MusicNote2.setLevel(value,-1);
        assertNotEquals(0,value);
        assertEquals(-1,MusicNote2.getLevel(value));
        value = MusicNote2.setLevel(value,-2);
        assertNotEquals(0,value);
        assertEquals(-2,MusicNote2.getLevel(value));

        value = MusicNote2.setLevel(value,0);
        assertNotEquals(0,value);
        assertEquals(0,MusicNote2.getLevel(value));

        value = MusicNote2.setLevel(value,4);
        assertEquals(0,value);

        value = 1;
        value = MusicNote2.setLevel(value,-3);
        assertEquals(0,value);
    }

    @Test
    public void setChange() {
        int value = 1;
        value = MusicNote2.setChange(value,MusicNote2.DOWN);
        assertNotEquals(0,value);
        assertEquals(MusicNote2.DOWN,MusicNote2.getChange(value));

        value = MusicNote2.setChange(value,MusicNote2.UP);
        assertNotEquals(0,value);
        assertEquals(MusicNote2.UP,MusicNote2.getChange(value));

        value = MusicNote2.setChange(value,MusicNote2.STANDARD);
        assertNotEquals(0,value);
        assertEquals(MusicNote2.STANDARD,MusicNote2.getChange(value));
    }
}