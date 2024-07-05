package org.application.gameshelfapp.signemployee.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalAdminBoundaryTest {

    @Test
    void executeCommandTest() {
        try {
            TerminalAdminBoundary test = new TerminalAdminBoundary(null);
            String[] testCommand = {"sign employee", "test", "testmail@gmail.com", "abcd", "Customer"};
            assertNotNull(test.executeCommand(testCommand));
        } catch(PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void exceuteCommandCheckFailedExceptionLaunchedByEmailTest(){
        TerminalAdminBoundary test = new TerminalAdminBoundary(null);
        String[] testCommand = {"sign employee", "test1", "testmail@gmail.com", "abcd", "Customer"};
        assertThrows(CheckFailedException.class, ()->test.executeCommand(testCommand));
    }

    @Test
    void exceuteCommandCheckFailedExceptionLaunchedByUsernameTest(){
        TerminalAdminBoundary test = new TerminalAdminBoundary(null);
        String[] testCommand = {"sign employee", "test", "testmail1@gmail.com", "abcd", "Customer"};
        assertThrows(CheckFailedException.class, ()->test.executeCommand(testCommand));
    }

    @Test
    void executeCommandSyntaxErrorExceptionLaunchedByEmailTest(){
        TerminalAdminBoundary test = new TerminalAdminBoundary(null);
        String[] testCommand = {"sign employee", "test", "mail", "abcd", "Customer"};
        assertThrows(SyntaxErrorException.class, ()->test.executeCommand(testCommand));
    }

    @Test
    void executeCommandSyntaxErrorExceptionLaunchedByUsernameTest(){
        TerminalAdminBoundary test = new TerminalAdminBoundary(null);
        String[] testCommand = {"sign employee", "$", "testmail@gmail.com", "abcd", "Customer"};
        assertThrows(SyntaxErrorException.class, ()->test.executeCommand(testCommand));
    }

    @Test
    void getUserBeanTest(){
        TerminalAdminBoundary test = new TerminalAdminBoundary(new UserBean());
        assertNotNull(test.getUserBean());
    }

}
