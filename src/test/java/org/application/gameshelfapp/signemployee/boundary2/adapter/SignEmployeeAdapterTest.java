package org.application.gameshelfapp.signemployee.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.signemployee.boundary2.adapter.SignEmployeeAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignEmployeeAdapterTest {
    @Test
    void registerTest() {
        try {
            SignEmployeeAdapter test = new SignEmployeeAdapter(null);
            AccessDAO testDao = PersistencyAbstractFactory.getFactory().createAccessDAO();
            test.register("test","testmail@gmail.com","abcd","Customer");
            assertNotNull(testDao.retrieveAccountByEmail(new AccessThroughLogIn("testmail@gmail.com", null,null)));
        }catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void registerCheckFailedExceptionLaunchedByEmailTest(){
        SignEmployeeAdapter test = new SignEmployeeAdapter(null);
        assertThrows(CheckFailedException.class, ()->  test.register("testName","testmail@gmail.com","abcd","Customer"));
    }

    @Test
    void registerCheckFailedExceptionLaunchedByUsernameTest(){
        SignEmployeeAdapter test = new SignEmployeeAdapter(null);
        assertThrows(CheckFailedException.class, ()->  test.register("test","testmail1@gmail.com","abcd","Customer"));
    }

    @Test
    void registerSyntaxErrorExceptionLaunchedByEmailTest(){
        SignEmployeeAdapter test = new SignEmployeeAdapter(null);
        assertThrows(SyntaxErrorException.class, ()-> test.register("test", "mail", "abcd", "Customer"));
    }

    @Test
    void registerSyntaxErrorExceptionLaunchedByUsernameTest(){
        SignEmployeeAdapter test = new SignEmployeeAdapter(null);
        assertThrows(SyntaxErrorException.class, ()-> test.register("$", "testmail@gmail.com", "abcd", "Customer"));
    }

    @Test
    void getUserBeanTest(){
        SignEmployeeAdapter test = new SignEmployeeAdapter(new UserBean());
        assertNotNull(test.getUserBean());
    }
}
