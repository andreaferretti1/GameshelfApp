package org.application.gameshelfapp.login.boundary2.adapter;

import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogInAdapterTest{

    @AfterEach()
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            AccessDAOCSVUtils.truncateFile();
        } else {
            AccessDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void logInSuccessfulTest(){
        String[][] records = {{"testName", "testEmail@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        try{
            LogInAdapter adapter = new LogInAdapter();
            assertEquals("You're logged.\n\nType <buy>\n", adapter.logIn("testEmail@gmail.com", "test"));
        } catch(PersistencyErrorException | CheckFailedException | NullPasswordException | SyntaxErrorException e) {
            fail();
        }
    }

    @Test
    void logInSyntaxErrorExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.logIn("testEmail", "test"));
    }

    @Test
    void logInEmailCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail1@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.logIn("testEMAIL@gmail.com", "test"));
    }

    @Test
    void logInPasswordCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail@example.com", "testPassword", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.logIn("testEmail@gmail.com", "TEST"));
    }
}
