package org.application.gameshelfapp.signemployee.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalAdminBoundaryTest {

    @Test
    void executeCommandTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            String[] testCommand = {"sign employee", "test", "testmail@gmail.com", "abcd", "Customer"};
            assertNotNull(test.executeCommand(testCommand));
        } catch(PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException |
                WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void exceuteCommandCheckFailedExceptionLaunchedByEmailTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            String[] testCommand = {"sign employee", "test1", "testmail@gmail.com", "abcd", "Customer"};
            assertThrows(CheckFailedException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void exceuteCommandCheckFailedExceptionLaunchedByUsernameTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            String[] testCommand = {"sign employee", "test", "testmail1@gmail.com", "abcd", "Customer"};
            assertThrows(CheckFailedException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandSyntaxErrorExceptionLaunchedByEmailTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            String[] testCommand = {"sign employee", "test", "mail", "abcd", "Customer"};
            assertThrows(SyntaxErrorException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandSyntaxErrorExceptionLaunchedByUsernameTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            String[] testCommand = {"sign employee", "$", "testmail@gmail.com", "abcd", "Customer"};
            assertThrows(SyntaxErrorException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            TerminalAdminBoundary test = new TerminalAdminBoundary(userBean);
            assertNotNull(test.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

}
