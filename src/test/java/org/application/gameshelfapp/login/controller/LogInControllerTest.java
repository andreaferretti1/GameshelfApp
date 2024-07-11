package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
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
}