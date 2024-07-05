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

    @Test
    void registerSuccessfulTest(){
        try {
            LogInAdapter adapter = new LogInAdapter();
            assertEquals("Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n", adapter.register("andrea", "fer.andrea35@gmail.com", "test"));
        } catch(PersistencyErrorException | GmailException | NullPasswordException | CheckFailedException | SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registerUsernameSyntaxExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.register("£$", "testEmail@gmail.com", "test"));
    }

    @Test
    void registerEmailSyntaxErrorExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.register("andrea", "testEmail", "test"));
    }

    @Test
    void registerUsernameCheckFailedExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.register("andrea", "testEmail@gmail.com", "test"));
    }

    @Test
    void registerEmailCheckFailedExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.register("testName", "testEmail@gmail.com", "testPassword"));
    }

    @Test
    void checkCodeSuccessfulTest(){
        try {
            LogInAdapter adapter = new LogInAdapter();
            adapter.register("andrea", "fer.andrea35@gmail.com", "test");
            String code = String.valueOf(adapter.getController().getRegAccess().getCodeGenerated());
            assertEquals("You're registered.\n\nType <buy>\n", adapter.checkCode(code));
        } catch(PersistencyErrorException | SyntaxErrorException | GmailException | CheckFailedException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void checkCodeGetUserBeanTest(){
        try {
            LogInAdapter adapter = new LogInAdapter();
            adapter.register("andrea", "fer.andrea35@gmail.com", "test");
            String code = String.valueOf(adapter.getController().getRegAccess().getCodeGenerated());
            adapter.checkCode(code);
            assertNotNull(adapter.getUserBean());
        } catch(PersistencyErrorException | SyntaxErrorException | GmailException | CheckFailedException | NullPasswordException e){
            fail();
        }
    }
    @Test
    void checkCodeCheckFailedExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        try {
            adapter.register("andrea", "fer.andrea35@gmail.com", "test");
        } catch(PersistencyErrorException | GmailException | SyntaxErrorException | NullPasswordException | CheckFailedException e){
            fail();
        }
        String code = String.valueOf(adapter.getController().getRegAccess().getCodeGenerated() + 1);
        assertThrows(CheckFailedException.class, () -> adapter.checkCode(code));
    }

    @Test
    void checkCodeNumberFormatExceptionTest(){
        LogInAdapter adapter = new LogInAdapter();
        assertThrows(NumberFormatException.class, () -> adapter.checkCode("test"));
    }
}
