package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogInControllerTest {

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            AccessDAOCSVUtils.truncateFile();
        } else {
            AccessDAOJDBCUtils.truncateTable();
        }
    }

    @Test
    void logInTest(){       //In the database there was tuple ('test', 'fer.andrea35@gmail.com', 'testPassword', 'Customer')
        String[][] records = {{"test", "fer.andrea35@gmail.com", "testPassword", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
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
        String[][] records = {{"test", "fer.andrea35@gmail.com", "testPassword", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
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
        String[][] records = {{"test", "fer.andrea35@gmail.com", "test", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
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