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
}
