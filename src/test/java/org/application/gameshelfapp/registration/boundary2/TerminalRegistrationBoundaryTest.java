package org.application.gameshelfapp.registration.boundary2;

import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalRegistrationBoundaryTest {

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.truncateFile();
        else AccessDAOJDBCUtils.truncateTable();
    }
    @Test
    void executeCommandRegisterSuccessfulTest(){
        try {
            TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
            String[] command = {"register", "andrea", "fer.andrea35@gmail.com", "test"};
            assertEquals("Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n", boundary.executeCommand(command));
        } catch(PersistencyErrorException | GmailException | NullPasswordException | SyntaxErrorException |
                CheckFailedException e){
            fail();
        }
    }
    @Test
    void executeCommandRegisterUsernameSyntaxErrorExceptionTest(){
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"register", "<-", "testEmail@gmail.com", "test"};
        assertThrows(SyntaxErrorException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterEmailSyntaxErrorExceptionTest(){
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"register", "andrea", "testEmail", "test"};
        assertThrows(SyntaxErrorException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterUsernameCheckFailedExceptionTest(){
        String[][] records = {{"andrea", "testEmail@gmail.com", "test", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"register", "andrea", "testEmail1@gmail.com", "test"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterEmailCheckFailedExceptionTest(){
        String[][] records = {{"andrea", "testEmail@gmail.com", "test", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"register", "testName", "testEmail@gmail.com", "testPassword"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void checkCodeNumberFormatExceptionTest(){
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"code", "test"};
        assertThrows(NumberFormatException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandTooFewParametersTest(){
        TerminalRegistrationBoundary boundary = new TerminalRegistrationBoundary();
        String[] command = {"register"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> boundary.executeCommand(command));
    }
}
