package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogInControllerTest {

    @Test
    void logInTest(){       //In the database there was tuple ('test', 'fer.andrea35@gmail.com', 'testPassword', 'Customer')
        LogInController logInController = new LogInController();
        LogInBean logInBean = new LogInBean();
        try {
            logInBean.setEmailBean("fer.andrea35@gmail.com");
            logInBean.setPasswordBean("testPassword");
            UserBean userBean = logInController.logIn(logInBean);
            assertNotNull(userBean);
        } catch(SyntaxErrorException | NullPasswordException | PersistencyErrorException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void logInWrongEmailTest(){
        LogInController logInController = new LogInController();
        LogInBean logInBean = new LogInBean();
        try{
            logInBean.setEmailBean("emailTest@gmail.com");
            logInBean.setPasswordBean("passwordTest");
            assertThrows(CheckFailedException.class, () -> logInController.logIn(logInBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void logInWrongPasswordTest(){      //In the database there was tuple ('nameTest', 'fer.andrea35@gmail.com', 'test', 'Customer')
        LogInController logInController = new LogInController();
        LogInBean logInBean = new LogInBean();
        try{
            logInBean.setEmailBean("fer.andrea35@gmail.com");
            logInBean.setPasswordBean("testPassword");
            assertThrows(CheckFailedException.class, () -> logInController.logIn(logInBean));
        } catch(SyntaxErrorException  e){
            fail();
        }
    }

    @Test
    void registrationTest(){
        LogInController logInController = new LogInController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest");
            registrationBean.setEmailBean("fer.andrea35@gmail.com");
            registrationBean.setPasswordBean("testPassword");
            logInController.registration(registrationBean);
        } catch(SyntaxErrorException | NullPasswordException | CheckFailedException | PersistencyErrorException | GmailException e){
            fail();
        }
    }

    @Test
    void regsitrationSameUsernameTest(){        //In the database there was tuple ('nameTest', 'email@gmail.com', 'testPassword', 'Customer')
        LogInController logInController = new LogInController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest");
            registrationBean.setEmailBean("email2@gmail.com");
            registrationBean.setPasswordBean("passwordTest");
            assertThrows(CheckFailedException.class, () -> logInController.registration(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registrationSameEmailTest(){       //In the database there was tuple ('nameTest', 'email@gmail.com', 'testPassword', 'Customer')
        LogInController logInController = new LogInController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest2");
            registrationBean.setEmailBean("email@gmail.com");
            registrationBean.setPasswordBean("testPassword");
            assertThrows(CheckFailedException.class, () -> logInController.registration(registrationBean));
        } catch(SyntaxErrorException e){
             fail();
        }
    }
    @Test
    void setAndGetRegAccessTest(){
        LogInController logInController = new LogInController();
        logInController.setRegAccess(new AccessThroughRegistration(null, null, null, null));
        assertNotNull(logInController.getRegAccess());
    }
}