package org.application.gameshelfapp.signemployee.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignEmployeeAdapterTest {
    @Test
    void registerTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            AccessDAO testDao = PersistencyAbstractFactory.getFactory().createAccessDAO();
            test.register("test","testmail@gmail.com","abcd","Customer");
            assertNotNull(testDao.retrieveAccountByEmail(new AccessThroughLogIn("testmail@gmail.com", null,null)));
        }catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException |
                WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void registerCheckFailedExceptionLaunchedByEmailTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            assertThrows(CheckFailedException.class, () -> test.register("testName", "testmail@gmail.com", "abcd", "Customer"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void registerCheckFailedExceptionLaunchedByUsernameTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            assertThrows(CheckFailedException.class, () -> test.register("test", "testmail1@gmail.com", "abcd", "Customer"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void registerSyntaxErrorExceptionLaunchedByEmailTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            assertThrows(SyntaxErrorException.class, () -> test.register("test", "mail", "abcd", "Customer"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
    @Test
    void registerSyntaxErrorExceptionLaunchedByUsernameTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            assertThrows(SyntaxErrorException.class, () -> test.register("$", "testmail@gmail.com", "abcd", "Customer"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            SignEmployeeAdapter test = new SignEmployeeAdapter(userBean);
            assertNotNull(test.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
