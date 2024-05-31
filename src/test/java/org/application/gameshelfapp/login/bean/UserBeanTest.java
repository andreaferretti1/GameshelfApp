package org.application.gameshelfapp.login.bean;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserBeanTest {

    @Test
    void usernameUserBeanTest(){
        UserBean userBean = new UserBean();
        userBean.setUsername("andrea");
        assertEquals("andrea", userBean.getUsername());
    }

    @Test
    void emailUserBeanTest(){
        UserBean userBean = new UserBean();
        userBean.setEmail("fer.andrea35@gmail.com");
        assertEquals("fer.andrea35@gmail.com", userBean.getEmail());
    }

    @Test
    void typeOfUserUserBeanTest(){
        UserBean userBean = new UserBean();
        userBean.setTypeOfUser("customer");
        assertEquals("customer", userBean.getTypeOfUser());
    }
}
