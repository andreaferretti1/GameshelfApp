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
}
