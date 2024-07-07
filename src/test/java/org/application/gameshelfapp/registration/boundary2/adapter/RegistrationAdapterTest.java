package org.application.gameshelfapp.registration.boundary2.adapter;

import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationAdapterTest {
    @Test
    void registerSuccessfulTest(){
        try {
            RegistrationAdapter adapter = new RegistrationAdapter();
            assertEquals("Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n", adapter.register("andrea", "fer.andrea35@gmail.com", "test"));
        } catch(PersistencyErrorException | GmailException | NullPasswordException | CheckFailedException |
                SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registerUsernameSyntaxExceptionTest(){
        RegistrationAdapter adapter = new RegistrationAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.register("£$", "testEmail@gmail.com", "test"));
    }

    @Test
    void registerEmailSyntaxErrorExceptionTest(){
        RegistrationAdapter adapter = new RegistrationAdapter();
        assertThrows(SyntaxErrorException.class, () -> adapter.register("andrea", "testEmail", "test"));
    }

    @Test
    void registerUsernameCheckFailedExceptionTest(){
        RegistrationAdapter adapter = new RegistrationAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.register("andrea", "testEmail@gmail.com", "test"));
    }

    @Test
    void registerEmailCheckFailedExceptionTest(){
        RegistrationAdapter adapter = new RegistrationAdapter();
        assertThrows(CheckFailedException.class, () -> adapter.register("testName", "testEmail@gmail.com", "testPassword"));
    }

    @Test
    void checkCodeNumberFormatExceptionTest(){
        RegistrationAdapter adapter = new RegistrationAdapter();
        assertThrows(NumberFormatException.class, () -> adapter.checkCode("test"));
    }
}
