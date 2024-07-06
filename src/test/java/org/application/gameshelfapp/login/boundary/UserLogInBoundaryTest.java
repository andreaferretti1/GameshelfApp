package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserLogInBoundaryTest {
    @Test
    void logSuccessfulTest(){
        try {
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.log("testEmail@example.com", "test");
            assertNotNull(userLogInBoundary.getUserBean());
            assertEquals("test", userLogInBoundary.getUserBean().getUsername());
            assertEquals("testEmail@example.com", userLogInBoundary.getUserBean().getEmail());
            assertEquals("customer", userLogInBoundary.getUserBean().getTypeOfUser());
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
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEmail@example.com", "test"));
    }

    @Test
    void logPasswordCheckFailedExceptionTest(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEmail@example.com", "TEST"));
    }

    @Test
    void logPasswordNullExceptionTest(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(NullPasswordException.class, () -> userLogInBoundary.log("testEmail@example.com", null));
    }

    @Test
    void registerSuccessfulTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "test");
        } catch(PersistencyErrorException | SyntaxErrorException | CheckFailedException | GmailException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void registerUsernameSyntaxErrorExceptionTest(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(SyntaxErrorException.class,() ->userLogInBoundary.register(".-<", "fer.andrea35@gmail.com", "test"));
    }

    @Test
    void registerEmailSyntaxErrorException(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(SyntaxErrorException.class, () -> userLogInBoundary.register("testName", "testEmail", "test"));
    }

    @Test  //credentials already existing in persistency are username: test, email: testEmail@example.com, password: test, typeOfUser: customer.
    void registerUsernameCheckFailedException(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.register("test", "testEmail2@example.com", "test"));
    }

    @Test
    void registerEmailCheckFailedException(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.register("test1", "testEmail@example.com", "test"));
    }

    @Test
    void registerNullPasswordException(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(NullPasswordException.class, () -> userLogInBoundary.register("test", "testEmail@example.com", null));
    }

    @Test
    void checkCodeNumberFormatExceptionTest(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(NumberFormatException.class, () -> userLogInBoundary.checkCode("test"));
    }

}
