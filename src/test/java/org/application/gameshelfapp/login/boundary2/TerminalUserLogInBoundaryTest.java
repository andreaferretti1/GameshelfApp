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

    @Test
    void executeCommandRegisterSuccessfulTest(){
        try {
            TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
            String[] command = {"register", "andrea", "fer.andrea35@gmail.com", "test"};
            assertEquals("Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n", boundary.executeCommand(command));
        } catch(PersistencyErrorException | GmailException | NullPasswordException | SyntaxErrorException | CheckFailedException e){
            fail();
        }
    }
    @Test
    void executeCommandRegisterUsernameSyntaxErrorExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"register", "<-", "testEmail@gmail.com", "test"};
        assertThrows(SyntaxErrorException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterEmailSyntaxErrorExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"register", "andrea", "testEmail", "test"};
        assertThrows(SyntaxErrorException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterUsernameCheckFailedExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"register", "andrea", "testEmail@gmail.com", "test"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandRegisterEmailCheckFailedExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"register", "testName", "testEmail@gmail.com", "testPassword"};
        assertThrows(CheckFailedException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void checkCodeNumberFormatExceptionTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"code", "test"};
        assertThrows(NumberFormatException.class, () -> boundary.executeCommand(command));
    }

    @Test
    void executeCommandTooFewParametersTest(){
        TerminalUserLogInBoundary boundary = new TerminalUserLogInBoundary();
        String[] command = {"login"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> boundary.executeCommand(command));
    }

}