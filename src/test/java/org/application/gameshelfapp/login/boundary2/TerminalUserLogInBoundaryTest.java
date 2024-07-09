package org.application.gameshelfapp.login.boundary2;

import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalUserLogInBoundaryTest {

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            AccessDAOJDBCUtils.truncateTable();
        } else {
            AccessDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void executeCommandSuccessfulLogInTest(){
        String[][] records = {{"testName", "testEmail@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        try {
            TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
            String[] command = new String[3];
            command[0] = "login";
            command[1] = "testEmail@example.com";
            command[2] = "test";
            assertEquals("You're logged.\n\nType <buy>\n", boundary.executeCommand(command));
        }catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException |
                GmailException e) {
                fail();
        }
    }

    @Test
    void executeCommandLogInGetUserBeanTest(){
        String[][] records = {{"testName", "testEmail@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        try {
            TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
            String[] command = new String[3];
            command[0] = "login";
            command[1] = "testEmail@example.com";
            command[2] = "test";
            boundary.executeCommand(command);
            assertNotNull(boundary.getUserBean());
        }catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException |
                GmailException e) {
            fail();
        }
    }

    @Test
    void executeCommandLogSyntaxErrorExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = new String[3];
        command[0] = "login";
        command[1] = "test";
        command[2] = "testEmail";
        assertThrows(SyntaxErrorException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandLogEmailCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail1@example.com", "f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"login", "testEmail@example.com", "test"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandLogPasswordCheckFailedExceptionTest(){
        String[][] records = {{"testName", "testEmail@example.com", "testPassword", "typeTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"login", "testEmail@example.com", "TEST"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }
}