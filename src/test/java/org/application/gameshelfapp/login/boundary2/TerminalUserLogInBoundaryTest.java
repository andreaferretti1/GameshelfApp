package org.application.gameshelfapp.login.boundary2;

import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalUserLogInBoundaryTest {

    @Test
    void executeCommandSuccessfulLogInTest(){
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
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"login", "testEmail@example.com", "test"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandLogPasswordCheckFailedExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"login", "testEmail@example.com", "TEST"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }
}