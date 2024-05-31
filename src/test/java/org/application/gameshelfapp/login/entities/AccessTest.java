package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessTest {

    @Test
    void usernameTest(){
        Access access = new Access("test", null, null, null);
        assertEquals("test", access.getUsername());
    }

    @Test
    void emailTest(){
        Access access = new Access(null, "test", null, null);
        assertEquals("test", access.getEmail());
    }

    @Test
    void passwordTest(){
        Access access = new Access(null, null, "test", null);
        assertEquals("test", access.getPassword());
    }

    @Test
    void typeOfUserTest(){
        Access access = new Access(null, null, null, "test");
        assertEquals("test", access.getTypeOfUser());
    }

    @Test
    void encodePasswordTest(){
        try {
            Access access = new Access(null, null, "test", null);
            access.encodePassword();
            assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", access.getEncodedPassword());
            assertNull(access.getPassword());
        } catch(NullPasswordException e){
            fail();
        }
    }

    @Test
    void encodeNullPasswordTest(){
        Access access = new Access(null, null, null, null);
        assertThrows(NullPasswordException.class, () -> access.encodePassword());
    }
}
