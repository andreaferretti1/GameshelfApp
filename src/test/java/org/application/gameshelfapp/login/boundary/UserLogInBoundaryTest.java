package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserLogInBoundaryTest {

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            AccessDAOCSVUtils.truncateFile();
        } else {
            AccessDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void logSuccessfulTest(){
        String[][] records = {{"testName", "testEmail@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        try {
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.log("testEmail@example.com", "test");
            assertNotNull(userLogInBoundary.getUserBean());
        }catch (PersistencyErrorException | SyntaxErrorException | CheckFailedException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void logSyntaxErrorExceptionTest(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(SyntaxErrorException.class, () -> userLogInBoundary.log("test", "testEmail"));
    }

    @Test
    void logEmailCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail1@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEmail@example.com", "test"));
    }

    @Test
    void logPasswordCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail@example.com", "testPassword", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEmail@example.com", "TEST"));
    }
}
