package org.application.gameshelfapp.login.boundary2.adapter;

import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogInAdapterTest{

    @Test
    void logInSuccessfulTest(){
        try{
            LogInAdapter adapter = new LogInAdapter();
            assertEquals("You're logged.\n\nType <buy>\n", adapter.logIn("testEmail@gmail.com", "testPassword"));
        } catch(PersistencyErrorException | CheckFailedException | NullPasswordException | SyntaxErrorException e) {
            fail();
        }
    }

    @Test
    void logInSyntaxErrorExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.logIn("testEmail", "test"));
    }

    @Test
    void logInEmailCheckFailedExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.logIn("testEMAIL@gmail.com", "test"));
    }

    @Test
    void logInPasswordCheckFailedExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.logIn("testEmail@gmail.com", "TEST"));
    }
}
