package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void getUsernameTest(){
        User user = new User("nameTest", null, null);
        assertEquals("nameTest", user.getUsername());
    }
    @Test
    void setUsernameTest(){
        User user = new User(null, null, null);
        user.setUsername("nameTest");
        assertEquals("nameTest", user.getUsername());
    }
    @Test
    void getEmailTest(){
        User user = new User(null, "emailTest", null);
        assertEquals("emailTest", user.getEmail());
    }
    @Test
    void setEmailTest(){
        User user = new User(null, null, null);
        user.setEmail("emailTest");
        assertEquals("emailTest", user.getEmail());
    }
    @Test
    void getTypeOfUserTest(){
        User user = new User(null, null, "Customer");
        assertEquals("Customer", user.getTypeOfUser());
    }
    @Test
    void setTypeOfUserTest(){
        User user = new User(null, null, null);
        user.setTypeOfUser("Customer");
        assertEquals("Customer", user.getTypeOfUser());
    }
}