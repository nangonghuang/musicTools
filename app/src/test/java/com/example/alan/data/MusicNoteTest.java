package com.example.alan.data;

import com.example.alan.data.MusicNote;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MusicNoteTest {
    @Test
    public void testConst() {
        assertEquals(0xfffffff8,MusicNote.NUMBER_BITS);
        assertEquals(0xffffffc7,MusicNote.LEVEL_BITS);
        assertEquals(0xffffff3f,MusicNote.CHANGE_BITS);
    }

    @Test
    public void setNumber() {
        int value = 0 ;
        value = MusicNote.setNumber(value,3);
        assertNotEquals(0,value);
        assertEquals(3,MusicNote.getNumber(value));

        value = MusicNote.setNumber(value,1);
        assertNotEquals(0,value);
        assertEquals(1,MusicNote.getNumber(value));

        value = MusicNote.setNumber(value,5);
        assertNotEquals(0,value);
        assertEquals(5,MusicNote.getNumber(value));

        value = MusicNote.setNumber(value,-1);
        assertEquals(-1,value);

        value = MusicNote.setNumber(value,8);
        assertEquals(0,value);
    }

    @Test
    public void setLevel() {
        int value = 1 ;
        assertEquals(0,MusicNote.getLevel(value));
        value = MusicNote.setLevel(value,1);
        assertNotEquals(0,value);
        assertEquals(1,MusicNote.getLevel(value));
        value = MusicNote.setLevel(value,2);
        assertNotEquals(0,value);
        assertEquals(2,MusicNote.getLevel(value));
        value = MusicNote.setLevel(value,-1);
        assertNotEquals(0,value);
        assertEquals(-1,MusicNote.getLevel(value));
        value = MusicNote.setLevel(value,-2);
        assertNotEquals(0,value);
        assertEquals(-2,MusicNote.getLevel(value));

        value = MusicNote.setLevel(value,0);
        assertNotEquals(0,value);
        assertEquals(0,MusicNote.getLevel(value));

        value = MusicNote.setLevel(value,4);
        assertEquals(0,value);

        value = 1;
        value = MusicNote.setLevel(value,-3);
        assertEquals(0,value);
    }

    @Test
    public void setChange() {
        int value = 1;
        value = MusicNote.setChange(value,MusicNote.DOWN);
        assertNotEquals(0,value);
        assertEquals(MusicNote.DOWN,MusicNote.getChange(value));

        value = MusicNote.setChange(value,MusicNote.UP);
        assertNotEquals(0,value);
        assertEquals(MusicNote.UP,MusicNote.getChange(value));

        value = MusicNote.setChange(value,MusicNote.STANDARD);
        assertNotEquals(0,value);
        assertEquals(MusicNote.STANDARD,MusicNote.getChange(value));
    }
}